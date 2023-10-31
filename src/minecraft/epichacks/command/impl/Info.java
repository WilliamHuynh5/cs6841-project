package epichacks.command.impl;

import epichacks.Client;
import epichacks.command.Command;
import epichacks.modules.Module;

public class Info extends Command {

	public Info() {
		super("Info", "Displays client information.", "info", "i");
	}

	@Override
	public void onCommand(String[] args, String command) {
		Client.addChatMessage("v" + Client.version + " by Ace Mikunda & William Huynh");
	}

}
