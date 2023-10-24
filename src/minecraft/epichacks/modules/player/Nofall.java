package epichacks.modules.player;

import org.lwjgl.input.Keyboard;

import epichacks.events.Event;
import epichacks.events.listeners.EventUpdate;
import epichacks.modules.Module;
import net.minecraft.network.play.client.C03PacketPlayer;
import epichacks.events.Event;
import epichacks.events.listeners.EventUpdate;

/**
 * The {@code Autosprint} class represents a hack that automatically sprints in the game.
 * It extends the {@link epichacks.modules.Module} class and provides specific behavior for enabling and disabling autosprint.
 */
public class Nofall extends Module {
    /**
     * Constructs a new {@code Autosprint} hack with the default name, key code, and category.
     * The default key code is {@link org.lwjgl.input.Keyboard#KEY_N}.
     */
    public Nofall() {
        super("no fall", Keyboard.KEY_M, Category.PLAYER);
    }
    
    public void onEvent(Event e) {
    	if (e instanceof EventUpdate && e.isPre()) {
    		if (mc.thePlayer.fallDistance > 2) {
    			// send a packet to the server telling the server that the player is in a state where they 
    			// should not take fall damage
    			mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true));
    		}
    	}
    }

    
}
