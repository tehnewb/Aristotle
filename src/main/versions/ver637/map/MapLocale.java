package versions.ver637.map;

import com.framework.map.RSCollision;
import com.framework.map.RSLocation;

import lombok.Getter;

@Getter
public class MapLocale {

	private final int ID;
	private final RSLocation location;
	private final int type;
	private final int rotation;
	private final LocaleData data;

	public MapLocale(int objectID, RSLocation location, int type, int rotation) {
		this.ID = objectID;
		this.location = location;
		this.type = type;
		this.rotation = rotation;
		this.data = LocaleResource.getData(objectID);
	}

	public void applyClip() {
		WorldMap map = WorldMap.getMap();
		int x = location.getX();
		int y = location.getY();
		int z = location.getZ();

		int xLength, yLength;

		if (rotation == 1 || rotation == 3) {
			xLength = data.getSizeX();
			yLength = data.getSizeY();
		} else {
			xLength = data.getSizeY();
			yLength = data.getSizeX();
		}

		if (type == 22) {
			if (data.getActionType() == 1) {
				map.addClip(x, y, z, RSCollision.BLOCKED);
			}
		} else if (type >= 9 && type <= 11) {
			if (data.getActionType() != 0) {
				int clippingMask = 0xFF;
				if (data.isSolid()) {
					clippingMask |= RSCollision.SOLID;
				}
				if (!data.isBlockRangeFlag()) {
					clippingMask |= RSCollision.ALLOW_RANGE;
				}
				for (int totalX = x; totalX < x + xLength; totalX++) {
					for (int totalY = y; totalY < y + yLength; totalY++) {
						map.addClip(totalX, totalY, z, clippingMask);
					}
				}
			}
		} else if (type >= 0 && type <= 3) {
			if (data.getActionType() != 0) {
				if (type == 0) {
					if (rotation == 0) {
						map.addClip(x, y, z, 128);
						map.addClip(x - 1, y, z, 8);
					}
					if (rotation == 1) {
						map.addClip(x, y, z, 2);
						map.addClip(x, y + 1, z, 32);
					}
					if (rotation == 2) {
						map.addClip(x, y, z, 8);
						map.addClip(x + 1, y, z, 128);
					}
					if (rotation == 3) {
						map.addClip(x, y, z, 32);
						map.addClip(x, y - 1, z, 2);
					}
				}
				if (type == 1 || type == 3) {
					if (rotation == 0) {
						map.addClip(x, y, z, 1);
						map.addClip(x - 1, y + 1, z, 16);
					}
					if (rotation == 1) {
						map.addClip(x, y, z, 4);
						map.addClip(x + 1, y + 1, z, 64);
					}
					if (rotation == 2) {
						map.addClip(x, y, z, 16);
						map.addClip(x + 1, y - 1, z, 1);
					}
					if (rotation == 3) {
						map.addClip(x, y, z, 64);
						map.addClip(x - 1, y - 1, z, 4);
					}
				}
				if (type == 2) {
					if (rotation == 0) {
						map.addClip(x, y, z, 130);
						map.addClip(x - 1, y, z, 8);
						map.addClip(x, y + 1, z, 32);
					}
					if (rotation == 1) {
						map.addClip(x, y, z, 10);
						map.addClip(x, y + 1, z, 32);
						map.addClip(x + 1, y, z, 128);
					}
					if (rotation == 2) {
						map.addClip(x, y, z, 40);
						map.addClip(x + 1, y, z, 128);
						map.addClip(x, y - 1, z, 2);
					}
					if (rotation == 3) {
						map.addClip(x, y, z, 160);
						map.addClip(x, y - 1, z, 2);
						map.addClip(x - 1, y, z, 8);
					}
				}
				if (data.isSolid()) {
					if (type == 0) {
						if (rotation == 0) {
							map.addClip(x, y, z, 0x10000);
							map.addClip(x - 1, y, z, 4096);
						}
						if (rotation == 1) {
							map.addClip(x, y, z, 1024);
							map.addClip(x, y + 1, z, 16384);
						}
						if (rotation == 2) {
							map.addClip(x, y, z, 4096);
							map.addClip(x + 1, y, z, 0x10000);
						}
						if (rotation == 3) {
							map.addClip(x, y, z, 16384);
							map.addClip(x, y - 1, z, 1024);
						}
					}
					if (type == 1 || type == 3) {
						if (rotation == 0) {
							map.addClip(x, y, z, 512);
							map.addClip(x - 1, y + 1, z, 8192);
						}
						if (rotation == 1) {
							map.addClip(x, y, z, 2048);
							map.addClip(x + 1, y + 1, z, 32768);
						}
						if (rotation == 2) {
							map.addClip(x, y, z, 8192);
							map.addClip(x + 1, y - 1, z, 512);
						}
						if (rotation == 3) {
							map.addClip(x, y, z, 32768);
							map.addClip(x - 1, y - 1, z, 2048);
						}
					}
					if (type == 2) {
						if (rotation == 0) {
							map.addClip(x, y, z, 0x10400);
							map.addClip(x - 1, y, z, 4096);
							map.addClip(x, y + 1, z, 16384);
						}
						if (rotation == 1) {
							map.addClip(x, y, z, 5120);
							map.addClip(x, y + 1, z, 16384);
							map.addClip(x + 1, y, z, 0x10000);
						}
						if (rotation == 2) {
							map.addClip(x, y, z, 20480);
							map.addClip(x + 1, y, z, 0x10000);
							map.addClip(x, y - 1, z, 1024);
						}
						if (rotation == 3) {
							map.addClip(x, y, z, 0x14000);
							map.addClip(x, y - 1, z, 1024);
							map.addClip(x - 1, y, z, 4096);
						}
					}
				}
				if (!data.isBlockRangeFlag()) {
					if (type == 0) {
						if (rotation == 0) {
							map.addClip(x, y, z, RSCollision.RANGE_WEST);
							map.addClip(x - 1, y, z, RSCollision.RANGE_EAST);
						}
						if (rotation == 1) {
							map.addClip(x, y, z, RSCollision.RANGE_NORTH);
							map.addClip(x, y + 1, z, RSCollision.RANGE_SOUTH);
						}
						if (rotation == 2) {
							map.addClip(x, y, z, RSCollision.RANGE_EAST);
							map.addClip(x + 1, y, z, RSCollision.RANGE_WEST);
						}
						if (rotation == 3) {
							map.addClip(x, y, z, RSCollision.RANGE_SOUTH);
							map.addClip(x, y - 1, z, RSCollision.RANGE_NORTH);
						}
					}
					if (type == 1 || type == 3) {
						if (rotation == 0) {
							map.addClip(x, y, z, RSCollision.RANGE_NORTH_WEST);
							map.addClip(x - 1, y + 1, z, RSCollision.RANGE_SOUTH_EAST);
						}
						if (rotation == 1) {
							map.addClip(x, y, z, RSCollision.RANGE_NORTH_EAST);
							map.addClip(1 + x, 1 + y, z, RSCollision.RANGE_SOUTH_WEST);
						}
						if (rotation == 2) {
							map.addClip(x, y, z, RSCollision.RANGE_SOUTH_EAST);
							map.addClip(x + 1, -1 + y, z, RSCollision.RANGE_NORTH_WEST);
						}
						if (rotation == 3) {
							map.addClip(x, y, z, RSCollision.RANGE_SOUTH_WEST);
							map.addClip(-1 + x, y - 1, z, RSCollision.RANGE_NORTH_EAST);
						}
					}
					if (type == 2) {
						if (rotation == 0) {
							map.addClip(x, y, z, RSCollision.RANGE_WEST | RSCollision.RANGE_NORTH);
							map.addClip(-1 + x, y, z, RSCollision.RANGE_EAST);
							map.addClip(x, 1 + y, z, RSCollision.RANGE_SOUTH);
						}
						if (rotation == 1) {
							map.addClip(x, y, z, RSCollision.RANGE_NORTH | RSCollision.RANGE_EAST);
							map.addClip(x, 1 + y, z, RSCollision.RANGE_SOUTH);
							map.addClip(x + 1, y, z, RSCollision.RANGE_WEST);
						}
						if (rotation == 2) {
							map.addClip(x, y, z, RSCollision.RANGE_EAST | RSCollision.RANGE_SOUTH);
							map.addClip(1 + x, y, z, RSCollision.RANGE_WEST);
							map.addClip(x, y - 1, z, RSCollision.RANGE_NORTH);
						}
						if (rotation == 3) {
							map.addClip(x, y, z, RSCollision.RANGE_SOUTH | RSCollision.RANGE_WEST);
							map.addClip(x, y - 1, z, RSCollision.RANGE_NORTH);
							map.addClip(-1 + x, y, z, RSCollision.RANGE_WEST);
						}
					}
				}
			}
		}
	}

