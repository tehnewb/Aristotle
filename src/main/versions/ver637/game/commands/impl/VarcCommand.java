package versions.ver637.game.commands.impl;

import versions.ver637.game.commands.Command;
import versions.ver637.model.player.Player;
import versions.ver637.pane.GamePane;

public class VarcCommand implements Command {

	@Override
	public String[] names() {
		return new String[] { "varc", "bconfig", "cs2config" };
	}

	@Override
	public String description() {
		return "Sends the given varc/bconfig to the client";
	}

	@Override
	public void onExecute(Player player, String name, String... arguments) {
		int varcID = Integer.parseInt(arguments[0]);
		int value = Integer.parseInt(arguments[1]);

		GamePane pane = player.getPane();
		pane.setVarc(varcID, value);
	}

	@Override
	public void onFail(Player player) {

	}

}
