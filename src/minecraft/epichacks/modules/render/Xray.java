package epichacks.modules.render;
import epichacks.events.Event;
import epichacks.modules.Module;
import net.minecraft.block.Block;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

public class Xray extends Module {

	public static ArrayList<Block> xrayBlocks = new ArrayList();

	public Xray() {
        super("x-ray", Keyboard.KEY_X, Category.RENDER);
	}
	
	public void onEnable() {
		mc.renderGlobal.loadRenderers();
	}
	
	public void onDisable() {
		mc.renderGlobal.loadRenderers();
	}
	
	public void onEvent(Event e) {
		
	}
	
}