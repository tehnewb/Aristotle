package versions.ver637.game.commands.impl;

import versions.ver637.game.commands.Command;
import versions.ver637.model.player.Player;

public class ItemCommand implements Command {

	@Override
	public String[] names() {
		return new String[] { "item", "itm" };
	}

	@Override
	public String description() {
		return "Spawns an item to your inventory";
	}

	@Override
	public void onExecute(Player player, String... arguments) {

	}

	@Override
	public void onFail(Player player) {

	}

}
