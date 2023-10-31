package epichacks.command.impl;

import epichacks.Client;
import epichacks.command.Command;
import epichacks.modules.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C01PacketChatMessage;

public class Help extends Command {

	public Help() {
		super("Help", "Displays a list of all commands.", "help", "h");
	}

	@Override
	public void onCommand(String[] args, String command) {
	    StringBuilder helpMessage = new StringBuilder();
	    helpMessage.append("\n§7---- Available Commands ----§r\n");

	    for (Command cmd : Client.commandManager.commands) {
	        helpMessage.append("§3.").append(cmd.getSyntax()).append("§r: ").append(cmd.getDescription()).append("\n");
	    }

	    Client.addChatMessage(helpMessage.toString());
	}



}
