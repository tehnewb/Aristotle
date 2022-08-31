package com.framework.map;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import versions.ver637.map.WorldMap;

@RequiredArgsConstructor
public class RSCollision {
	public static final int OPEN = 0;
	public static final int CLOSED = 0xFFFFFF;
	public static final int UNINITIALIZED = 0x1000000;
	public static final int OCCUPIED = 0x100;
	public static final int SOLID = 0x20000;
	public static final int BLOCKED = 0x200000;
	public static final int ALLOW_RANGE = 0x40000000;

	public static final int NORTH = 0x2;
	public static final int EAST = 0x8;
	public static final int SOUTH = 0x20;
	public static final int WEST = 0x80;

	public static final int NORTHEAST = 0x4;
	public static final int SOUTHEAST = 0x10;
	public static final int SOUTHWEST = 0x40;
	public static final int NORTHWEST = 0x1;

	public static final int EASTNORTH = EAST | NORTH;
	public static final int EASTSOUTH = EAST | SOUTH;
	public static final int WESTSOUTH = WEST | SOUTH;
	public static final int WESTNORTH = WEST | NORTH;

	public static final int BLOCKEDNORTH = 0x400;
	public static final int BLOCKEDEAST = 0x1000;
	public static final int BLOCKEDSOUTH = 0x4000;
	public static final int BLOCKEDWEST = 0x10000;

	public static final int BLOCKEDNORTHEAST = 0x800;
	public static final int BLOCKEDSOUTHEAST = 0x2000;
	public static final int BLOCKEDNORTHWEST = 0x200;
	public static final int BLOCKEDSOUTHWEST = 0x8000;

	public static final int BLOCKEDEAST_NORTH = BLOCKEDEAST | BLOCKEDNORTH;
	public static final int BLOCKEDEAST_SOUTH = BLOCKEDEAST | BLOCKEDSOUTH;
	public static final int BLOCKEDWEST_SOUTH = BLOCKEDWEST | BLOCKEDSOUTH;
	public static final int BLOCKEDWEST_NORTH = BLOCKEDWEST | BLOCKEDNORTH;

	public static final int RANGE_NORTH = NORTH << 22;
	public static final int RANGE_SOUTH = SOUTH << 22;
	public static final int RANGE_WEST = WEST << 22;
	public static final int RANGE_EAST = EAST << 22;
	public static final int RANGE_NORTH_WEST = NORTHWEST << 22;
	public static final int RANGE_NORTH_EAST = NORTHEAST << 22;
	public static final int RANGE_SOUTH_EAST = SOUTHEAST << 22;
	public static final int RANGE_SOUTH_WEST = SOUTHWEST << 22;
	public static final int RANGE_ALL = RANGE_NORTH | RANGE_SOUTH | RANGE_EAST | RANGE_WEST | RANGE_NORTH_WEST | RANGE_NORTH_EAST | RANGE_SOUTH_EAST | RANGE_SOUTH_WEST;

	public static final int BLOCK_RANGE_NORTH = 0x1280120;
	public static final int BLOCK_RANGE_SOUTH = 0x1280102;
	public static final int BLOCK_RANGE_EAST = 0x1280180;
	public static final int BLOCK_RANGE_WEST = 0x1280108;
	public static final int BLOCK_RANGE_NORTHEAST = 0x12801e0;
	public static final int BLOCK_RANGE_NORTHWEST = 0x1280138;
	public static final int BLOCK_RANGE_SOUTHEAST = 0x1280183;
	public static final int BLOCK_RANGE_SOUTHWEST = 0x128010e;

	@Getter
	private final RSMap map;

