package epichacks.modules.render;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.lwjgl.input.Keyboard;

import epichacks.Client;
import epichacks.events.Event;
import epichacks.events.listeners.EventKey;
import epichacks.events.listeners.EventRenderGUI;
import epichacks.events.listeners.EventUpdate;
import epichacks.modules.Module;
import epichacks.settings.BooleanSetting;
import epichacks.settings.ModeSetting;
import epichacks.settings.NumberSetting;
import epichacks.settings.Setting;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import epichacks.events.Event;
import epichacks.events.listeners.EventUpdate;

/**
 * The {@code TabGUI} class represents a hack that provides an interactive GUI for game features.
 * It extends the {@link epichacks.modules.Module} class and customizes the rendering and behavior of the hack.
 */
public class TabGUI extends Module {
	
	// Variables to keep track of the current tab and the expansion state of the GUI
	public int currentTab;
	public boolean expanded;

    /**
     * Constructor to create a TabGUI module with specific settings.
     */
    public TabGUI() {
        super("TabGUI", Keyboard.KEY_NONE, Category.RENDER);
        hackToggle = true;  // Enables the hack by default
    }
    
    /**
     * Event handler for GUI rendering and related events.
     *
     * @param e The event being handled.
     */
    public void onEvent(Event e) {
        if (e instanceof EventRenderGUI) {
            // Get the FontRenderer object for rendering text
            FontRenderer fr = mc.fontRendererObj;

            // Define primary and secondary colors for the GUI
            int primaryColour = 0Xff0090ff, secondaryColour = 0xff0070aa;

            // Draw a semi-transparent background for the module category area
            Gui.drawRect(5, 5, 70, 5 + Module.Category.values().length * 16, 0x90000000);

            // Draw a colored rectangle to highlight the current tab/category
            Gui.drawRect(5, 5 + currentTab * 16, 70, 21 + currentTab * 16, primaryColour);

            // Initialize a count to keep track of the categories being displayed
            int count = 0;

            // Iterate through each category and display its name
            for (Category c : Module.Category.values()) {
                // Display the category name
                fr.drawString(c.name, 11, 10 + count * 16, -1);
                count++;
            }

            // Check if the GUI is expanded
            if (expanded) {
                // Get the category based on the current tab
                Category category = Module.Category.values()[currentTab];

                // Get modules for the current category
                List<Module> modules = Client.getModulesByCategory(category);

                // Check if there are no modules in this category, and return if so
                if (modules.size() == 0) {
                    return;
                }

                // Sort the modules based on module names
                Collections.sort(modules, new Comparator<Module>() {
                    public int compare(Module module1, Module module2) {
                        return module1.hackName.compareTo(module2.hackName);
                    }
                });
                
                // Calculate the maximum module name width
                int maxModuleNameWidth = 0;
                for (Module m : modules) {
                    int moduleNameWidth = fr.getStringWidth(m.hackName);
                    if (moduleNameWidth > maxModuleNameWidth) {
                        maxModuleNameWidth = moduleNameWidth;
                    }
                }

                // Draw a semi-transparent background for the module list
                int moduleListWidth = 70 + maxModuleNameWidth + 9; // Add some padding
                Gui.drawRect(70, 5, moduleListWidth + 3, 5 + modules.size() * 16, 0x90000000);

                // Draw a colored rectangle to highlight the current category
                Gui.drawRect(70, 5 + category.moduleIndex * 16, moduleListWidth + 3, 21 + category.moduleIndex * 16, primaryColour);

                count = 0;
                for (Module m : modules) {
                    // Draw the module's name at the specified position
                    fr.drawString(m.hackName, 76, 10 + count * 16, -1);

                    // Check if this module is the currently selected one and expanded
                    if (count == category.moduleIndex && m.expanded) {
                        int index = 0;
                        int maxLength = 0;
                        // Iterate through each setting in the module
                        for (Setting setting : m.settings) {
                            // Calculate the maximum text length for different settings
                            int settingTextWidth = 0;
                            if (setting instanceof BooleanSetting) {
                                BooleanSetting bool = (BooleanSetting) setting;
                                settingTextWidth = fr.getStringWidth(setting.name + ": " + (bool.enabled ? "Enabled" : "Disabled"));
                            } else if (setting instanceof NumberSetting) {
                                NumberSetting number = (NumberSetting) setting;
                                settingTextWidth = fr.getStringWidth(setting.name + ": " + number.getValue());
                            } else if (setting instanceof ModeSetting) {
                                ModeSetting mode = (ModeSetting) setting;
                                settingTextWidth = fr.getStringWidth(setting.name + ": " + mode.getMode());
                            }

                            if (settingTextWidth > maxLength) {
                                maxLength = settingTextWidth;
                            }

                            // Increment the index for the next setting
                            index++;
                        }

                        // Check if the module has settings
                        if (!m.settings.isEmpty()) {
                            // Draw a semi-transparent background for the settings area
                            Gui.drawRect(moduleListWidth + 3, 5, moduleListWidth + maxLength + 13, 5 + m.settings.size() * 16, 0x90000000);

                            // Draw a colored rectangle to highlight the selected setting
                            Gui.drawRect(moduleListWidth + 3, 5 + m.index * 16, moduleListWidth + maxLength + 13, 21 + m.index * 16,
                                    m.settings.get(m.index).focused ? secondaryColour : primaryColour);

                            // Initialize index to keep track of the setting being displayed
                            index = 0;

                            // Iterate through each setting in the module
                            for (Setting setting : m.settings) {
                                // Display the setting based on its type
                                if (setting instanceof BooleanSetting) {
                                    // Display a boolean setting
                                    BooleanSetting bool = (BooleanSetting) setting;
                                    fr.drawString(setting.name + ": " + (bool.enabled ? "Enabled" : "Disabled"), moduleListWidth + 9, 10 + index * 16, -1);
                                } else if (setting instanceof NumberSetting) {
                                    // Display a number setting
                                    NumberSetting number = (NumberSetting) setting;
                                    fr.drawString(setting.name + ": " + number.getValue(), moduleListWidth + 9, 10 + index * 16, -1);
                                } else if (setting instanceof ModeSetting) {
                                    // Display a mode setting
                                    ModeSetting mode = (ModeSetting) setting;
                                    fr.drawString(setting.name + ": " + mode.getMode(), moduleListWidth + 9, 10 + index * 16, -1);
                                }

                                // Increment the index for the next setting
                                index++;
                            }
                        }
                    }
                    count++;
                }
            }
        }

    	
    	if (e instanceof EventKey) {
    		// Get the category based on the current tab
    		Category category = Module.Category.values()[currentTab];

    		// Get modules for the current category
    		List<Module> modules = Client.getModulesByCategory(category);

    		// Get the keyboard code from the event
    		int code = ((EventKey)e).code;
    		
    		// Check if the keyboard event is the UP arrow key
    		if (code == Keyboard.KEY_UP) {
    		    // Check if the GUI is expanded
    		    if (expanded) {
    		        // Check if the current module is expanded and has settings
    		        if (expanded && !modules.isEmpty() && modules.get(category.moduleIndex).expanded) {
    		            Module module = modules.get(category.moduleIndex);
    		            // Check if a setting is focused within the module
    		            if (!module.settings.isEmpty()) {
    		                if (module.settings.get(module.index).focused) {
    		                    // If a setting is focused, increment its value (assuming it's a number setting)
    		                    Setting setting = module.settings.get(module.index);
    		                    if (setting instanceof NumberSetting) {
    		                        ((NumberSetting)setting).increment(true);
    		                    }
    		                } else {
    		                    // If no setting is focused, navigate to the previous setting or wrap around to the end
    		                    if (module.index <= 0) {
    		                        module.index = module.settings.size() - 1;
    		                    } else {
    		                        module.index--;
    		                    }   
    		                }
    		            }
    		        } else {
    		            // If the module is not expanded, navigate to the previous module or wrap around to the end
    		            if (category.moduleIndex <= 0) {
    		                category.moduleIndex = modules.size() - 1;
    		            } else {
    		                category.moduleIndex--;
    		            }   
    		        }  				
    		    } else {
    		        // If the GUI is not expanded, navigate to the previous tab or wrap around to the end
    		        if (currentTab <= 0) {
    		            currentTab = Module.Category.values().length - 1;
    		        } else {
    		            currentTab--;
    		        }    				
    		    }
    		}

    		// Check if the keyboard event is the DOWN arrow key
    		if (code == Keyboard.KEY_DOWN) {
    		    // Check if the GUI is expanded
    		    if (expanded) {
    		        // Check if the current module is expanded and has settings
    		        if (expanded && !modules.isEmpty() && modules.get(category.moduleIndex).expanded) {
    		            Module module = modules.get(category.moduleIndex);
    		            // Check if a setting is focused within the module
    		            if (!module.settings.isEmpty()) {
    		                if (module.settings.get(module.index).focused) {
    		                    // If a setting is focused, decrement its value (assuming it's a number setting)
    		                    Setting setting = module.settings.get(module.index);
    		                    if (setting instanceof NumberSetting) {
    		                        ((NumberSetting)setting).increment(false);
    		                    }
    		                } else {
    		                    // If no setting is focused, navigate to the next setting or wrap around to the beginning
    		                    if (module.index >= module.settings.size() - 1) {
    		                        module.index = 0;
    		                    } else {
    		                        module.index++;
    		                    }       		
    		                }
    		            }
    		        } else {
    		            // If the module is not expanded, navigate to the next module or wrap around to the beginning
    		            if (category.moduleIndex >= modules.size() - 1) {
    		                category.moduleIndex = 0;
    		            } else {
    		                category.moduleIndex++;
    		            }       				
    		        }
    		    } else {
    		        // If the GUI is not expanded, navigate to the next tab or wrap around to the beginning
    		        if (currentTab >= Module.Category.values().length - 1) {
    		            currentTab = 0;
    		        } else {
    		            currentTab++;
    		        }   				
    		    }
    		}
    		
    		// Check if the keyboard event is the RETURN key
    		if (code == Keyboard.KEY_RETURN) {
    		    // Check if the GUI is expanded and there are modules available
    		    if (expanded && modules.size() != 0) {
    		        // Get the current module based on the selected category
    		        Module module = modules.get(category.moduleIndex);

    		        // Expand the module if it's not expanded and has settings
    		        if (!module.expanded && !module.settings.isEmpty())
    		            module.expanded = true;	
    		        else if (module.expanded && !module.settings.isEmpty()) {
    		            // Toggle focus for the current setting if the module is expanded
    		            module.settings.get(module.index).focused = !module.settings.get(module.index).focused;
    		        }
    		    }
    		}
    		
    		// Check if the keyboard event is the RIGHT arrow key
    		if (code == Keyboard.KEY_RIGHT) {
    		    // Check if the GUI is expanded and there are modules available
    		    if (expanded && modules.size() != 0) {
    		        // Get the current module based on the selected category
    		        Module module = modules.get(category.moduleIndex);

    		        // Check if the module is expanded and has settings
    		        if (module.expanded) {
    		            // Check if a setting is focused within the module
    		            if (!module.settings.isEmpty()) {
    		                Setting setting = module.settings.get(module.index);
    		                // Toggle a boolean setting or cycle a mode setting if focused
    		                if (setting instanceof BooleanSetting) {
    		                    ((BooleanSetting)setting).toggle();
    		                }
    		                if (setting instanceof ModeSetting) {
    		                    ((ModeSetting)setting).cycle();
    		                }
    		            }
    		        } else {
    		            // Toggle the module if not expanded and not a specific type (e.g., TabGUI)
    		            if (!module.hackName.equals("TabGUI")) {
    		                module.toggle();    
    		            }
    		        }
    		    } else {
    		        // If the GUI is not expanded, expand it
    		        expanded = true;
    		    }
    		}
    		
    		// Check if the keyboard event is the LEFT arrow key
    		if (code == Keyboard.KEY_LEFT) {
    		    // Check if the GUI is expanded and there are modules available
    		    if (expanded && !modules.isEmpty() && modules.get(category.moduleIndex).expanded) {
    		        Module module = modules.get(category.moduleIndex);
    		        // Check if a setting is focused within the module
    		        if (!module.settings.isEmpty()) {
    		            if (module.settings.get(module.index).focused) {
    		                // Additional actions specific to focused settings can be added here
    		                // Currently, no specific action is defined when a setting is focused
    		            } else {
    		                // If no setting is focused, collapse the module
    		                modules.get(category.moduleIndex).expanded = false;
    		            }
    		        }
    		    } else {
    		        // If the GUI is not expanded, collapse it
    		        expanded = false;
    		    }
    		}
    	}
    }
}