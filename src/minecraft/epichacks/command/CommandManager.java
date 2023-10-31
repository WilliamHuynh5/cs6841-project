package epichacks.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import epichacks.Client;
import epichacks.command.impl.Bind;
import epichacks.command.impl.Help;
import epichacks.command.impl.Info;
import epichacks.command.impl.Say;
import epichacks.command.impl.Toggle;
import epichacks.events.listeners.EventChat;

public class CommandManager {

	public List<Command> commands = new ArrayList<Command>();
	public String prefix = ".";
	
	public CommandManager() {
		setup();
	}
	
	public void setup() {
		commands.add(new Toggle());
		commands.add(new Bind());
		commands.add(new Info());
		commands.add(new Say());
		commands.add(new Help());
	}
	
	public void handleChat(EventChat event) {
		String message = event.getMessage();
		
		if (!message.startsWith(prefix))
			return;
		
		event.setCancelled(true);
		
		message = message.substring(prefix.length());
		
		boolean foundCommand = false;
		
		if (message.split(" ").length > 0) {
			String commandName = message.split(" ")[0];
			
			for (Command c: commands) {
				if (c.aliases.contains(commandName) || c.name.equalsIgnoreCase(commandName)) {
					c.onCommand(Arrays.copyOfRange(message.split(" "), 1, message.split(" ").length), message);
					foundCommand = true;
					break;
				}
			}
		}
		
		if (!foundCommand) {
			Client.addChatMessage("Could not find command.");
		}
	}

}
