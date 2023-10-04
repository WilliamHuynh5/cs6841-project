package epichacks.modules.render;

import org.lwjgl.input.Keyboard;

import epichacks.events.Event;
import epichacks.events.listeners.EventUpdate;
import epichacks.modules.Module;
import epichacks.events.Event;
import epichacks.events.listeners.EventUpdate;

/**
 * The {@code Autosprint} class represents a hack that automatically sprints in the game.
 * It extends the {@link epichacks.modules.Module} class and provides specific behavior for enabling and disabling autosprint.
 */
public class Fullbright extends Module {
    /**
     * Constructs a new {@code Autosprint} hack with the default name, key code, and category.
     * The default key code is {@link org.lwjgl.input.Keyboard#KEY_N}.
     */
    public Fullbright() {
        super("fullbright", Keyboard.KEY_O, Category.RENDER);
    }

    /**
     * Called when the hack is enabled.
     * This method is empty because autosprint does not require specific setup actions when enabled.
     */
    @Override
    public void onEnable() {
        mc.gameSettings.gammaSetting = 100;
    }

    /**
     * Called when the hack is disabled.
     * This method stops the player from sprinting when autosprint is disabled.
     */
    @Override
    public void onDisable() {
    	mc.gameSettings.gammaSetting = 1;
    }
}
