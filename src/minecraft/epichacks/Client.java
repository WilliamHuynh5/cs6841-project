package epichacks;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import epichacks.modules.Module;
import epichacks.modules.Module.Category;
import epichacks.modules.combat.KillAura;
import epichacks.modules.movement.AutoSneak;
import epichacks.modules.movement.AutoWalk;
import epichacks.modules.movement.Autosprint;
import epichacks.modules.movement.Fly;
import epichacks.modules.movement.HighJump;
import epichacks.modules.movement.LongJump;
import epichacks.modules.player.AutoRespawn;
import epichacks.modules.player.Nofall;
import epichacks.modules.player.Nuker;
import epichacks.modules.player.Regen;
import epichacks.modules.render.Freecam;
import epichacks.modules.render.Fullbright;
import epichacks.modules.render.NoWeather;
import epichacks.modules.render.TabGUI;
import epichacks.modules.render.Xray;
import epichacks.ui.HUD;
import epichacks.util.XrayUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import epichacks.command.CommandManager;
import epichacks.events.Event;
import epichacks.events.listeners.EventChat;
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
	public static String version = "1";
	public static CommandManager commandManager = new CommandManager();
    /**
     * Initializes the client by adding a {@link epichacks.modules.movement.Fly} module to the modules map.
     */
    public static void startup() {
    	XrayUtils.initXRayBlocks();
    	// Fly
        Module flyModule = new Fly();
        modules.put(flyModule.getKey(), flyModule);
        // Autosprint
        Module autoSprint = new Autosprint();
        modules.put(autoSprint.getKey(), autoSprint);
        // AutoWalk
        Module autoWalk = new AutoWalk();
        modules.put(autoWalk.getKey(), autoWalk);
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
        Module noWeather = new NoWeather();
        modules.put(noWeather.getKey(), noWeather);
        // Freecam
        Module freecam = new Freecam();
        modules.put(freecam.getKey(), freecam);
        // Nuker
        Module nuker = new Nuker();
        modules.put(nuker.getKey(), nuker);
        // Xray
        Module xray = new Xray();
        modules.put(xray.getKey(), xray);
        // Regen
        Module regen = new Regen();
        modules.put(regen.getKey(), regen);
        // Auto Respawn
        Module autorespawn = new AutoRespawn();
        modules.put(autorespawn.getKey(), autorespawn);
    }

    /**
     * Handles an event by triggering the {@code onEvent} method of enabled modules.
     *
     * @param e The event to be handled.
     */
    public static void onEvent(Event e) {
    	if (e instanceof EventChat) {
    		commandManager.handleChat((EventChat) e);
    	}
    	
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
    	
    	// Sort the modules alphabetically by name
        modules.sort((module1, module2) -> module1.getHackName().compareTo(module2.getHackName()));

    	return modules;
    }
    
    public static void addChatMessage(String message) {
    	message = "\2479" + name + "\2477: " + message;
    	Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(message));
    }
}