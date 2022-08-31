package versions.ver637.map;

import java.util.BitSet;

import com.displee.cache.CacheLibrary;
import com.framework.io.RSStream;
import com.framework.io.XTEAKey;
import com.framework.map.RSChunk;
import com.framework.map.RSCollision;
import com.framework.map.RSLocation;
import com.framework.map.RSRegion;
import com.framework.resource.RSResource;

import lombok.RequiredArgsConstructor;
import versions.ver637.cache.CacheResource;
import versions.ver637.cache.XTEAResource;

/**
 * This resource is used to load regions. Do not queue this resource unless the
 * CacheResource and XTEAResource have been loaded.
 * 
 * @author Albert Beaupre
 */
@RequiredArgsConstructor
public class RegionResource implements RSResource<RSRegion> {

	/*
	 * This is to avoid queuing for the same region resource and causing issues if
	 * for some reason this is requested on different threads
	 */
	private static final BitSet loaded = new BitSet(60000);

	private final int regionID;

	@Override
	public void finish(RSRegion region) {
		if (region == null)
			return;

		WorldMap.getMap().setRegion(region);
	}

	@Override
	public RSRegion load() throws Exception {
		if (loaded.get(regionID))
			return null;

		loaded.set(regionID, true);

		XTEAKey key = XTEAResource.getKey(regionID);
		CacheLibrary library = CacheResource.getLibrary();
		RSRegion region = new RSRegion(regionID);

		int regionX = region.getRegionX();
		int regionY = region.getRegionY();
		int relativeX = regionX << 6;
		int relativeY = regionY << 6;

		byte[] landArr = library.data(CacheResource.MAPS, "m" + regionX + "_" + regionY);
		byte[] objArr = library.data(CacheResource.MAPS, "l" + regionX + "_" + regionY, key.getKeys());

		final int FLAG_BRIDGE = 0x2;
		final int FLAG_CLIP = 0x1;
		byte[][][] flags = new byte[64][64][4];

		if (landArr != null) {
			RSStream landStream = new RSStream(landArr);
			for (int localZ = 0; localZ < 4; localZ++) {
				for (int localX = 0; localX < 64; localX++) {
					for (int localY = 0; localY < 64; localY++) {
						while (true) {
							int v = landStream.readByte() & 0xff;
							if (v == 0) {
								break;
							} else if (v == 1) {
								landStream.readByte();
								break;
							} else if (v <= 49) {
								landStream.readByte();
							} else if (v <= 81) {
								flags[localX][localY][localZ] = (byte) (v - 49);
							}
						}
					}
				}
			}
			for (int localZ = 0; localZ < 4; localZ++) {
				for (int localX = 0; localX < 64; localX++) {
					for (int localY = 0; localY < 64; localY++) {
						if ((flags[localX][localY][localZ] & FLAG_CLIP) == FLAG_CLIP) {
							int height = localZ;
							if ((flags[localX][localY][1] & FLAG_BRIDGE) == FLAG_BRIDGE) {
								height--;
							}

							if (height >= 0 && height <= 3) {
								WorldMap.getMap().addFlags(relativeX + localX, relativeY + localY, height, RSCollision.BLOCKED);
							}
						}
					}
				}
			}
		}

		if (objArr != null) {
			RSStream objStream = new RSStream(objArr);
			int objectID = -1;
			int incr;
			while ((incr = objStream.readExtendedSmart()) != 0) {
				objectID += incr;
				int location = 0;
				int incr2;
				while ((incr2 = objStream.readUnsignedSmart()) != 0) {
					location += incr2 - 1;
					int localX = location >> 6 & 0x3f;
					int localY = location & 0x3f;
					int height = location >> 12;
					int objectHash = objStream.readUnsignedByte();
					int type = objectHash >> 2;
					int rotation = objectHash & 0x3;
					if (localX < 0 || localX >= RSChunk.TileCount || localY < 0 || localY >= RSChunk.TileCount)
						continue;

					if ((flags[localX][localY][1] & FLAG_BRIDGE) == FLAG_BRIDGE)
						height--;

					if (height >= 0 && height <= 3) {
						final int absoluteX = (regionX << 6) + localX;
						final int absoluteY = (regionY << 6) + localY;
						WorldMap.getMap().addGameObject(new GameObject(objectID, new RSLocation(absoluteX, absoluteY, height), type, rotation));
					}
				}
			}
		}
		return region;
	}

}
