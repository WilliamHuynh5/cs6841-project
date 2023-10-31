package epichacks.command.impl;

import epichacks.Client;
import epichacks.command.Command;
import epichacks.modules.Module;

public class Toggle extends Command {

	public Toggle() {
		super("Toggle", "Toggles a module by name.", "toggle <name>", "t");
	}

	@Override
	public void onCommand(String[] args, String command) {
		if (args.length > 0) {
			String moduleName = args[0];
			
			boolean foundModule = false;
			
			for (Module module : Client.modules.values()) {
				if (module.hackName.equalsIgnoreCase(moduleName) ) {
					module.toggle();
					
					Client.addChatMessage((module.isEnabled() ? "Enabled" : "Disabled") + " " + module.hackName);
					
					foundModule = true;
					break;
				}
			}
			
			if (!foundModule) {
				Client.addChatMessage("Could not find module.");
			}
		}
	}

}
