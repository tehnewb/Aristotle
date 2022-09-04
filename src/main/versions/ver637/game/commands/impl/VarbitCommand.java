package versions.ver637.game.commands.impl;

import versions.ver637.game.commands.Command;
import versions.ver637.model.player.Player;
import versions.ver637.pane.GamePane;

public class VarbitCommand implements Command {

	@Override
	public String[] names() {
		return new String[] { "varbit", "cbf" };
	}

	@Override
	public String description() {
		return "Sends the given varbit/configbyfile to the client";
	}

	@Override
	public void onExecute(Player player, String... arguments) {
		int varbitID = Integer.parseInt(arguments[0]);
		int value = Integer.parseInt(arguments[1]);

		GamePane pane = player.getPane();
		pane.setVarbit(varbitID, value);
	}

	@Override
	public void onFail(Player player) {

	}

}
