package versions.ver637.game.commands.impl;

import versions.ver637.game.commands.Command;
import versions.ver637.model.player.Player;
import versions.ver637.network.coders.frames.CS2Frame;

public class CS2Command implements Command {

	@Override
	public String[] names() {
		return new String[] { "script", "cs2" };
	}

	@Override
	public String description() {
		return "Sends the given script to the client";
	}

	@Override
	public void onExecute(Player player, String name, String... arguments) {
		int scriptID = Integer.parseInt(arguments[0]);

		Object[] values = new Object[arguments.length - 1];
		for (int i = 0; i < values.length; i++) {
			try {
				values[i] = Integer.parseInt(arguments[i + 1]);
			} catch (Exception e) {
				values[i] = arguments[i + 1];
			}
		}

		player.getSession().write(new CS2Frame(scriptID, values));
	}

	@Override
	public void onFail(Player player) {

	}

}
