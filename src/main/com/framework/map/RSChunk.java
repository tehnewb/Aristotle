package com.framework.map;

import java.util.BitSet;

import lombok.Getter;

/**
 * An {@code RSChunk} is filled with 64 tiles in an 8x8 area and has a relative
 * x, y, and z coordinate. Clipping, collision, and npc flags are set within
 * chunks.
 * 
 * @author Albert Beaupre
 */
public class RSChunk {

	public static final int TileCount = 64;
	public static final int TilePlaneSize = 8;
	public static final int Bits = 3;
	public static final int MaximumHeight = 4;

	private BitSet npcFlags;

	@Getter
	private final short chunkX;

	@Getter
	private final short chunkY;

	@Getter
	private final byte chunkZ;

	/**
	 * Constructs a new {@code RSChunk} with the given relative coordinates.
	 * 
	 * @param chunkX the x coordinate relative to the chunk
	 * @param chunkY the y coordinate relative to the chunk
	 * @param chunkZ the z coordinate relative to the chunk
	 */
	public RSChunk(int chunkX, int chunkY, int chunkZ) {
		this.chunkX = (short) chunkX;
		this.chunkY = (short) chunkY;
		this.chunkZ = (byte) chunkZ;
	}

	/**
	 * Sets the NPC at the given coordinates.
	 * 
	 * @param x   the local x coordinate of the chunk
	 * @param y   the local y coordinate of the chunk
	 * @param npc the npc to set
	 */
	public void setNPCFlag(int x, int y) {
		if (this.npcFlags == null)
			this.npcFlags = new BitSet(TilePlaneSize * TilePlaneSize);

		this.npcFlags.set(x + y * TilePlaneSize);
	}

	/**
	 * Removes the NPC at the given coordinates.
	 * 
	 * @param x the local x coordinate of the chunk
	 * @param y the local y coordinate of the chunk
	 */
	public void removeNPCFlag(int x, int y) {
		this.npcFlags.clear(x + y * TilePlaneSize);

		if (this.npcFlags.isEmpty()) {
			this.npcFlags = null;
		}
	}

	/**
	 * Returns true if there is currently an npc standing on this location in the
	 * chunk.
	 * 
	 * @param x the local x coordinate of the chunk
	 * @param y the local y coordinate of the chunk
	 * @return true if there is an npc; false otherwise
	 */
	public boolean hasNPCFlag(int x, int y) {
		return this.npcFlags == null ? false : this.npcFlags.get(x + y * TilePlaneSize);
	}
}
