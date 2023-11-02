package epichacks.modules.movement;
import org.lwjgl.input.Keyboard;

import epichacks.events.Event;
import epichacks.events.listeners.EventUpdate;
import epichacks.modules.Module;
import net.minecraft.network.play.client.C13PacketPlayerAbilities;

/**
 * The {@code Fly} class represents a hack that enables flight in the game.
 * It extends the {@link epichacks.modules.Module} class and provides specific
 * behavior for enabling and disabling flight.
 */
public class Fly extends Module {

    /**
     * Constructs a new {@code Fly} hack with the default name, key code, and category.
     * The default key code is {@link org.lwjgl.input.Keyboard#KEY_G}.
     */
    public Fly() {
        super("fly", Keyboard.KEY_G, Category.MOVEMENT);
    }

    /**
     * Called when the hack is disabled.
     * This method disables the player's flight capabilities.
     */
    @Override
    public void onDisable() {
        mc.thePlayer.capabilities.isFlying = false;
    }

    /**
     * Handles an event, specifically the {@link epichacks.events.listeners.EventUpdate} event.
     *
     * @param e The event to be handled.
     */
    @Override
    public void onEvent(Event e) {
        if (e instanceof EventUpdate) {
            if (e.isPre()) {
            	// Enable flying
                mc.thePlayer.capabilities.allowFlying = true;
                mc.thePlayer.capabilities.isFlying = true;
                
                // Create a new C13PacketPlayerAbilities packet to update abilities
                C13PacketPlayerAbilities packet = new C13PacketPlayerAbilities();
                packet.setAllowFlying(true);
                packet.setFlying(true);
                
                // Send the packet to the server
                mc.getNetHandler().addToSendQueue(packet);            }
        }
    }
}
