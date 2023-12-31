package epichacks.modules.movement;

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
public class Autosprint extends Module {
    /**
     * Constructs a new {@code Autosprint} hack with the default name, key code, and category.
     * The default key code is {@link org.lwjgl.input.Keyboard#KEY_H}.
     */
    public Autosprint() {
        super("autoSprint", Keyboard.KEY_H, Category.MOVEMENT);
    }

    /**
     * Called when the hack is disabled.
     * This method stops the player from sprinting when autosprint is disabled.
     */
    @Override
    public void onDisable() {
        mc.thePlayer.setSprinting(false);
    }

    /**
     * Handles an event, specifically the {@link epichacks.events.listeners.EventUpdate} event.
     * When the event is "PRE," this method sets the player to sprint.
     *
     * @param e The event to be handled.
     */
    @Override
    public void onEvent(Event e) {
        if (e instanceof EventUpdate) {
            if (e.isPre() && mc.thePlayer.moveForward > 0 ) {
                mc.thePlayer.setSprinting(true);
            }
        }
    }
}
