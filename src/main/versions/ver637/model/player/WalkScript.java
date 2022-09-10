package versions.ver637.model.player;

import com.framework.map.RSLocation;
import com.framework.map.path.RSRouteBuilder;
import com.framework.mechanics.queue.RSQueueType;

import lombok.RequiredArgsConstructor;
import versions.ver637.model.player.mechanics.PlayerScript;

@RequiredArgsConstructor
public class WalkScript extends PlayerScript {

	private final int x;
	private final int y;

	@Override
	public void process() {
		RSRouteBuilder builder = new RSRouteBuilder();
		builder.startingAt(player.getLocation());
		builder.endingAt(new RSLocation(x, y, player.getLocation().getZ()));

		LocationVariables.resetRoute(player);
		player.getLocationVariables().setRoute(builder.findPath());
	}

	@Override
	public RSQueueType type() {
		return RSQueueType.Strong;
	}
}
