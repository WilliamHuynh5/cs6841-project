package epichacks.modules.combat;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.lwjgl.input.Keyboard;

import epichacks.events.Event;
import epichacks.events.listeners.EventMotion;
import epichacks.modules.Module;
import epichacks.settings.BooleanSetting;
import epichacks.settings.ModeSetting;
import epichacks.settings.NumberSetting;
import epichacks.util.Timer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C02PacketUseEntity.Action;
import net.minecraft.network.play.client.C0APacketAnimation;

/**
 * The {@code KillAura} class represents a hack that automatically targets and attacks nearby entities or players within a certain range.
 * It extends the {@link epichacks.modules.Module} class and provides specific behavior for enabling and disabling killaura.
 */
public class KillAura extends Module {
	
	public Timer timer = new Timer();
	public NumberSetting range = new NumberSetting("Range", 4, 1, 6, 0.1);
	public NumberSetting aps = new NumberSetting("APS", 10, 1, 20, 1);
	public BooleanSetting noSwing = new BooleanSetting("No Swing", false);
	
    /**
     * Constructs a new {@code KillAura} hack with the default name, key code, and category.
     * The default key code is {@link org.lwjgl.input.Keyboard#KEY_X}.
     */
    public KillAura() {
        super("killaura", Keyboard.KEY_X, Category.COMBAT);
        this.addSettings(range, aps, noSwing);
    }

    /**
     * Called when the hack is enabled.
     * This method is empty because killaura does not require specific setup actions when enabled.
     */
    @Override
    public void onEnable() {
        // No specific actions needed when enabling killaura.
    }

    /**
     * Called when the hack is disabled.
     * This method is empty because killaura does not require specific setup actions when disabled.
     */
    @Override
    public void onDisable() {
        // No specific actions needed when disabling killaura.
    }

    /**
     * Handles an event, specifically the {@link epichacks.events.listeners.EventMotion} event.
	 * When the event is "PRE," this method processes the event to target and attacks
	 * hostile mobs and players within a certain range
	 * 
     * @param e The event to be handled.
     */
    @Override
    public void onEvent(Event e) {
    	if (e instanceof EventMotion) {
    		if (e.isPre()) {
    			
    			EventMotion event = (EventMotion)e;
    			
                // Get a list of all living entities in the loaded world
    			List<EntityLivingBase> targets = (List<EntityLivingBase>) mc.theWorld.loadedEntityList.stream()
    					.filter(EntityLivingBase.class::isInstance).collect(Collectors.toList());
    			
                // Filter the list to only include entities within a certain range, alive, and not the player
    			targets = targets.stream().filter(entity -> entity.getDistanceToEntity(mc.thePlayer) < range.getValue() &&
    					entity != mc.thePlayer && !entity.isDead && entity.getHealth() > 0).collect(Collectors.toList());
    			
                // Sort the entities by their distance to the player, from closest to farthest
    			targets.sort(Comparator.comparingDouble(entity -> ((EntityLivingBase)entity).getDistanceToEntity(mc.thePlayer)));
    			
                // Filter the list to only include hostile mobs and players
    			targets = targets.stream().filter(entity -> entity instanceof EntityMob || entity instanceof EntityPlayer)
    					.collect(Collectors.toList());
    			
    			if (!targets.isEmpty()) {
                    // Get the closest entity
    				EntityLivingBase target = targets.get(0);
    				
                    // Set the yaw and pitch of the player to face the target entity
    				event.setYaw(getRotations(target)[0]);
    				event.setPitch(getRotations(target)[1]);
    				
                    // Check if the attack timer has elapsed to attack the target
    				if (timer.hasTimeElapsed((long) (1000 / aps.getValue()), true)) {
    					if (noSwing.isEnabled()) {
                            // Send a packet to perform the attack animation (no swing)
    	    				mc.thePlayer.sendQueue.addToSendQueue(new C0APacketAnimation());
    					} else {
                            // Ensure the player swings the item when attacking the entity
        					mc.thePlayer.swingItem();
    					}
                        // Attack the closest entity at a specified rate (10 times per second)
	    				mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(target, Action.ATTACK));
    				}
    			}
    		}
    	}
    }
    
   /**
     * Calculates yaw and pitch angles to face an entity.
     *
     * @param e The target entity to face.
     * @return An array containing yaw and pitch angles in degrees.
     */
    public float[] getRotations(Entity e) {
        // Calculate the differences in X, Y, and Z coordinates between the player and the target entity.
        double deltaX = e.posX + (e.posX - e.lastTickPosX) - mc.thePlayer.posX,
               deltaY = e.posY - 3.5 + e.getEyeHeight() - mc.thePlayer.posY + mc.thePlayer.getEyeHeight(),
               deltaZ = e.posZ + (e.posZ - e.lastTickPosZ) - mc.thePlayer.posZ,
               distance = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaZ, 2)); 

        // Calculate the yaw and pitch angles
        // The yaw angle is calculated using the arctangent of deltaX and deltaZ.
        float yaw = (float) Math.toDegrees(-Math.atan(deltaX / deltaZ)),
              // The pitch angle is calculated using the arctangent of deltaY and the calculated distance.
              pitch = (float) -Math.toDegrees(Math.atan(deltaY / distance));

        // Adjust the yaw angle based on the quadrant of the target entity
        // This ensures that the player faces the target correctly, considering the target's position.
        if (deltaX < 0 && deltaZ < 0) {
            yaw = (float) (90 + Math.toDegrees(Math.atan(deltaZ / deltaX)));
        } else if (deltaX > 0 && deltaZ < 0) {
            yaw = (float) (-90 + Math.toDegrees(Math.atan(deltaZ / deltaX)));
        }
        // Return an array containing the calculated yaw and pitch angles in degrees.
        return new float[] { yaw, pitch };
    }
}