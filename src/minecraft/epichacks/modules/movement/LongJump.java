package epichacks.modules.movement;

import org.lwjgl.input.Keyboard;

import epichacks.events.Event;
import epichacks.events.listeners.EventUpdate;
import epichacks.modules.Module;
import epichacks.settings.NumberSetting;
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
            if (e.isPre() && mc.gameSettings.keyBindJump.getIsKeyPressed()) {
                setCustomJumpDistance();
                hasJumped = true; 
            }
            // Reset the flag when the player is on the ground
            if (mc.thePlayer.onGround) {
                hasJumped = false;
            }
        }
    }
    
    /**
     * Sets a custom jump distance for the player based on the distance setting.
     * Takes into account the base jump distance and custom distance setting.
     */
    private void setCustomJumpDistance() {
        // update the player's jump distance considering the custom distance setting and the default distance (0.02F)
        mc.thePlayer.jumpMovementFactor = 0.02F + (float) distance.getValue() * 0.001F;
    }
}