	public void removeClip() {
		WorldMap map = WorldMap.getMap();
		int x = location.getX();
		int y = location.getY();
		int z = location.getZ();

		int xLength, yLength;

		if (rotation == 1 || rotation == 3) {
			xLength = data.getSizeX();
			yLength = data.getSizeY();
		} else {
			xLength = data.getSizeY();
			yLength = data.getSizeX();
		}

		if (type == 22) {
			if (data.getActionType() == 1) {
				map.removeClip(x, y, z, RSCollision.BLOCKED);
			}
		} else if (type >= 9 && type <= 11) {
			if (data.getActionType() != 0) {
				int clippingMask = 0xFF;
				if (data.isSolid()) {
					clippingMask |= RSCollision.SOLID;
				}
				if (!data.isBlockRangeFlag()) {
					clippingMask |= RSCollision.ALLOW_RANGE;
				}
				for (int totalX = x; totalX < x + xLength; totalX++) {
					for (int totalY = y; totalY < y + yLength; totalY++) {
						map.removeClip(totalX, totalY, z, clippingMask);
					}
				}
			}
		} else if (type >= 0 && type <= 3) {
			if (data.getActionType() != 0) {
				if (type == 0) {
					if (rotation == 0) {
						map.removeClip(x, y, z, 128);
						map.removeClip(x - 1, y, z, 8);
					}
					if (rotation == 1) {
						map.removeClip(x, y, z, 2);
						map.removeClip(x, y + 1, z, 32);
					}
					if (rotation == 2) {
						map.removeClip(x, y, z, 8);
						map.removeClip(x + 1, y, z, 128);
					}
					if (rotation == 3) {
						map.removeClip(x, y, z, 32);
						map.removeClip(x, y - 1, z, 2);
					}
				}
				if (type == 1 || type == 3) {
					if (rotation == 0) {
						map.removeClip(x, y, z, 1);
						map.removeClip(x - 1, y + 1, z, 16);
					}
					if (rotation == 1) {
						map.removeClip(x, y, z, 4);
						map.removeClip(x + 1, y + 1, z, 64);
					}
					if (rotation == 2) {
						map.removeClip(x, y, z, 16);
						map.removeClip(x + 1, y - 1, z, 1);
					}
					if (rotation == 3) {
						map.removeClip(x, y, z, 64);
						map.removeClip(x - 1, y - 1, z, 4);
					}
				}
				if (type == 2) {
					if (rotation == 0) {
						map.removeClip(x, y, z, 130);
						map.removeClip(x - 1, y, z, 8);
						map.removeClip(x, y + 1, z, 32);
					}
					if (rotation == 1) {
						map.removeClip(x, y, z, 10);
						map.removeClip(x, y + 1, z, 32);
						map.removeClip(x + 1, y, z, 128);
					}
					if (rotation == 2) {
						map.removeClip(x, y, z, 40);
						map.removeClip(x + 1, y, z, 128);
						map.removeClip(x, y - 1, z, 2);
					}
					if (rotation == 3) {
						map.removeClip(x, y, z, 160);
						map.removeClip(x, y - 1, z, 2);
						map.removeClip(x - 1, y, z, 8);
					}
				}
				if (data.isSolid()) {
					if (type == 0) {
						if (rotation == 0) {
							map.removeClip(x, y, z, 0x10000);
							map.removeClip(x - 1, y, z, 4096);
						}
						if (rotation == 1) {
							map.removeClip(x, y, z, 1024);
							map.removeClip(x, y + 1, z, 16384);
						}
						if (rotation == 2) {
							map.removeClip(x, y, z, 4096);
							map.removeClip(x + 1, y, z, 0x10000);
						}
						if (rotation == 3) {
							map.removeClip(x, y, z, 16384);
							map.removeClip(x, y - 1, z, 1024);
						}
					}
					if (type == 1 || type == 3) {
						if (rotation == 0) {
							map.removeClip(x, y, z, 512);
							map.removeClip(x - 1, y + 1, z, 8192);
						}
						if (rotation == 1) {
							map.removeClip(x, y, z, 2048);
							map.removeClip(x + 1, y + 1, z, 32768);
						}
						if (rotation == 2) {
							map.removeClip(x, y, z, 8192);
							map.removeClip(x + 1, y - 1, z, 512);
						}
						if (rotation == 3) {
							map.removeClip(x, y, z, 32768);
							map.removeClip(x - 1, y - 1, z, 2048);
						}
					}
					if (type == 2) {
						if (rotation == 0) {
							map.removeClip(x, y, z, 0x10400);
							map.removeClip(x - 1, y, z, 4096);
							map.removeClip(x, y + 1, z, 16384);
						}
						if (rotation == 1) {
							map.removeClip(x, y, z, 5120);
							map.removeClip(x, y + 1, z, 16384);
							map.removeClip(x + 1, y, z, 0x10000);
						}
						if (rotation == 2) {
							map.removeClip(x, y, z, 20480);
							map.removeClip(x + 1, y, z, 0x10000);
							map.removeClip(x, y - 1, z, 1024);
						}
						if (rotation == 3) {
							map.removeClip(x, y, z, 0x14000);
							map.removeClip(x, y - 1, z, 1024);
							map.removeClip(x - 1, y, z, 4096);
						}
					}
				}
				if (!data.isBlockRangeFlag()) {
					if (type == 0) {
						if (rotation == 0) {
							map.removeClip(x, y, z, RSCollision.RANGE_WEST);
							map.removeClip(x - 1, y, z, RSCollision.RANGE_EAST);
						}
						if (rotation == 1) {
							map.removeClip(x, y, z, RSCollision.RANGE_NORTH);
							map.removeClip(x, y + 1, z, RSCollision.RANGE_SOUTH);
						}
						if (rotation == 2) {
							map.removeClip(x, y, z, RSCollision.RANGE_EAST);
							map.removeClip(x + 1, y, z, RSCollision.RANGE_WEST);
						}
						if (rotation == 3) {
							map.removeClip(x, y, z, RSCollision.RANGE_SOUTH);
							map.removeClip(x, y - 1, z, RSCollision.RANGE_NORTH);
						}
					}
					if (type == 1 || type == 3) {
						if (rotation == 0) {
							map.removeClip(x, y, z, RSCollision.RANGE_NORTH_WEST);
							map.removeClip(x - 1, y + 1, z, RSCollision.RANGE_SOUTH_EAST);
						}
						if (rotation == 1) {
							map.removeClip(x, y, z, RSCollision.RANGE_NORTH_EAST);
							map.removeClip(1 + x, 1 + y, z, RSCollision.RANGE_SOUTH_WEST);
						}
						if (rotation == 2) {
							map.removeClip(x, y, z, RSCollision.RANGE_SOUTH_EAST);
							map.removeClip(x + 1, -1 + y, z, RSCollision.RANGE_NORTH_WEST);
						}
						if (rotation == 3) {
							map.removeClip(x, y, z, RSCollision.RANGE_SOUTH_WEST);
							map.removeClip(-1 + x, y - 1, z, RSCollision.RANGE_NORTH_EAST);
						}
					}
					if (type == 2) {
						if (rotation == 0) {
							map.removeClip(x, y, z, RSCollision.RANGE_WEST | RSCollision.RANGE_NORTH);
							map.removeClip(-1 + x, y, z, RSCollision.RANGE_EAST);
							map.removeClip(x, 1 + y, z, RSCollision.RANGE_SOUTH);
						}
						if (rotation == 1) {
							map.removeClip(x, y, z, RSCollision.RANGE_NORTH | RSCollision.RANGE_EAST);
							map.removeClip(x, 1 + y, z, RSCollision.RANGE_SOUTH);
							map.removeClip(x + 1, y, z, RSCollision.RANGE_WEST);
						}
						if (rotation == 2) {
							map.removeClip(x, y, z, RSCollision.RANGE_EAST | RSCollision.RANGE_SOUTH);
							map.removeClip(1 + x, y, z, RSCollision.RANGE_WEST);
							map.removeClip(x, y - 1, z, RSCollision.RANGE_NORTH);
						}
						if (rotation == 3) {
							map.removeClip(x, y, z, RSCollision.RANGE_SOUTH | RSCollision.RANGE_WEST);
							map.removeClip(x, y - 1, z, RSCollision.RANGE_NORTH);
							map.removeClip(-1 + x, y, z, RSCollision.RANGE_WEST);
						}
					}
				}
			}
		}
	}

	public int getSizeX() {
		if (this.rotation == 0 || this.rotation == 2) {
			return data.getSizeY();
		} else {
			return data.getSizeX();
		}
	}

	public int getSizeY() {
		if (this.rotation == 0 || this.rotation == 2) {
			return data.getSizeX();
		} else {
			return data.getSizeY();
		}
	}
}
