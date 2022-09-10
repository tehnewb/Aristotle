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
	private BitSet playerFlags;

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
	 * Sets the player flag at the given coordinates.
	 * 
	 * @param x the local x coordinate of the chunk
	 * @param y the local y coordinate of the chunk
	 */
	public void setPlayerFlag(int x, int y) {
		if (this.playerFlags == null)
			this.playerFlags = new BitSet(TilePlaneSize * TilePlaneSize);

		this.playerFlags.set(x + y * TilePlaneSize);
	}

	/**
	 * Removes the player flag at the given coordinates
	 * 
	 * @param x the local x coordinate of the chunk
	 * @param y the local y coordinate of the chunk
	 */
	public void removePlayerFlag(int x, int y) {
		if (playerFlags == null)
			return;

		this.playerFlags.clear(x + y * TilePlaneSize);

		if (this.playerFlags.isEmpty()) {
			this.playerFlags = null;
		}
	}

	/**
	 * Returns true if there is currently a player standing on this location in the
	 * chunk.
	 * 
	 * @param x the local x coordinate of the chunk
	 * @param y the local y coordinate of the chunk
	 * @return true if there is a player; false otherwise
	 */
	public boolean hasPlayerFlag(int x, int y) {
		return this.playerFlags == null ? false : this.playerFlags.get(x + y * TilePlaneSize);
	}

	/**
	 * Sets the NPC flag at the given coordinates.
	 * 
	 * @param x the local x coordinate of the chunk
	 * @param y the local y coordinate of the chunk
	 */
	public void setNPCFlag(int x, int y) {
		if (this.npcFlags == null)
			this.npcFlags = new BitSet(TilePlaneSize * TilePlaneSize);

		this.npcFlags.set(x + y * TilePlaneSize);
	}

	/**
	 * Removes the NPC flag at the given coordinates.
	 * 
	 * @param x the local x coordinate of the chunk
	 * @param y the local y coordinate of the chunk
	 */
	public void removeNPCFlag(int x, int y) {
		if (npcFlags == null)
			return;

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
