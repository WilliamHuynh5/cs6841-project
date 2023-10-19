package epichacks;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import epichacks.modules.Module;
import epichacks.modules.Module.Category;
import epichacks.modules.combat.KillAura;
import epichacks.modules.movement.AutoSneak;
import epichacks.modules.movement.Autosprint;
import epichacks.modules.movement.Fly;
import epichacks.modules.movement.HighJump;
import epichacks.modules.movement.LongJump;
import epichacks.modules.player.Nofall;
import epichacks.modules.render.Fullbright;
import epichacks.modules.render.TabGUI;
import epichacks.modules.render.WeatherControl;
import epichacks.ui.HUD;
import epichacks.events.Event;
import epichacks.events.listeners.EventKey;


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
     * Initializes the client by adding a {@link epichacks.modules.movement.Fly} module to the modules map.
     */
    public static void startup() {
    	// Fly
        Module flyModule = new Fly();
        modules.put(flyModule.getKey(), flyModule);
        // Autosprint
        Module autoSprint = new Autosprint();
        modules.put(autoSprint.getKey(), autoSprint);
        // AutoSneak
        Module autoSneak = new AutoSneak();
        modules.put(autoSneak.getKey(), autoSneak);
        // Fullbright
        Module fullBright = new Fullbright();
        modules.put(fullBright.getKey(), fullBright);
        // Nofall
        Module noFall = new Nofall();
        modules.put(noFall.getKey(), noFall);
        // TabGUI
        Module tabGui = new TabGUI();
        modules.put(tabGui.getKey(), tabGui);
        // KillAura
        Module killAura = new KillAura();
        modules.put(killAura.getKey(), killAura);
        // High Jump
        Module highJump = new HighJump();
        modules.put(highJump.getKey(), highJump);
        // Long Jump
        Module longJump = new LongJump();
        modules.put(longJump.getKey(), longJump);
        // Weather Control
        Module weatherControl = new WeatherControl();
        modules.put(weatherControl.getKey(), weatherControl);
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
    	Client.onEvent(new EventKey(key));
        if (modules.containsKey(key)) {
            modules.get(key).toggle();
        }
    }
    
    public static List<Module> getModulesByCategory(Category c) {
    	List<Module> modules = new ArrayList<Module>();
    	
    	for (Map.Entry<Integer, Module> pair : Client.modules.entrySet()) {
    		if(pair.getValue().hackCategory == c) {
    			modules.add(pair.getValue());
    		}
    	}
    	return modules;
    }
}