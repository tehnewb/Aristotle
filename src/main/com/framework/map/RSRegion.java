package com.framework.map;

import lombok.Getter;
import lombok.NonNull;

/**
 * The {@code RSRegion} contains 64 RSChunks in an 8x8 chunk area.
 * 
 * @author Albert Beaupre
 */
public class RSRegion {

	public static final int Size = 8;
	public static final int Bits = 6;
	public static final int MaximumHeight = 4;

	private RSChunk[][][] chunks = new RSChunk[Size][Size][MaximumHeight];

	@Getter
	private final int ID;

	/**
	 * Constructs a new {@code RSRegion} with the given {@code ID}.
	 * 
	 * @param ID the region ID
	 */
	public RSRegion(int ID) {
		this.ID = ID;
	}

	/**
	 * Sets the given {@code chunk} to this region.
	 * 
	 * @param chunk the chunk to set
	 */
	public void setChunk(@NonNull RSChunk chunk) {
		if (chunk.getChunkZ() < 0 || chunk.getChunkZ() >= MaximumHeight)
			throw new IndexOutOfBoundsException("Chunk Z must be between 0 and 3 inclusive");
		this.chunks[chunk.getChunkX() % Size][chunk.getChunkY() % Size][chunk.getChunkZ()] = chunk;
	}

	/**
	 * Returns the chunk set at the given chunk coordinates. This value can return
	 * null if the chunk has not been set.
	 * 
	 * @param chunkX the chunk x coordinate
	 * @param chunkY the chunk y coordinate
	 * @param chunkZ the chunk z coordinate
	 * @return the chunk set; possibly null
	 */
	public RSChunk getChunk(int chunkX, int chunkY, int chunkZ) {
		if (chunkZ < 0 || chunkZ >= MaximumHeight)
			throw new IndexOutOfBoundsException("Chunk Z must be between 0 and 3 inclusive");
		return this.chunks[chunkX % Size][chunkY % Size][chunkZ];
	}

	/**
	 * Returns the relative region x coordinate
	 * 
	 * @return the relative x coordinate
	 */
	public int getRegionX() {
		return ID >> 8;
	}

	/**
	 * Returns the relative region y coordinate
	 * 
	 * @return the relative y coordinate
	 */
	public int getRegionY() {
		return ID & 0xFF;
	}

}
