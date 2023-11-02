package epichacks.modules.movement;

import java.util.concurrent.ConcurrentHashMap;

import org.lwjgl.input.Keyboard;

import epichacks.Client;
import epichacks.events.Event;
import epichacks.events.listeners.EventUpdate;
import epichacks.modules.Module;
import epichacks.events.Event;
import epichacks.events.listeners.EventUpdate;

/**
 * The {@code AutoWalk} class represents a hack that automatically walks in the game.
 * It extends the {@link epichacks.modules.Module} class and provides specific behavior for enabling and disabling autowalk.
 */
public class AutoWalk extends Module {
	
    /**
     * Constructs a new {@code AutoWalk} hack with the default name, key code, and category.
     * The default key code is {@link org.lwjgl.input.Keyboard#KEY_U}.
     * @param modManager 
     */
    public AutoWalk() {
        super("autoWalk", Keyboard.KEY_U, Category.MOVEMENT);
    }

    /**
     * Called when the hack is disabled.
     * This method stops the player from walking when autowalk is disabled.
     */
    @Override
    public void onDisable() {
    	mc.gameSettings.keyBindForward.pressed = false;
    }
    
    /**
     * Handles an event, specifically the {@link epichacks.events.listeners.EventUpdate} event.
     * When the event is "PRE," this method sets the player to walk automatically.
     * Note that when autosprint is also enabled, the player will automatically sprint rather than walk.
     *
     * @param e The event to be handled.
     */
    @Override
    public void onEvent(Event e) {
        if (e instanceof EventUpdate) {
            if (e.isPre()) {
            	mc.gameSettings.keyBindForward.pressed = true;
            }
        }
    }
}


