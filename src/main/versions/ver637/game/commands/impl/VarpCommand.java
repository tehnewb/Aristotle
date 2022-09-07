package versions.ver637.game.commands.impl;

import versions.ver637.game.commands.Command;
import versions.ver637.model.player.Player;
import versions.ver637.pane.GamePane;

public class VarpCommand implements Command {

	@Override
	public String[] names() {
		return new String[] { "varp", "config" };
	}

	@Override
	public String description() {
		return "Sends the given varp/config to the client";
	}

	@Override
	public void onExecute(Player player, String name, String... arguments) {
		int varpID = Integer.parseInt(arguments[0]);
		int value = Integer.parseInt(arguments[1]);

		GamePane pane = player.getPane();
		pane.setVarp(varpID, value);
	}

	@Override
	public void onFail(Player player) {

	}

}
