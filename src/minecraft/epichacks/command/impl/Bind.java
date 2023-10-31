package epichacks.command.impl;

import org.lwjgl.input.Keyboard;

import epichacks.Client;
import epichacks.command.Command;
import epichacks.modules.Module;
import net.minecraft.client.settings.KeyBinding;

public class Bind extends Command {

	public Bind() {
		super("Bind", "Binds a module by name.", "bind <name> <key>", "b");
	}

	@Override
	public void onCommand(String[] args, String command) {
		if (args.length == 2) {
			String moduleName = args[0];
			String keyName = args[1];
			
			boolean foundModule = false;
			
			for (Module module: Client.modules.values()) {
				if (module.hackName.equalsIgnoreCase(moduleName)) {
					
					module.hackKeycode = Keyboard.getKeyIndex(keyName.toUpperCase());
					
					Client.addChatMessage(String.format("Bound %s to %s", module.hackName, Keyboard.getKeyName(module.getKey())));
					foundModule = true;
					break;
				}
			}
			
			if (!foundModule) {
				Client.addChatMessage("Could not find module.");
			}
		}
		
		if (args.length == 1) {
			if(args[0].equalsIgnoreCase("clear")) {
				for (Module module : Client.modules.values()) {
					module.hackKeycode = Keyboard.KEY_NONE;

				}
			}
			
			Client.addChatMessage("Cleared all binds.");
		}
		
	}

}
