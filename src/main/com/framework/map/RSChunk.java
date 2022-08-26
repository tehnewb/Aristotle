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
	public static final int Size = 8;
	public static final int Bits = 3;
	public static final int MaximumHeight = 4;

	private BitSet npcFlags;
	private byte[][] flags;
	private int[][] clip;

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
			this.npcFlags = new BitSet(Size * Size);

		this.npcFlags.set(x + y * Size);
	}

	/**
	 * Removes the NPC at the given coordinates.
	 * 
	 * @param x the local x coordinate of the chunk
	 * @param y the local y coordinate of the chunk
	 */
	public void removeNPCFlag(int x, int y) {
		this.npcFlags.clear(x + y * Size);

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
		return this.npcFlags == null ? false : this.npcFlags.get(x + y * Size);
	}

	/**
	 * Adds the specified {@code clip} value to the clips of this {@code Chunk} at
	 * the specified {@code (x, y)} coordinate argument.
	 * 
	 * @param x    the x coordinate to add the clip
	 * @param y    the y coordinate to add the clip
	 * @param clip the clip value to add
	 */
	public void addClip(int x, int y, int clip) {
		if (this.clip == null)
			this.clip = new int[Size][Size];

		this.clip[x][y] |= clip;
	}

	/**
	 * Removes the specified {@code clip} value from the clips of this {@code Chunk}
	 * at the specified ({@code (x, y)} coordinate arguments
	 * 
	 * @param x    the x coordinate to remove the clip from
	 * @param y    the y coordinate to remove the clip from
	 * @param clip the clip value to remove the clip location
	 */
	public void removeClip(int x, int y, int clip) {
		if (this.clip == null)
			return;
		this.clip[x][y] &= ~clip;
	}

	/**
	 * Returns the clip value placed at the specified {@code (x, y)} coordinate
	 * arguments.
	 * 
	 * @param x the x coordinate to get the clip at
	 * @param y the y coordinate to get the clip at
	 * @return the clip value placed at the coordinates
	 */
	public int getClip(int x, int y) {
		return this.clip[x][y];
	}

	/**
	 * Sets the flag for this chunk at the given coordinate to the given flag. This
	 * sets, and does not bitwise OR.
	 * 
	 * @param x    the x coordinate
	 * @param y    the y coordinate
	 * @param flag the flag value
	 */
	public void setFlag(int x, int y, int flag) {
		if (flag == 0 && this.flags == null)
			return; // It's assumed to be 0 already. This saves us allocating extra data.
		if (this.flags == null)
			this.flags = new byte[Size][Size];
		this.flags[x][y] = (byte) flag;
	}

	/**
	 * Returns the flags placed at the specified {@code (x, y)} coordinate
	 * arguments.
	 * 
	 * @param x the x coordinate, 0 to 7
	 * @param y the y coordinate, 0 to 7
	 * @return the flags or 0 if none
	 */
	public int getFlags(int x, int y) {
		if (this.flags == null)
			return 0;
		return this.flags[x][y];
	}
}
