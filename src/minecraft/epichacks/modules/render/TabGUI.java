package epichacks.modules.render;

import java.util.List;

import org.lwjgl.input.Keyboard;

import epichacks.Client;
import epichacks.events.Event;
import epichacks.events.listeners.EventKey;
import epichacks.events.listeners.EventRenderGUI;
import epichacks.events.listeners.EventUpdate;
import epichacks.modules.Module;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import epichacks.events.Event;
import epichacks.events.listeners.EventUpdate;

/**
 * The {@code Autosprint} class represents a hack that automatically sprints in the game.
 * It extends the {@link epichacks.modules.Module} class and provides specific behavior for enabling and disabling autosprint.
 */
public class TabGUI extends Module {
	
	public int currentTab;
	public boolean expanded;
    /**
     * Constructs a new {@code Autosprint} hack with the default name, key code, and category.
     * The default key code is {@link org.lwjgl.input.Keyboard#KEY_N}.
     */
    public TabGUI() {
        super("TabGUI", Keyboard.KEY_NONE, Category.RENDER);
        hackToggle = true;
    }
    
    public void onEvent(Event e) {
    	if (e instanceof EventRenderGUI) {
    		FontRenderer fr = mc.fontRendererObj;
    		Gui.drawRect(5, 30, 70, 30 + Module.Category.values().length * 16 + 4, 0x90000000);
    		Gui.drawRect(7,  33 + currentTab * 16, 7 + 61, 33 + currentTab * 16 + 12, 0xff0090ff);
    		int count = 0;
    		for (Category c : Module.Category.values()) {
    			fr.drawString(c.name, 11, 35 + count * 16, -1);
    			count++;
    		}
    		
    		if (expanded) {
        		Category category = Module.Category.values()[currentTab];
    			List<Module> modules = Client.getModulesByCategory(category);
        		
        		if (modules.size() == 0) {
        			return;
        		}
        		
        		Gui.drawRect(70, 30, 70 + 70, 30 + modules.size() * 16 + 1, 0x90000000);
        		Gui.drawRect(72,  33 + category.moduleIndex * 16, 7 + 61 + 68, 33 + category.moduleIndex * 16 + 12, 0xff0090ff);
        		count = 0;
        		for (Module m : modules) {
        			fr.drawString(m.hackName, 73, 35 + count * 16, -1);
        			count++;
        		}    			
    		}
    		

    	}
    	
    	if (e instanceof EventKey) {
    		Category category = Module.Category.values()[currentTab];
			List<Module> modules = Client.getModulesByCategory(category);
    		int code = ((EventKey)e).code;
    		
    		if (code == Keyboard.KEY_UP) {
    			if (expanded) {
          			if (category.moduleIndex <= 0) {
          				category.moduleIndex = modules.size() - 1;
        			} else {
        				category.moduleIndex--;
        			}     				
    			} else {
        			if (currentTab <= 0) {
        				currentTab = Module.Category.values().length - 1;
        			} else {
        				currentTab--;
        			}    				
    			}

    		}
    		if (code == Keyboard.KEY_DOWN) {
    			if (expanded) {
		   			if (category.moduleIndex >= modules.size() - 1) {
		   				category.moduleIndex = 0;
	    			} else {
	    				category.moduleIndex++;
	    			}       				
    			} else {
		   			if (currentTab >= Module.Category.values().length - 1) {
	    				currentTab = 0;
	    			} else {
	    				currentTab++;
	    			}   				
    			}
    		}
    		if (code == Keyboard.KEY_RIGHT) {
    			if (expanded && modules.size() != 0) {
    				Module module = modules.get(category.moduleIndex);
    				if (!module.hackName.equals("TabGUI")) {
    					modules.get(category.moduleIndex).toggle();	
    				}
    				
    			}else {
    				expanded = true;
    			}
    			
    		}
    		if (code == Keyboard.KEY_LEFT) {
    			expanded = false;
  
    		}
    		
    	}
    }
}
