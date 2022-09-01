package versions.ver637.game.commands.impl;

import versions.ver637.game.commands.Command;
import versions.ver637.model.player.Player;
import versions.ver637.network.coders.frames.InterfaceFrame;
import versions.ver637.pane.GamePane;

public class InterfaceCommand implements Command {

	@Override
	public String[] names() {
		return new String[] { "inter", "interface" };
	}

	@Override
	public String description() {
		return "Opens an interface for viewing only";
	}

	@Override
	public void onExecute(Player player, String... arguments) {
		int interfaceID = Integer.parseInt(arguments[0]);

		GamePane pane = player.getPane();
		player.getSession().write(new InterfaceFrame(pane.getID(), interfaceID, pane.isResizable() ? 9 : 18, false));
	}

	@Override
	public void onFail(Player player) {

	}

}
