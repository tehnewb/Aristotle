package versions.ver637.model.player;

import com.framework.event.RSController;
import com.framework.event.RSEventMethod;
import com.framework.map.RSLocation;

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
			player.getSession().write(new RegionFrame(player, variables.getView(), false, false));
			variables.setRegionLocation(toLocation);
		}
	}

}
