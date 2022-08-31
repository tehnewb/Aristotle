package com.framework.map.path;

import java.util.function.Consumer;

import org.rsmod.pathfinder.Route;
import org.rsmod.pathfinder.RouteCoordinates;
import org.rsmod.pathfinder.SmartPathFinder;
import org.rsmod.pathfinder.collision.CollisionStrategy;
import org.rsmod.pathfinder.collision.NormalBlockFlagCollision;
import org.rsmod.pathfinder.reach.DefaultReachStrategy;
import org.rsmod.pathfinder.reach.ReachStrategy;

import com.framework.map.RSLocation;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;
import versions.ver637.map.WorldMap;

/**
 * The {@code RSPathFinderBuilder} is used to build specifications for how the
 * path finder will work.
 * 
 * @author Albert Beaupre
 */
@Getter
@Setter
@Accessors(fluent = true, chain = true)
public class RSRouteBuilder {

	private static final int[] directions = new int[16384];
	private static final int[] distances = new int[16384];
	private static final int[] xbuffer = new int[16384];
	private static final int[] ybuffer = new int[16384];
	private static SmartPathFinder pathFinder;

	@NonNull
	private RSLocation startingAt;
	@NonNull
	private RSLocation endingAt;
	@NonNull
	private Consumer<RSRoute> reachRequest = r -> {};
	private Object target;
	private int srcSize = 1;
	private int objectRotation = 1;
	private int objectShape = 1;
	private int accessBitMask = 0;
	private int maximumTurns = 10000;
	private int destWidth = 1;
	private int destHeight = 1;
	private int extraFlag = 0;

	@NonNull
	private CollisionStrategy collisionStrategy = new NormalBlockFlagCollision();
	@NonNull
	private ReachStrategy reachStrategy = DefaultReachStrategy.INSTANCE;

	/**
	 * Returns the {@code PathRoute} which contains the path from the startingAt
	 * location to the endingAt location of this {@code PathFinderBuilder}. This
	 * path found is determined by the specifications set to this builder.
	 * 
	 * @return the path found
	 */
	public RSRoute findPath() {
		if (pathFinder == null) {
			int[][] flags = WorldMap.getMap().getZoneFlags().getFlags();
			pathFinder = new SmartPathFinder(true, 128, 4096, directions, distances, xbuffer, ybuffer, 0, 0, 0, 0, false, flags, 0, true, 25);
		}

		int srcX = startingAt.getX();
		int srcY = startingAt.getY();
		int destX = endingAt.getX();
		int destY = endingAt.getY();
		int z = endingAt.getZ();
		Route path = pathFinder.findPath(srcX, srcY, destX, destY, z, srcSize, destWidth, destHeight, objectRotation, objectShape, accessBitMask, maximumTurns, extraFlag, collisionStrategy, reachStrategy);

		RSLocation[] checkpoints = new RSLocation[path.size() + 2];
		checkpoints[0] = startingAt.copy();
		checkpoints[checkpoints.length - 1] = endingAt.copy();
		for (int i = 1; i <= path.size(); i++) {
			RouteCoordinates r = path.get(i - 1);
			int packed = r.getPacked();
			int x = packed & 0xFFFF;
			int y = (packed >> 16) & 0xFFFF;
			checkpoints[i] = new RSLocation(x, y, endingAt.getZ());
		}
		return new RSRoute(checkpoints).target(target).reachRequest(reachRequest).failed(path.getAlternative() || path.getFailed());
	}

}
