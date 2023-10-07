package epichacks.modules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import epichacks.events.Event;
import epichacks.settings.Setting;
import net.minecraft.client.Minecraft;

/**
 * The {@code Module} class serves as a superclass for various hacks and provides common functionality.
 * Hacks can be categorized into different types, and each hack can be enabled or disabled independently.
 */
public class Module {
    /**
     * The name of the hack.
     */
    public String hackName;

    /**
     * A flag indicating whether the hack is currently active or not.
     */
    public boolean hackToggle;

    /**
     * The key code (on a keyboard) that activates the hack.
     */
    public int hackKeycode;

    /**
     * The category to which the hack belongs. Categories include Combat, Movement, Player, and Render.
     */
    public Category hackCategory;

    /**
     * An instance of Minecraft client for game-related operations.
     */
    public Minecraft mc = Minecraft.getMinecraft();
    
    /**
     * A flag indicating whether the hack's settings are expanded or not.
     */
    public boolean expanded;  
    
    /**
     * A index indicating which setting the player is currently looking at.
     */
    public int index;
    
    /**
     * List to store instances of settings for a particular hack.
     */
    public List<Setting> settings = new ArrayList<Setting>();

    /**
     * Enumeration representing the possible categories of hacks.
     */
    public enum Category {
        /**
         * Represents combat-related hacks.
         */
        COMBAT("Combat"),

        /**
         * Represents movement-related hacks.
         */
        MOVEMENT("Movement"),

        /**
         * Represents player-related hacks.
         */
        PLAYER("Player"),

        /**
         * Represents render-related hacks.
         */
        RENDER("Render");
        
        public String name;
        public int moduleIndex;
    	
    	Category(String name) {
    		this.name = name;
    	}
    }

    /**
     * Constructs a new Module with the specified name, key code, and category.
     *
     * @param name     The name of the hack.
     * @param keycode  The key code that activates the hack.
     * @param category The category to which the hack belongs.
     */
    public Module(String name, int keycode, Category category) {
        this.hackName = name;
        this.hackKeycode = keycode;
        this.hackCategory = category;
    }
    
    /**
     * Adds an array of settings to the list of settings for a particular hack.
     *
     * @param settings An array of settings to be added.
     */
    public void addSettings(Setting...settings) {
    	this.settings.addAll(Arrays.asList(settings));
    }

    /**
     * Handles the specified event.
     *
     * @param e The event to be handled.
     */
    public void onEvent(Event e) {
        // Subclasses should override this method to define event handling behavior.
    }

    /**
     * Checks if the hack is currently enabled.
     *
     * @return {@code true} if the hack is enabled, {@code false} otherwise.
     */
    public boolean isEnabled() {
        return hackToggle;
    }

    /**
     * Gets the key code associated with the hack.
     *
     * @return The key code.
     */
    public int getKey() {
        return hackKeycode;
    }

    /**
     * Toggles the hack's enabled state.
     */
    public void toggle() {
        hackToggle = !hackToggle;
        if (hackToggle) {
            onEnable();
        } else {
            onDisable();
        }
    }

    /**
     * Called when the hack is enabled.
     * Subclasses can override this method to perform specific actions when the hack is enabled.
     */
    public void onEnable() {
        // Subclasses should override this method to define behavior on enable.
    }

    /**
     * Called when the hack is disabled.
     * Subclasses can override this method to perform specific actions when the hack is disabled.
     */
    public void onDisable() {
        // Subclasses should override this method to define behavior on disable.
    }
}
