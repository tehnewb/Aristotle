package com.framework.map;

import lombok.NonNull;

/**
 * The {@code RSMap} holds an array of regions.
 * 
 * @author Albert Beaupre
 */
public class RSMap {

	private static final int MaximumRegions = 60000;
	private final RSRegion[] regions = new RSRegion[MaximumRegions];
	private final RSCollision collision = new RSCollision(this);

	public boolean canTraverse(@NonNull RSLocation from, @NonNull RSLocation to) {
		return collision.canTraverse(from, to);
	}

	/**
	 * Sets the region to the given {@code region} object.
	 * 
	 * @param region the region to set
	 */
	public void setRegion(@NonNull RSRegion region) {
		if (region.getID() < 0 || region.getID() >= 60000)
			throw new IndexOutOfBoundsException("Region ID must be between 0 and 60000 inclusive");
		regions[region.getID()] = region;
	}

	/**
	 * Returns the region with the given {@code regionID}.
	 * 
	 * @param regionID the id corresponding to the region
	 * @return the region
	 */
	public RSRegion getRegion(int regionID) {
		if (regionID < 0 || regionID >= 60000)
			throw new IndexOutOfBoundsException("Region ID must be between 0 and 60000 inclusive");
		return regions[regionID];
	}

	/**
	 * Returns the chunk surrounding the given absolute coordinates. If a chunk with
	 * valid coordinates has not already been set to the region, it will
	 * automatically be created and stored so as to return a non-null chunk.
	 * 
	 * @param absoluteX the absolute x coordinate
	 * @param absoluteY the absolute y coordinate
	 * @param absoluteZ the absolute z coordinate
	 * @return the chunk at the given coordinates
	 */
	public RSChunk getChunk(int absoluteX, int absoluteY, int absoluteZ) {
		RSLocation location = new RSLocation(absoluteX, absoluteY, absoluteZ);
		RSRegion region = regions[location.getRegionID()];
		RSChunk chunk = region.getChunk(location.getChunkX() % RSRegion.Size, location.getChunkY() % RSRegion.Size, absoluteZ);
		if (chunk == null) {
			region.setChunk(chunk = new RSChunk(location.getChunkX(), location.getChunkY(), absoluteZ));
		}
		return chunk;
	}

	/**
	 * Returns the clip value to the chunk based on the given absolute coordinates.
	 * 
	 * @param absoluteX the
	 * @param absoluteX the absolute x coordinate
	 * @param absoluteY the absolute y coordinate
	 * @param absoluteZ the absolute z coordinate
	 * @return the clip
	 */
	public int getClip(int absoluteX, int absoluteY, int absoluteZ) {
		RSChunk chunk = getChunk(absoluteX, absoluteY, absoluteZ);
		return chunk.getClip(absoluteX % RSChunk.Size, absoluteY % RSChunk.Size);
	}

	/**
	 * Adds the given {@code clip} to the chunk based on the given absolute
	 * coordinates.
	 * 
	 * @param absoluteX the absolute x coordinate
	 * @param absoluteY the absolute y coordinate
	 * @param absoluteZ the absolute z coordinate
	 * @param clip      the clip value to add
	 */
	public void addClip(int absoluteX, int absoluteY, int absoluteZ, int clip) {
		RSChunk chunk = getChunk(absoluteX, absoluteY, absoluteZ);
		chunk.addClip(absoluteX % RSChunk.Size, absoluteY % RSChunk.Size, clip);
	}

	/**
	 * Removes the given {@code clip} from the chunk based on the given absolute
	 * coordinates.
	 * 
	 * @param absoluteX the absolute x coordinate
	 * @param absoluteY the absolute y coordinate
	 * @param absoluteZ the absolute z coordinate
	 * @param clip      the clip value to remove
	 */
	public void removeClip(int absoluteX, int absoluteY, int absoluteZ, int clip) {
		RSChunk chunk = getChunk(absoluteX, absoluteY, absoluteZ);
		chunk.removeClip(absoluteX % RSChunk.Size, absoluteY % RSChunk.Size, clip);
	}

	/**
	 * Returns the collision flags set to the chunk based on the given absolute
	 * coordinates.
	 * 
	 * @param absoluteX the absolute x coordinate
	 * @param absoluteY the absolute y coordinate
	 * @param absoluteZ the absolute z coordinate
	 * @return the collision flags
	 */
	public int getCollisionFlags(int absoluteX, int absoluteY, int absoluteZ) {
		RSChunk chunk = getChunk(absoluteX, absoluteY, absoluteZ);
		return chunk.getFlags(absoluteX % RSChunk.Size, absoluteY % RSChunk.Size);
	}

	/**
	 * Sets the given {@code flag} value to the chunk based on the given absolute
	 * coordinates.
	 * 
	 * @param absoluteX the absolute x coordinate
	 * @param absoluteY the absolute y coordinate
	 * @param absoluteZ the absolute z coordinate
	 * @param flag      the flag to set
	 */
	public void setCollisionFlags(int absoluteX, int absoluteY, int absoluteZ, int flag) {
		RSChunk chunk = getChunk(absoluteX, absoluteY, absoluteZ);
		chunk.setFlag(absoluteX % RSChunk.Size, absoluteY % RSChunk.Size, flag);
	}

	/**
	 * Sets the npc flag to the given {@code flagged} value on the chunk based on
	 * the given absolute coordinates.
	 * 
	 * @param absoluteX the absolute x coordinate
	 * @param absoluteY the absolute y coordinate
	 * @param absoluteZ the absolute z coordinate
	 * @param flagged   the flag value to set
	 */
	public void setNPCFlag(int absoluteX, int absoluteY, int absoluteZ, boolean flagged) {
		RSChunk chunk = getChunk(absoluteX, absoluteY, absoluteZ);
		if (flagged) {
			chunk.setNPCFlag(absoluteX % RSChunk.Size, absoluteY % RSChunk.Size);
		} else {
			chunk.removeNPCFlag(absoluteX % RSChunk.Size, absoluteY % RSChunk.Size);
		}
	}

	/**
	 * Returns true if the npc flag at the given absolute coordinates is set to
	 * true. Returns false otherwise.
	 * 
	 * @param absoluteX the absolute x coordinate
	 * @param absoluteY the absolute y coordinate
	 * @param absoluteZ the absolute z coordinate
	 * @return true if npc flag set; false otherwise
	 */
	public boolean hasNPCFlag(int absoluteX, int absoluteY, int absoluteZ) {
		RSChunk chunk = getChunk(absoluteX, absoluteY, absoluteZ);
		return chunk.hasNPCFlag(absoluteX % RSChunk.Size, absoluteY % RSChunk.Size);
	}
}
