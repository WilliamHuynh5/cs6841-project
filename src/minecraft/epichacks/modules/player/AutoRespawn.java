package epichacks.modules.player;

import org.lwjgl.input.Keyboard;

import epichacks.events.Event;
import epichacks.events.listeners.EventUpdate;
import epichacks.modules.Module;

public class AutoRespawn extends Module{
	public AutoRespawn() {
		super("AutoRespawn", Keyboard.KEY_0, Category.PLAYER);
	}
	
	public void onEvent(Event e) {
		if(e instanceof EventUpdate) {
			if(mc.thePlayer.isDead) {
				mc.thePlayer.respawnPlayer();
			}
		}
	}
}
