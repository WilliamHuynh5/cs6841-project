package epichacks.modules.combat;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.lwjgl.input.Keyboard;

import epichacks.events.Event;
import epichacks.events.listeners.EventMotion;
import epichacks.modules.Module;
import epichacks.util.Timer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C02PacketUseEntity.Action;

/**
 * The {@code KillAura} class represents a hack that automatically targets and attacks nearby entities or players within a certain range.
 * It extends the {@link epichacks.modules.Module} class and provides specific behavior for enabling and disabling killaura.
 */
public class KillAura extends Module {
	
	public Timer timer = new Timer();
	
    /**
     * Constructs a new {@code KillAura} hack with the default name, key code, and category.
     * The default key code is {@link org.lwjgl.input.Keyboard#KEY_X}.
     */
    public KillAura() {
        super("killaura", Keyboard.KEY_X, Category.COMBAT);
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
    			
    			List<EntityLivingBase> targets = (List<EntityLivingBase>) mc.theWorld.loadedEntityList.stream()
    					.filter(EntityLivingBase.class::isInstance).collect(Collectors.toList());
    			
    			targets = targets.stream().filter(entity -> entity.getDistanceToEntity(mc.thePlayer) < 4 &&
    					entity != mc.thePlayer && !entity.isDead && entity.getHealth() > 0).collect(Collectors.toList());
    			
    			// sort the entities by closest to farthest away
    			targets.sort(Comparator.comparingDouble(entity -> ((EntityLivingBase)entity).getDistanceToEntity(mc.thePlayer)));
    			
    			// filter to only kill hostile mobs and players
    			targets = targets.stream().filter(entity -> entity instanceof EntityMob || entity instanceof EntityPlayer)
    					.collect(Collectors.toList());
    			
    			if (!targets.isEmpty()) {
    				EntityLivingBase target = targets.get(0);
    				
    				event.setYaw(getRotations(target)[0]);
    				event.setPitch(getRotations(target)[1]);
    				
    				if (timer.hasTimeElapsed(1000 / 10, true)) {
    					// ensure the player swings the item when attacking the entity
    					mc.thePlayer.swingItem();
	    				// attack the closest entity 10x/second
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
    	float yaw = (float) Math.toDegrees(-Math.atan(deltaX / deltaZ)),
    		  pitch = (float) -Math.toDegrees(Math.atan(deltaY / distance));
    	
        // Adjust yaw angle based on the quadrant of the target entity
    	if (deltaX < 0 && deltaZ < 0) {
    		yaw = (float) (90 + Math.toDegrees(Math.atan(deltaZ / deltaX)));
    	} else if (deltaX > 0 && deltaZ < 0) {
    		yaw = (float) (-90 + Math.toDegrees(Math.atan(deltaZ / deltaX)));
    	}
    	return new float[] { yaw, pitch };
    }
}
