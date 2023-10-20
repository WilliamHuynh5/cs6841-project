package epichacks.modules.movement;

import org.lwjgl.input.Keyboard;

import epichacks.events.Event;
import epichacks.events.listeners.EventUpdate;
import epichacks.modules.Module;
import epichacks.settings.NumberSetting;
import net.minecraft.potion.Potion;

/**
 * The {@code HighJump} class represents a hack that allows the player to perform high jumps in the game.
 * It extends the {@link epichacks.modules.Module} class and provides specific behavior for enabling and disabling high jump.
 */
public class HighJump extends Module {

    public NumberSetting height = new NumberSetting("Height", 6, 1, 100, 1);
    private boolean hasJumped = false;

    /**
     * Constructs a new {@code HighJump} hack with the default name, key code, and category.
     * The default key code is {@link org.lwjgl.input.Keyboard#KEY_J}.
     */
    public HighJump() {
        super("high jump", Keyboard.KEY_J, Category.MOVEMENT);
        this.addSettings(height);
    }

    /**
     * Called when the hack is disabled.
     * Resets the jump flag.
     */
    @Override
    public void onDisable() {
        hasJumped = false;
    }

    /**
     * Handles an event, specifically the {@link epichacks.events.listeners.EventUpdate} event.
     * When the event is "PRE," this method sets a custom jump height for the player based on the height setting.
     *
     * @param e The event to be handled.
     */
    @Override
    public void onEvent(Event e) {
        if (e instanceof EventUpdate) {
            if (e.isPre() && mc.gameSettings.keyBindJump.getIsKeyPressed() && !hasJumped) {
                jumpHigh();
                hasJumped = true;  
            }
            // Reset the flag when the player is on the ground
            if (mc.thePlayer.onGround) {
                hasJumped = false;
            }
        }
    }

    /**
     * Sets a custom jump height for the player based on the height setting.
     * Takes into account the base jump height (0.42F), custom height setting, and potion effects.
     */
    private void jumpHigh() {
        // Calculate the jump height considering the custom height setting
        float jumpHeight = 0.42F + (float) height.getValue() * 0.01F;

        // Apply potion effects
        if (mc.thePlayer.isPotionActive(Potion.jump)) {
            int amplifier = mc.thePlayer.getActivePotionEffect(Potion.jump).getAmplifier() + 1;
            jumpHeight += (float) amplifier * 0.1F;
        }

        // Apply the jump
        mc.thePlayer.motionY = jumpHeight;
        mc.thePlayer.isAirBorne = true;
    }
}
