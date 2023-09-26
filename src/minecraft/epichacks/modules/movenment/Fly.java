package epichacks.modules.movenment;
import org.lwjgl.input.Keyboard;

import epichacks.events.Event;
import epichacks.events.listeners.EventUpdate;
import epichacks.modules.Module;

public class Fly extends Module {

	public Fly() {
		super("fly", Keyboard.KEY_G, Category.MOVEMENT);
	}
	
	public void onEnable() {
		mc.thePlayer.capabilities.isFlying = true;
		mc.thePlayer.capabilities.allowFlying = true;
	}
	
	public void onDisable() {
		mc.thePlayer.capabilities.isFlying = false;
		mc.thePlayer.capabilities.allowFlying = false;
	}
	
	public void onEvent(Event e) {
		if (e instanceof EventUpdate) {
			if (e.isPre()) {
				
			}
		}
	}
	
}
