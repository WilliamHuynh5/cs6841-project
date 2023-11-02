package epichacks.modules.movement;

import org.lwjgl.input.Keyboard;

import epichacks.events.Event;
import epichacks.events.listeners.EventUpdate;
import epichacks.modules.Module;
import epichacks.events.Event;
import epichacks.events.listeners.EventUpdate;

/**
 * The {@code Sneak} class represents a hack that automatically sprints in the game.
 * It extends the {@link epichacks.modules.Module} class and provides specific behavior for enabling and disabling autosprint.
 */
public class AutoSneak extends Module {
    /**
     * Constructs a new {@code Sneak} hack with the default name, key code, and category.
     * The default key code is {@link org.lwjgl.input.Keyboard#KEY_Z}.
     */
    public AutoSneak() {
        super("autoSneak", Keyboard.KEY_Z, Category.MOVEMENT);
    }

    /**
     * Called when the hack is disabled.
     * This method stops the player from sneaking when autosneak is disabled.
     */
    @Override
    public void onDisable() {
        mc.gameSettings.keyBindSneak.pressed = false;
    }

    /**
     * Handles an event, specifically the {@link epichacks.events.listeners.EventUpdate} event.
     * When the event is "PRE," this method sets the player to sneak.
     *
     * @param e The event to be handled.
     */
    @Override
    public void onEvent(Event e) {
        if (e instanceof EventUpdate) {
            if (e.isPre() && mc.thePlayer.onGround) {
                mc.gameSettings.keyBindSneak.pressed = true;
            }
        }
    }
}
