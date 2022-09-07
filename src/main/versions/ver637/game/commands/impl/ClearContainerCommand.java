package versions.ver637.game.commands.impl;

import versions.ver637.game.commands.Command;
import versions.ver637.model.player.Player;

public class ClearContainerCommand implements Command {

	@Override
	public String[] names() {
		return new String[] { "clearinv", "clearequip" };
	}

	@Override
	public String description() {
		return null;
	}

	@Override
	public void onExecute(Player player, String name, String... arguments) {
		if (name.equalsIgnoreCase("clearinv")) {
			player.getInventory().clear();
		} else if (name.equalsIgnoreCase("clearequip")) {
			player.getEquipmentVariables().getEquipment().clear();
		}
	}

	@Override
	public void onFail(Player player) {

	}

}
