package versions.ver637.model.player;

import com.framework.map.RSLocation;
import com.framework.map.path.RSRoute;
import com.framework.map.path.RSRouteStep;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import versions.ver637.model.player.flags.MovementFlag;
import versions.ver637.network.coders.frames.RegionFrame.RegionView;

@Getter
@Setter
public class LocationVariables {

	public static final RSLocation DefaultLocation = new RSLocation(3222, 3222);

	private static final RSRoute EmptyRoute = new RSRoute();

	@NonNull
	private transient RSRoute route = EmptyRoute;
	@NonNull
	private transient RSLocation regionLocation = null;
	@NonNull
	private transient RSLocation previousLocation = null;

	@NonNull
	private RSLocation currentLocation = DefaultLocation.copy();

	@NonNull
	private RegionView view = RegionView.Small;

	private boolean running;
	private boolean resting;

	/**
	 * Returns the location at which the player has entered a new region.
	 * 
	 * @return the region location
	 */
	public RSLocation getRegionLocation() {
		if (regionLocation == null)
			regionLocation = currentLocation;
		return regionLocation;
	}

	/**
	 * Processes the route movement for the given {@code player}.
	 * 
	 * @param player
	 */
	public static void processRoute(Player player) {
		LocationVariables variables = player.getAccount().getLocationVariables();
		RSRoute route = variables.getRoute();
		if (!route.isEmpty()) {
			RSRouteStep firstStep = route.next();

			if (variables.isRunning() && !route.isEmpty()) {
				RSRouteStep secondStep = route.next();
				player.setLocation(secondStep.location());
				player.getModel().registerFlag(new MovementFlag(firstStep, secondStep, variables.isRunning()));
			} else {
				player.setLocation(firstStep.location());
				player.getModel().registerFlag(new MovementFlag(firstStep, null, variables.isRunning()));
			}
		}
		if (route.isEmpty() && !route.failed()) {
			route.reachRequest().accept(route);

			variables.setRoute(EmptyRoute);
		}
	}

}
