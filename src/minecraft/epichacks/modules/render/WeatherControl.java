package epichacks.modules.render;

import java.util.Random;

import org.lwjgl.input.Keyboard;

import epichacks.events.Event;
import epichacks.events.listeners.EventUpdate;
import epichacks.modules.Module;
import epichacks.settings.ModeSetting;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.WorldInfo;
import epichacks.events.Event;
import epichacks.events.listeners.EventUpdate;

/**
 * The {@code WeatherControl} class represents a hack that allows a player to have control over the weather in the game.
 * It extends the {@link epichacks.modules.Module} class and provides specific behavior for enabling and disabling weather control.
 */
public class WeatherControl extends Module {
	
	public ModeSetting weather = new ModeSetting("Weather", "clear", "clear", "rain", "storm");
	
    /**
     * Constructs a new {@code Weather} hack with the default name, key code, and category.
     * The default key code is {@link org.lwjgl.input.Keyboard#KEY_K}.
     */
    public WeatherControl() {
        super("weather control", Keyboard.KEY_K, Category.RENDER);
        this.addSettings(weather);
    }


    /**
     * Called when the hack is disabled. 
     * This method generates a random number of ticks before the weather changes back to clear,
     * sets this duration in the world's WorldInfo, and triggers the change.
     */
    @Override
    public void onDisable() {
    	// generate random number of ticks before weather changes to clear
    	int numTicksUntilClear = (300 + (new Random()).nextInt(600)) * 20;
    	
    	// get info about the world
        WorldServer worldServer = MinecraftServer.getServer().worldServers[0];
        WorldInfo worldInfo = worldServer.getWorldInfo();
        
        // set the number of ticks before the weather changes back to clear
        worldInfo.func_176142_i(numTicksUntilClear);
    }
    
    public void onEvent(Event e) {
    	if (e instanceof EventUpdate && e.isPre()) {
        	// get info about the world
            WorldServer worldServer = MinecraftServer.getServer().worldServers[0];
            WorldInfo worldInfo = worldServer.getWorldInfo();
            
            // set weather permanently
    		if (weather.getMode() == "clear") {
    			worldInfo.setRaining(false);
    			worldInfo.setThundering(false);
    			
    		} else if (weather.getMode() == "rain") {
    			worldInfo.setRaining(true);
    			worldInfo.setThundering(false);
    			
    		} else if (weather.getMode() == "storm") {
    			worldInfo.setRaining(true);
    			worldInfo.setThundering(true);
    		}
    	}
    }
}
