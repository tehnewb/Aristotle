package versions.ver637.model.player;

import com.framework.event.RSController;
import com.framework.event.RSEventMethod;
import com.framework.map.RSLocation;

import versions.ver637.model.player.music.MusicResource;
import versions.ver637.model.player.music.MusicTrack;
import versions.ver637.model.player.music.MusicVariables;
import versions.ver637.network.coders.frames.RegionFrame;

@RSController
public class PlayerLocationController {

	@RSEventMethod
	public void onLocationChange(PlayerLocationChangeEvent event) {
		Player player = event.player();
		RSLocation toLocation = event.to();

		LocationVariables variables = player.getAccount().getLocationVariables();
		RSLocation regionLocation = variables.getRegionLocation();
		int diffX = Math.abs(regionLocation.getChunkX() - toLocation.getChunkX());
		int diffY = Math.abs(regionLocation.getChunkY() - toLocation.getChunkY());

		int updateMapLength = ((variables.getView().tiles() >> 4) / 2);
		if (diffX >= updateMapLength || diffY >= updateMapLength) {
			player.getSession().write(new RegionFrame(player, variables.getView(), true, false));
			variables.setRegionLocation(toLocation);
		}

		MusicTrack music = MusicResource.getMusicTrack(toLocation.getRegionID());
		if (music != null) {
			if (!player.getMusicVariables().isTrackUnlocked(music.index())) {
				MusicVariables.unlockMusic(player, music.index());

				player.sendMessage("<col=ff0000>You have unlocked a new music track: {0}.", music.name());
			}
			MusicVariables.playMusic(player, music.index());
		}
	}

}
