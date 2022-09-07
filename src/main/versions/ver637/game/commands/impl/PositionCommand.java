package versions.ver637.game.commands.impl;

import com.framework.map.RSLocation;

import versions.ver637.game.commands.Command;
import versions.ver637.map.WorldMap;
import versions.ver637.model.player.Player;

public class PositionCommand implements Command {

	@Override
	public String[] names() {
		return new String[] { "pos", "coords", "position" };
	}

	@Override
	public String description() {
		return "Gives the current location of the player";
	}

	@Override
	public void onExecute(Player player, String name, String... arguments) {
		RSLocation l = player.getLocation();
		int flag = WorldMap.getMap().getFlags(l.getX(), l.getY(), l.getZ());
		System.out.printf("%s, chunkX=%s, chunkY=%s, regionX=%s, regionY=%s, regionID=%s, flag=%s\n", l, l.getChunkX(), l.getChunkY(), l.getRegionX(), l.getRegionY(), l.getRegionID(), flag);
	}

	@Override
	public void onFail(Player player) {

	}

}
