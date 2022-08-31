package versions.ver637.network.coders.frames;

import com.framework.RSFramework;
import com.framework.map.RSChunk;
import com.framework.map.RSLocation;
import com.framework.network.RSFrame;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import versions.ver637.cache.XTEAResource;
import versions.ver637.map.RegionResource;
import versions.ver637.model.player.Player;

@Accessors(fluent = true, chain = true)
@Setter
public class RegionFrame extends RSFrame {

	public enum RegionView {
		/**
		 * Small, 104 tiles, or 6 chunks to the left and right of the player.
		 */
		Small((short) 104),
		/**
		 * Medium, 120 tiles, or 7 chunks to the left and right of the player.
		 */
		Medium((short) 120),
		/**
		 * Large, 136 tiles, or 8 chunks to the left and right of the player.
		 */
		Large((short) 136),
		/**
		 * Huge, 168 tiles, or 10 chunks to the left and right of the player.
		 */
		Huge((short) 168);

		@Getter
		private final short tiles;

		private RegionView(short tiles) {
			this.tiles = tiles;
		}
	}

	/**
	 * The opcode of the region frame
	 */
	private static final short RegionOpcode = 80;

	/**
	 * Constructs a new {@code RegionFrame} based on the given chunk coordinates.
	 * 
	 * @param chunkX the x coordinate relative to the chunk
	 * @param chunkY the y coordinate relative to the chunk
	 */
	public RegionFrame(Player player, RegionView view, boolean forceNextMapLoad, boolean initialMapLoad) {
		super(RegionOpcode, VarShortType);

		RSLocation location = player.getLocation();
		if (initialMapLoad) {
			beginBitAccess();
			writeBits(30, location.packTo30Bits());
			for (int i = 1; i < 2048; i++) {
				Player other = Player.get(i);
				if (i == player.getIndex())
					continue;
				writeBits(18, other == null ? 0 : other.getLocation().packTo18Bits());
			}
			finishBitAccess();
		}

		int chunkX = location.getChunkX();
		int chunkY = location.getChunkY();

		writeLEShortA(chunkY);
		writeShortA(chunkX);
		writeByte(view.ordinal());
		writeByteA(forceNextMapLoad ? 1 : 0);

		int radius = view.tiles() >> 4;
		for (int regionX = (chunkX - radius) >> 3; regionX <= (chunkX + radius) >> 3; regionX++) {
			for (int regionY = (chunkY - radius) >> 3; regionY <= (chunkY + radius) >> 3; regionY++) {
				int regionID = regionY | (regionX << RSChunk.TilePlaneSize);
				RSFramework.queueResource(new RegionResource(regionID));

				int[] keys = XTEAResource.getKey(regionID).getKeys();
				for (int i = 0; i < 4; i++) {
					writeInt(keys[i]);
				}
			}
		}
	}

}
