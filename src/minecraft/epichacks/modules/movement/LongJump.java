package epichacks.modules.movement;

import org.lwjgl.input.Keyboard;

import epichacks.events.Event;
import epichacks.events.listeners.EventUpdate;
import epichacks.modules.Module;
import epichacks.settings.NumberSetting;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.potion.Potion;

/**
 * The {@code LongJump} class represents a hack that allows the player to jump futher.
 * It extends the {@link epichacks.modules.Module} class and provides specific behavior for enabling and disabling long jump.
 */
public class LongJump extends Module {

    public NumberSetting distance = new NumberSetting("Distance", 10, 1, 100, 1);
    private boolean hasJumped = false;

    /**
     * Constructs a new {@code LongJump} hack with the default name, key code, and category.
     * The default key code is {@link org.lwjgl.input.Keyboard#KEY_L}.
     */
    public LongJump() {
        super("long jump", Keyboard.KEY_L, Category.MOVEMENT);
        this.addSettings(distance);
    }

    /**
     * Called when the hack is disabled.
     * Restores the default jump movement factor.
     */
    @Override
    public void onDisable() {
        mc.thePlayer.jumpMovementFactor = 0.02F;
    }

    /**
     * Handles an event, specifically the {@link epichacks.events.listeners.EventUpdate} event.
     * When the event is "PRE," this method sets a custom jump distance for the player based on the distance setting.
     *
     * @param e The event to be handled.
     */
    @Override
    public void onEvent(Event e) {
        if (e instanceof EventUpdate) {
            if (e.isPre() && mc.gameSettings.keyBindJump.getIsKeyPressed() && !hasJumped) {
                // Calculate the custom jump distance based on the setting
                float customJumpDistance = calculateCustomJumpDistance();

                // Set the custom jump distance
                setCustomJumpDistance(customJumpDistance);

                // Send a packet to notify the server of the change
                sendJumpDistancePacket(customJumpDistance);

                hasJumped = true;
            }
            // Reset the flag when the player is on the ground
            if (mc.thePlayer.onGround) {
                hasJumped = false;
            }
        }
    }

    /**
     * Calculates the custom jump distance based on the distance setting.
     */
    private float calculateCustomJumpDistance() {
        // Calculate the jump distance considering the custom distance setting
        return 0.02F + (float) distance.getValue() * 0.001F;
    }

    /**
     * Sets the player's custom jump distance.
     */
    private void setCustomJumpDistance(float customJumpDistance) {
        mc.thePlayer.jumpMovementFactor = customJumpDistance;
    }

    /**
     * Sends a packet to notify the server of the custom jump distance.
     */
    private void sendJumpDistancePacket(float customJumpDistance) {
        // Create a new C03PacketPlayer packet to update the player's motion
        C03PacketPlayer packet = new C03PacketPlayer.C04PacketPlayerPosition(
            mc.thePlayer.posX,
            mc.thePlayer.posY,
            mc.thePlayer.posZ,
            mc.thePlayer.onGround
        );

        // Send the packet to the server
        mc.thePlayer.sendQueue.addToSendQueue(packet);
    }
}
