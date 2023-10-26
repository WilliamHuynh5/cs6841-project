package epichacks.modules.render;

import java.util.Random;

import org.lwjgl.input.Keyboard;

import epichacks.events.Event;
import epichacks.events.listeners.EventUpdate;
import epichacks.modules.Module;
import epichacks.settings.BooleanSetting;
import epichacks.settings.ModeSetting;
import epichacks.settings.NumberSetting;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.GameRules;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.WorldInfo;
import epichacks.events.Event;
import epichacks.events.listeners.EventUpdate;

/**
 * The {@code NoWeather} class represents a hack that allows a player to disable weather in the game.
 * It extends the {@link epichacks.modules.Module} class and provides specific behavior for enabling and disabling weather.
 */
public class NoWeather extends Module {
	
	public BooleanSetting noRain = new BooleanSetting("No Rain", false);
	public BooleanSetting changeWorldTime = new BooleanSetting("Change World Time", false);
	public NumberSetting time = new NumberSetting("Time", 6000, 0, 23900, 100);
	
    // Fields to store original world info
    private int originalWorldTime;
    private boolean originalRaining;
    private boolean originalThundering;
	
    /**
     * Constructs a new {@code NoWeather} hack with the default name, key code, and category.
     * The default key code is {@link org.lwjgl.input.Keyboard#KEY_K}.
     */
    public NoWeather() {
        super("no-weather", Keyboard.KEY_K, Category.RENDER);
        this.addSettings(noRain, changeWorldTime, time);
    }
    
    @Override
    public void onEnable() {
        // Store original world info
        WorldServer worldServer = MinecraftServer.getServer().worldServers[0];
        WorldInfo worldInfo = worldServer.getWorldInfo();
        originalWorldTime = (int) worldInfo.getWorldTime();
        originalRaining = worldInfo.isRaining();
        originalThundering = worldInfo.isThundering();

        // Set weather and time
        setWeatherAndTime();
    }

    /**
     * Called when the hack is disabled. 
     */
    @Override
    public void onDisable() {
        WorldServer worldServer = MinecraftServer.getServer().worldServers[0];
        WorldInfo worldInfo = worldServer.getWorldInfo();
        GameRules gameRules = worldInfo.getGameRulesInstance();
        
        // Restore original weather
        worldInfo.setRaining(originalRaining);
        worldInfo.setThundering(originalThundering);
        
        // Enable daylight cycle
        gameRules.setOrCreateGameRule("doDaylightCycle", "true");
    }
    
    public void onEvent(Event e) {
        if (e instanceof EventUpdate && e.isPre()) {
            setWeatherAndTime();
        }
    }

    private void setWeatherAndTime() {
    	WorldServer worldServer = MinecraftServer.getServer().worldServers[0];
        WorldInfo worldInfo = worldServer.getWorldInfo();
        GameRules gameRules = worldInfo.getGameRulesInstance();

        if (noRain.isEnabled()) {
            // Disable rain and thunder
            worldInfo.setRaining(false);
            worldInfo.setThundering(false);
        } else {
        	// reset back to original weather
        	worldInfo.setRaining(originalRaining);
            worldInfo.setThundering(originalThundering);
        }
        if (changeWorldTime.isEnabled()) {
            // Set the time permanently and disable daylight cycle
            worldInfo.setWorldTime((long) time.getValue());
            gameRules.setOrCreateGameRule("doDaylightCycle", "false");
        } else {
        	// enable daylight cycle
            gameRules.setOrCreateGameRule("doDaylightCycle", "true");
        }
    }

}
