package versions.ver637.game.commands.impl;

import versions.ver637.cache.StructResource;
import versions.ver637.game.commands.Command;
import versions.ver637.model.player.Player;

public class StructCommand implements Command {

	@Override
	public String[] names() {
		return new String[] { "struct" };
	}

	@Override
	public String description() {
		return "Prints out the struct with the given ID";
	}

	@Override
	public void onExecute(Player player, String name, String... arguments) {
		int structID = getInt(arguments[0], -1);

		if (structID == -1)
			return;

		System.out.println(StructResource.getStructData(structID));
	}

	@Override
	public void onFail(Player player) {

	}

}
