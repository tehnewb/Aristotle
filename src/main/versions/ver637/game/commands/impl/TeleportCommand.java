package versions.ver637.game.commands.impl;

import com.framework.map.RSLocation;

import versions.ver637.game.commands.Command;
import versions.ver637.model.player.LocationVariables;
import versions.ver637.model.player.Player;
import versions.ver637.model.player.flags.TeleportFlag;

public class TeleportCommand implements Command {

	@Override
	public String[] names() {
		return new String[] { "tele", "teleport" };
	}

	@Override
	public String description() {
		return "Teleports the player to a given location";
	}

	@Override
	public void onExecute(Player player, String... arguments) {
		int x, y, z = player.getLocation().getZ();
		if (arguments[0].contains(",")) {
			String[] shiftArgs = arguments[0].trim().split(",");
			x = Integer.parseInt(shiftArgs[1]) << 6 | Integer.parseInt(shiftArgs[3]);
			y = Integer.parseInt(shiftArgs[2]) << 6 | Integer.parseInt(shiftArgs[4]);
		} else {
			x = Integer.parseInt(arguments[0].trim());
			y = Integer.parseInt(arguments[1].trim());
			z = arguments.length > 2 ? Integer.parseInt(arguments[2].trim()) : player.getLocation().getZ();
		}
		LocationVariables.resetRoute(player);
		player.setLocation(new RSLocation(x, y, z));
		player.getModel().registerFlag(new TeleportFlag());
	}

	@Override
	public void onFail(Player player) {

	}

}