	/**
	 * Returns true if the given {@codes start} location can move to the given
	 * {@code finish} location.
	 * 
	 * @param start  starting location
	 * @param finish ending location
	 * @return true if can move; false otherwise
	 */
	public static boolean canTraverse(WorldMap map, RSLocation start, RSDirection to) {
		RSLocation finish = start.neighbor(to);

		if (start.equals(finish))
			return true;

		RSLocation delta = RSLocation.delta(start, finish);
		RSLocation north = start.neighbor(RSDirection.North);
		RSLocation east = start.neighbor(RSDirection.East);
		RSLocation south = start.neighbor(RSDirection.South);
		RSLocation west = start.neighbor(RSDirection.West);

		RSDirection approach = RSDirection.of(delta.getX(), delta.getY());

		int clipFrom = map.getFlags(start.getX(), start.getY(), start.getZ());
		int clipTo = map.getFlags(finish.getX(), finish.getY(), finish.getZ());
		int clipNorth = map.getFlags(north.getX(), north.getY(), north.getZ());
		int clipEast = map.getFlags(east.getX(), east.getY(), east.getZ());
		int clipSouth = map.getFlags(south.getX(), south.getY(), south.getZ());
		int clipWest = map.getFlags(west.getX(), west.getY(), west.getZ());

		return canMove8Way(clipFrom, clipTo, clipNorth, clipEast, clipSouth, clipWest, approach); // west of starting tile, and the approach we are taking to get to the destination.
	}

	/**
	 * Returns true if the {@code start} or {@code dest} flags contain a block mask
	 * in them.
	 * 
	 * @param start        the starting tile's flag
	 * @param dest         the destination tile's flag
	 * @param destApproach the direction from the start tile to the end tile
	 * @return true if the direction can be moved to
	 */
	public static boolean canMove4Way(int start, int dest, RSDirection destApproach) {
		if (isBlocked(dest))
			return false;

		switch (destApproach) {
			case North:
				return !(has(dest, SOUTH) || has(start, NORTH));
			case East:
				return !(has(dest, WEST) || has(start, EAST));
			case South:
				return !(has(dest, NORTH) || has(start, SOUTH));
			case West:
				return !(has(dest, EAST) || has(start, WEST));
			default:
				return false;
		}
	}

	/**
	 * Returns true if the {@code start} or {@code dest} flags contain a block mask
	 * in them. If any other flag parameters contain a block flag, this method will
	 * return false.
	 * 
	 * @param start        the starting tile's flag
	 * @param dest         the destination tile's flag
	 * @param destApproach the direction from the start tile to the end tile
	 * @return true if the direction can be moved to
	 */
	public static boolean canMove8Way(int start, int dest, int north, int east, int south, int west, RSDirection destApproach) {
		if (isBlocked(dest))
			return false;

		switch (destApproach) {
			case NorthEast:
				return !(has(dest, SOUTHWEST) || has(dest, SOUTH) || has(dest, WEST) || has(start, NORTHEAST) || has(start, EAST) || has(start, NORTH) || has(east, WEST) || has(east, NORTH) || isBlocked(east) || has(north, EAST) || has(north, SOUTH) || isBlocked(north));
			case NorthWest:
				return !(has(dest, SOUTHEAST) || has(dest, SOUTH) || has(dest, EAST) || has(start, NORTHWEST) || has(start, WEST) || has(start, NORTH) || has(west, EAST) || has(west, NORTH) || isBlocked(west) || has(north, WEST) || has(north, SOUTH) || isBlocked(north));
			case SouthEast:
				return !(has(dest, NORTHWEST) || has(dest, NORTH) || has(dest, WEST) || has(start, SOUTHEAST) || has(start, EAST) || has(start, SOUTH) || has(east, WEST) || has(east, SOUTH) || isBlocked(east) || has(south, EAST) || has(south, NORTH) || isBlocked(south));
			case SouthWest:
				return !(has(dest, NORTHEAST) || has(dest, NORTH) || has(dest, EAST) || has(start, SOUTHWEST) || has(start, WEST) || has(start, SOUTH) || has(west, EAST) || has(west, SOUTH) || isBlocked(west) || has(south, WEST) || has(south, NORTH) || isBlocked(south));
			default:
				return canMove4Way(start, dest, destApproach);
		}
	}

	/**
	 * Returns true if the given {@code flag} contains any masks that must be
	 * blocked.
	 * 
	 * @param flag the flag to check
	 * @return true if containing block masks; false otherwise
	 */
	public static boolean isBlocked(int flag) {
		return (has(flag, CLOSED) || has(flag, BLOCKED) || has(flag, OCCUPIED) || has(flag, SOLID));
	}

	/**
	 * Returns true if the given {@code flag} contains the given {@code mask}.
	 * 
	 * @param flag the flag to check
	 * @param mask the mask to check if it is added to the flag
	 * @return true if the flag contains the mask; false otherwise
	 */
	public static boolean has(int flag, int mask) {
		return (flag & mask) == mask;
	}
}
