package epichacks.modules.player;

import org.lwjgl.input.Keyboard;

import epichacks.events.Event;
import epichacks.modules.Module;
import net.minecraft.network.play.client.C03PacketPlayer;

public class Regen extends Module{
	public Regen() {
		super("regen", Keyboard.KEY_R, Category.PLAYER);
	}
	public void onEvent(Event e) {
			if(e.isPre()) {
				
					if(mc.thePlayer.getHealth() < mc.thePlayer.getMaxHealth()) {
						for (int i = 0; i < 10; i++) {
							mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C05PacketPlayerLook(mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch, true));
						}
					}
			}
		}
	}

