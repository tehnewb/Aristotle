package com.framework.map.path;

import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.function.Consumer;

import com.framework.map.RSCollision;
import com.framework.map.RSDirection;
import com.framework.map.RSLocation;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;
import versions.ver637.map.WorldMap;

/**
 * The {@code RSRoute} contains a deque of {@code RSRouteStep} and is generally
 * used for player or npc traversal.
 * 
 * @author Albert Beaupre
 */
@Accessors(fluent = true, chain = true)
public class RSRoute implements Iterable<RSRouteStep> {

	private ArrayDeque<RSRouteStep> steps = new ArrayDeque<>();

	@Getter
	@Setter
	private Object target;

	@Getter
	@Setter
	@NonNull
	private Consumer<RSRoute> reachRequest = r -> {};

	@Getter
	@Setter
	private boolean failed;

	/**
	 * Returns the next {@code RSRouteStep} of this {@code RSRoute} and removes it.
	 * 
	 * @return the next step
	 */
	public RSRouteStep next() {
		return steps.poll();
	}

	/**
	 * Clears this {@code RSRoute} of all steps.
	 */
	public void clear() {
		steps.clear();
	}

	/**
	 * Returns true if there are no more steps within this {@code RSRoute}.
	 * 
	 * @return true if empty; otherwise false
	 */
	public boolean isEmpty() {
		return steps.isEmpty();
	}

	@Override
	public String toString() {
		return steps.toString();
	}

	@Override
	public Iterator<RSRouteStep> iterator() {
		return steps.iterator();
	}

	/**
	 * Builds the {@code RSRoute} based on the given {@code checkpoints}. The
	 * coordinates between the checkpoints are added to the route.
	 * 
	 * @param checkpoints the checkpoints to build from.
	 * @return the build steps route
	 */
	public static RSRoute buildFromCheckpoints(RSLocation... checkpoints) {
		if (checkpoints.length == 1)
			return new RSRoute();

		RSRoute route = new RSRoute();
		RSLocation from = checkpoints[0];
		for (int i = 1; i < checkpoints.length; i++) {
			RSLocation to = checkpoints[i];
			RSLocation current = from;
			while (!current.equals(to)) {
				RSDirection direction = RSDirection.getDirectionGoingTowards(from, to);
				if (!RSCollision.canTraverse(WorldMap.getMap(), current, direction))
					break;

				current = current.neighbor(direction);
				route.steps.add(new RSRouteStep(direction, current));
			}
			from = to;
		}
		return route;
	}

}
