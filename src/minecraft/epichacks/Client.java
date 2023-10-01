package epichacks;

import java.util.concurrent.ConcurrentHashMap;
import epichacks.modules.Module;
import epichacks.modules.movenment.Autosprint;
import epichacks.modules.movenment.Fly;
import epichacks.ui.HUD;
import epichacks.events.Event;


/**
 * The {@code Client} class represents a client application for managing modules and handling events.
 * It uses a {@link java.util.concurrent.ConcurrentHashMap} to store modules with integer keys.
 */
public class Client {

    /**
     * A concurrent map that stores modules with integer keys.
     */
    public static ConcurrentHashMap<Integer, Module> modules = new ConcurrentHashMap<Integer, Module>();
    public static HUD hud = new HUD();
	public static String name = "Epic Minecraft Hacks";
	public static String version = "1.8";
    /**
     * Initializes the client by adding a {@link epichacks.modules.movenment.Fly} module to the modules map.
     */
    public static void startup() {
    	// Fly
        Module flyModule = new Fly();
        modules.put(flyModule.getKey(), flyModule);
        // Autosprint
        Module autoSprint = new Autosprint();
        modules.put(autoSprint.getKey(), autoSprint);
    }

    /**
     * Handles an event by triggering the {@code onEvent} method of enabled modules.
     *
     * @param e The event to be handled.
     */
    public static void onEvent(Event e) {
        modules.values().stream()
                .filter(Module::isEnabled)
                .forEach(module -> module.onEvent(e));
    }

    /**
     * Toggles the module associated with the specified key.
     *
     * @param key The key associated with the module to be toggled.
     */
    public static void keyPress(int key) {
        if (modules.containsKey(key)) {
            modules.get(key).toggle();
        }
    }
}