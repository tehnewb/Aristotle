package com.framework.map;

import java.util.Objects;

import lombok.Getter;

public class RSLocation {

	@Getter
	private int x;
	@Getter
	private int y;
	@Getter
	private int z;

	/**
	 * Constructs a new {@code Location} with the given {@code x, y, z} assigned.
	 * 
	 * @param x the value on the x axis
	 * @param y the value on the y axis
	 * @param z the value on the z axis
	 */
	public RSLocation(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * Constructs a new {@code Location} with the given {@code x, y} assigned and
	 * the default value of z being 0.
	 * 
	 * @param x the value on the x axis
	 * @param y the value on the y axis
	 * @param z the value on the z axis
	 */
	public RSLocation(int x, int y) {
		this(x, y, 0);
	}

	/**
	 * Gets the local x relative to another tile.
	 * 
	 * @param other - the other tile.
	 * @return - the local x.
	 */
	public final int getLocalX(RSLocation other) {
		final int offset = 6;
		return x - (8 * (other.getChunkX() - offset));
	}

	/**
	 * Get the local y relative to another tile.
	 * 
	 * @param other - the other tile.
	 * @return - the local y.
	 */
	public final int getLocalY(RSLocation other) {
		final int offset = 6;
		return y - (8 * (other.getChunkY() - offset));
	}

	/**
	 * Translates this {@code Location} by adding the given {@code x, y, z}
	 * coordinates and returning a new instance with the translated values.
	 * 
	 * @param x the x value to add to this locations current x coordinate
	 * @param y the y value to add to this locations current y coordinate
	 * @param z the z value to add to this locations current z coordinate
	 * @return the new location with the translated coordinates
	 */
	public RSLocation translate(int x, int y, int z) {
		return new RSLocation(this.x + x, this.y + y, this.z + z);
	}

	/**
	 * Returns the delta between the given {@code from} and {@code to} locations.
	 * 
	 * @param from the location to subject from
	 * @param to   the location to subject to
	 * @return the delta between the locations
	 */
	public static RSLocation delta(RSLocation from, RSLocation to) {
		return new RSLocation(to.x - from.x, to.y - from.y, to.z - from.z);
	}

	/**
	 * Returns the neighbor location after translating the coordinates from the
	 * deltas of the given {@code direction}.
	 * 
	 * @param direction the direction to retrieve the deltas from
	 * @return the translated location
	 */
	public RSLocation neighbor(RSDirection direction) {
		return new RSLocation(x + direction.getDeltaX(), y + direction.getDeltaY(), z);
	}

	/**
	 * Returns a new instance copy of this {@code Location}.
	 * 
	 * @return a copy
	 */
	public RSLocation copy() {
		return new RSLocation(this.x, this.y, this.z);
	}

	/**
	 * Returns the id of the region that this {@code Location} is in.
	 * 
	 * @return the region id
	 */
	public int getRegionID() {
		return getRegionY() | (getRegionX() << RSChunk.TilePlaneSize);
	}

	/**
	 * Returns the chunk x coordinate of this {@code Location}.
	 * 
	 * @return the chunk x coordinate
	 */
	public int getChunkX() {
		return this.x >> RSChunk.Bits;
	}

	/**
	 * Returns the chunk y coordinate of this {@code Location}.
	 * 
	 * @return the chunk y coordinate
	 */
	public int getChunkY() {
		return this.y >> RSChunk.Bits;
	}

	/**
	 * Returns the region x coordinate of this {@code Location}.
	 * 
	 * @return the region x coordinate
	 */
	public int getRegionX() {
		return this.x >> RSRegion.Bits;
	}

	/**
	 * Returns the region y coordinate of this {@code Location}.
	 * 
	 * @return the region y coordinate
	 */
	public int getRegionY() {
		return this.y >> RSRegion.Bits;
	}

	/**
	 * Returns the local x coordinate relative to the chunk this coordinate sits in.
	 * 
	 * @return the local x coordinate
	 */
	public final int getLocalChunkX() {
		return x % RSChunk.TilePlaneSize;
	}

	/**
	 * Returns the local y coordinate relative to the chunk this coordinate sits in.
	 * 
	 * @return the local y coordinate
	 */
	public final int getLocalChunkY() {
		return y % RSChunk.TilePlaneSize;
	}

	/**
	 * Returns the 18 bit hash of this {@code Location}.
	 * 
	 * @return the 18 bit hash
	 */
	public int packTo18Bits() {
		return ((getRegionX() & 0xFF) << 8) | (getRegionY() & 0xFF) | ((getZ() & 0x3) << 16);
	}

	/**
	 * Returns the 30 bit hash of this {@code Location}.
	 * 
	 * @return the 30 bit hash
	 */
	public int packTo30Bits() {
		return getY() | getZ() << 28 | getX() << 14;
	}

	/**
	 * Returns true if the given {@code to} location is diagonal to this current
	 * location.
	 * 
	 * @param to the location to compare to
	 * @return true if diagonal; false otherwise
	 */
	public boolean isDiagonalTo(RSLocation to) {
		int xDial = Math.abs(x - to.x);
		int yDial = Math.abs(y - to.y);
		return xDial == 1 && yDial == 1;
	}

	/**
	 * Returns the distance between this {@code Location} values and the specified
	 * {@code Point3D} coordinate values.
	 * 
	 * @param point the {@code Location} to compare the coordinates to
	 * 
	 * @return the distance between this point and the specific {@code Location}
	 */
	public double distance(RSLocation location) {
		int ax = Math.abs(location.x - x);
		int ay = Math.abs(location.y - y);
		int az = Math.abs(location.z - z);
		return Math.sqrt((ax * ax) + (ay * ay) + (az * az));
	}

	/**
	 * Returns the distance between this {@code Location} values and the specified
	 * {@code (x,y,z)} coordinate values.
	 * 
	 * @param x the X coordinate of the coordinates to compare
	 * @param y the Y coordinate of the coordinates to compare
	 * @param z the Z coordinate of the coordinates to compare
	 * 
	 * @return the distance between this point and the specific coordinates
	 */
	public double distance(int x, int y, int z) {
		return distance(new RSLocation(x, y, z));
	}

	/**
	 * Returns {@code true} if the specified {@code Location} has a distance <= the
	 * specified {@code range} argument. If it does not, then {@code false} is
	 * returned.
	 * 
	 * @param point the point to check if this {@code Location} is in range
	 * @param range the range distance to check
	 * @return true if within range; return false otherwise
	 */
	public boolean inRange(RSLocation point, int range) {
		return point.distance(this) <= range;
	}

	/**
	 * The {@code Location} hashCode is hashed based on the x and y coordinates. The
	 * uniqueness of the hashCode is meant to be slim to be easily overridden by any
	 * other locations that have the same coordinates.
	 */
	public int hashCode() {
		return Objects.hash(x, y, z);
	}

	@Override
	public boolean equals(Object o) {
		if (RSLocation.class.isAssignableFrom(o.getClass())) {
			RSLocation loc = RSLocation.class.cast(o);
			return loc.x == x && loc.y == y && loc.z == z;
		}
		return false;
	}

	@Override
	public String toString() {
		return "Location[x=%s, y=%s, z=%s]".formatted(this.x, this.y, this.z, this.getChunkX(), this.getChunkY(), this.getRegionX(), this.getRegionY());
	}

}
