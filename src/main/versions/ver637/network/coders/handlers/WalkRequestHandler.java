package versions.ver637.network.coders.handlers;

import com.framework.map.RSLocation;
import com.framework.map.path.RSPathFinderBuilder;
import com.framework.map.path.RSRoute;
import com.framework.network.RSFrame;

import versions.ver637.model.player.Player;
import versions.ver637.network.coders.FrameHandler;

public class WalkRequestHandler implements FrameHandler {

	@Override
	public void handleFrame(Player player, RSFrame frame) {
		int x = frame.readShort();
		int y = frame.readShort();

		RSLocation destination = new RSLocation(x, y, player.getLocation().getZ());
		RSPathFinderBuilder builder = new RSPathFinderBuilder();
		builder.startingAt(player.getLocation());
		builder.endingAt(destination);

		RSRoute route = builder.findPath();
		player.getAccount().getLocationVariables().setRoute(route);
	}

	@Override
	public int[] opcodesHandled() {
		return new int[] { 35, 50 };
	}

}