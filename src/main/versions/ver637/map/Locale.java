package versions.ver637.map;

import com.framework.map.RSCollision;
import com.framework.map.RSLocation;

import lombok.Getter;

@Getter
public class Locale {

	private final int ID;
	private final RSLocation location;
	private final int type;
	private final int rotation;
	private final LocaleData data;

	public Locale(int objectID, RSLocation location, int type, int rotation) {
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
				map.addFlags(x, y, z, RSCollision.BLOCKED);
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
						map.addFlags(totalX, totalY, z, clippingMask);
					}
				}
			}
		} else if (type >= 0 && type <= 3) {
			if (data.getActionType() != 0) {
				if (type == 0) {
					if (rotation == 0) {
						map.addFlags(x, y, z, 128);
						map.addFlags(x - 1, y, z, 8);
					}
					if (rotation == 1) {
						map.addFlags(x, y, z, 2);
						map.addFlags(x, y + 1, z, 32);
					}
					if (rotation == 2) {
						map.addFlags(x, y, z, 8);
						map.addFlags(x + 1, y, z, 128);
					}
					if (rotation == 3) {
						map.addFlags(x, y, z, 32);
						map.addFlags(x, y - 1, z, 2);
					}
				}
				if (type == 1 || type == 3) {
					if (rotation == 0) {
						map.addFlags(x, y, z, 1);
						map.addFlags(x - 1, y + 1, z, 16);
					}
					if (rotation == 1) {
						map.addFlags(x, y, z, 4);
						map.addFlags(x + 1, y + 1, z, 64);
					}
					if (rotation == 2) {
						map.addFlags(x, y, z, 16);
						map.addFlags(x + 1, y - 1, z, 1);
					}
					if (rotation == 3) {
						map.addFlags(x, y, z, 64);
						map.addFlags(x - 1, y - 1, z, 4);
					}
				}
				if (type == 2) {
					if (rotation == 0) {
						map.addFlags(x, y, z, 130);
						map.addFlags(x - 1, y, z, 8);
						map.addFlags(x, y + 1, z, 32);
					}
					if (rotation == 1) {
						map.addFlags(x, y, z, 10);
						map.addFlags(x, y + 1, z, 32);
						map.addFlags(x + 1, y, z, 128);
					}
					if (rotation == 2) {
						map.addFlags(x, y, z, 40);
						map.addFlags(x + 1, y, z, 128);
						map.addFlags(x, y - 1, z, 2);
					}
					if (rotation == 3) {
						map.addFlags(x, y, z, 160);
						map.addFlags(x, y - 1, z, 2);
						map.addFlags(x - 1, y, z, 8);
					}
				}
				if (data.isSolid()) {
					if (type == 0) {
						if (rotation == 0) {
							map.addFlags(x, y, z, 0x10000);
							map.addFlags(x - 1, y, z, 4096);
						}
						if (rotation == 1) {
							map.addFlags(x, y, z, 1024);
							map.addFlags(x, y + 1, z, 16384);
						}
						if (rotation == 2) {
							map.addFlags(x, y, z, 4096);
							map.addFlags(x + 1, y, z, 0x10000);
						}
						if (rotation == 3) {
							map.addFlags(x, y, z, 16384);
							map.addFlags(x, y - 1, z, 1024);
						}
					}
					if (type == 1 || type == 3) {
						if (rotation == 0) {
							map.addFlags(x, y, z, 512);
							map.addFlags(x - 1, y + 1, z, 8192);
						}
						if (rotation == 1) {
							map.addFlags(x, y, z, 2048);
							map.addFlags(x + 1, y + 1, z, 32768);
						}
						if (rotation == 2) {
							map.addFlags(x, y, z, 8192);
							map.addFlags(x + 1, y - 1, z, 512);
						}
						if (rotation == 3) {
							map.addFlags(x, y, z, 32768);
							map.addFlags(x - 1, y - 1, z, 2048);
						}
					}
					if (type == 2) {
						if (rotation == 0) {
							map.addFlags(x, y, z, 0x10400);
							map.addFlags(x - 1, y, z, 4096);
							map.addFlags(x, y + 1, z, 16384);
						}
						if (rotation == 1) {
							map.addFlags(x, y, z, 5120);
							map.addFlags(x, y + 1, z, 16384);
							map.addFlags(x + 1, y, z, 0x10000);
						}
						if (rotation == 2) {
							map.addFlags(x, y, z, 20480);
							map.addFlags(x + 1, y, z, 0x10000);
							map.addFlags(x, y - 1, z, 1024);
						}
						if (rotation == 3) {
							map.addFlags(x, y, z, 0x14000);
							map.addFlags(x, y - 1, z, 1024);
							map.addFlags(x - 1, y, z, 4096);
						}
					}
				}
				if (!data.isBlockRangeFlag()) {
					if (type == 0) {
						if (rotation == 0) {
							map.addFlags(x, y, z, RSCollision.RANGE_WEST);
							map.addFlags(x - 1, y, z, RSCollision.RANGE_EAST);
						}
						if (rotation == 1) {
							map.addFlags(x, y, z, RSCollision.RANGE_NORTH);
							map.addFlags(x, y + 1, z, RSCollision.RANGE_SOUTH);
						}
						if (rotation == 2) {
							map.addFlags(x, y, z, RSCollision.RANGE_EAST);
							map.addFlags(x + 1, y, z, RSCollision.RANGE_WEST);
						}
						if (rotation == 3) {
							map.addFlags(x, y, z, RSCollision.RANGE_SOUTH);
							map.addFlags(x, y - 1, z, RSCollision.RANGE_NORTH);
						}
					}
					if (type == 1 || type == 3) {
						if (rotation == 0) {
							map.addFlags(x, y, z, RSCollision.RANGE_NORTH_WEST);
							map.addFlags(x - 1, y + 1, z, RSCollision.RANGE_SOUTH_EAST);
						}
						if (rotation == 1) {
							map.addFlags(x, y, z, RSCollision.RANGE_NORTH_EAST);
							map.addFlags(1 + x, 1 + y, z, RSCollision.RANGE_SOUTH_WEST);
						}
						if (rotation == 2) {
							map.addFlags(x, y, z, RSCollision.RANGE_SOUTH_EAST);
							map.addFlags(x + 1, -1 + y, z, RSCollision.RANGE_NORTH_WEST);
						}
						if (rotation == 3) {
							map.addFlags(x, y, z, RSCollision.RANGE_SOUTH_WEST);
							map.addFlags(-1 + x, y - 1, z, RSCollision.RANGE_NORTH_EAST);
						}
					}
					if (type == 2) {
						if (rotation == 0) {
							map.addFlags(x, y, z, RSCollision.RANGE_WEST | RSCollision.RANGE_NORTH);
							map.addFlags(-1 + x, y, z, RSCollision.RANGE_EAST);
							map.addFlags(x, 1 + y, z, RSCollision.RANGE_SOUTH);
						}
						if (rotation == 1) {
							map.addFlags(x, y, z, RSCollision.RANGE_NORTH | RSCollision.RANGE_EAST);
							map.addFlags(x, 1 + y, z, RSCollision.RANGE_SOUTH);
							map.addFlags(x + 1, y, z, RSCollision.RANGE_WEST);
						}
						if (rotation == 2) {
							map.addFlags(x, y, z, RSCollision.RANGE_EAST | RSCollision.RANGE_SOUTH);
							map.addFlags(1 + x, y, z, RSCollision.RANGE_WEST);
							map.addFlags(x, y - 1, z, RSCollision.RANGE_NORTH);
						}
						if (rotation == 3) {
							map.addFlags(x, y, z, RSCollision.RANGE_SOUTH | RSCollision.RANGE_WEST);
							map.addFlags(x, y - 1, z, RSCollision.RANGE_NORTH);
							map.addFlags(-1 + x, y, z, RSCollision.RANGE_WEST);
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
				map.removeFlags(x, y, z, RSCollision.BLOCKED);
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
						map.removeFlags(totalX, totalY, z, clippingMask);
					}
				}
			}
		} else if (type >= 0 && type <= 3) {
			if (data.getActionType() != 0) {
				if (type == 0) {
					if (rotation == 0) {
						map.removeFlags(x, y, z, 128);
						map.removeFlags(x - 1, y, z, 8);
					}
					if (rotation == 1) {
						map.removeFlags(x, y, z, 2);
						map.removeFlags(x, y + 1, z, 32);
					}
					if (rotation == 2) {
						map.removeFlags(x, y, z, 8);
						map.removeFlags(x + 1, y, z, 128);
					}
					if (rotation == 3) {
						map.removeFlags(x, y, z, 32);
						map.removeFlags(x, y - 1, z, 2);
					}
				}
				if (type == 1 || type == 3) {
					if (rotation == 0) {
						map.removeFlags(x, y, z, 1);
						map.removeFlags(x - 1, y + 1, z, 16);
					}
					if (rotation == 1) {
						map.removeFlags(x, y, z, 4);
						map.removeFlags(x + 1, y + 1, z, 64);
					}
					if (rotation == 2) {
						map.removeFlags(x, y, z, 16);
						map.removeFlags(x + 1, y - 1, z, 1);
					}
					if (rotation == 3) {
						map.removeFlags(x, y, z, 64);
						map.removeFlags(x - 1, y - 1, z, 4);
					}
				}
				if (type == 2) {
					if (rotation == 0) {
						map.removeFlags(x, y, z, 130);
						map.removeFlags(x - 1, y, z, 8);
						map.removeFlags(x, y + 1, z, 32);
					}
					if (rotation == 1) {
						map.removeFlags(x, y, z, 10);
						map.removeFlags(x, y + 1, z, 32);
						map.removeFlags(x + 1, y, z, 128);
					}
					if (rotation == 2) {
						map.removeFlags(x, y, z, 40);
						map.removeFlags(x + 1, y, z, 128);
						map.removeFlags(x, y - 1, z, 2);
					}
					if (rotation == 3) {
						map.removeFlags(x, y, z, 160);
						map.removeFlags(x, y - 1, z, 2);
						map.removeFlags(x - 1, y, z, 8);
					}
				}
				if (data.isSolid()) {
					if (type == 0) {
						if (rotation == 0) {
							map.removeFlags(x, y, z, 0x10000);
							map.removeFlags(x - 1, y, z, 4096);
						}
						if (rotation == 1) {
							map.removeFlags(x, y, z, 1024);
							map.removeFlags(x, y + 1, z, 16384);
						}
						if (rotation == 2) {
							map.removeFlags(x, y, z, 4096);
							map.removeFlags(x + 1, y, z, 0x10000);
						}
						if (rotation == 3) {
							map.removeFlags(x, y, z, 16384);
							map.removeFlags(x, y - 1, z, 1024);
						}
					}
					if (type == 1 || type == 3) {
						if (rotation == 0) {
							map.removeFlags(x, y, z, 512);
							map.removeFlags(x - 1, y + 1, z, 8192);
						}
						if (rotation == 1) {
							map.removeFlags(x, y, z, 2048);
							map.removeFlags(x + 1, y + 1, z, 32768);
						}
						if (rotation == 2) {
							map.removeFlags(x, y, z, 8192);
							map.removeFlags(x + 1, y - 1, z, 512);
						}
						if (rotation == 3) {
							map.removeFlags(x, y, z, 32768);
							map.removeFlags(x - 1, y - 1, z, 2048);
						}
					}
					if (type == 2) {
						if (rotation == 0) {
							map.removeFlags(x, y, z, 0x10400);
							map.removeFlags(x - 1, y, z, 4096);
							map.removeFlags(x, y + 1, z, 16384);
						}
						if (rotation == 1) {
							map.removeFlags(x, y, z, 5120);
							map.removeFlags(x, y + 1, z, 16384);
							map.removeFlags(x + 1, y, z, 0x10000);
						}
						if (rotation == 2) {
							map.removeFlags(x, y, z, 20480);
							map.removeFlags(x + 1, y, z, 0x10000);
							map.removeFlags(x, y - 1, z, 1024);
						}
						if (rotation == 3) {
							map.removeFlags(x, y, z, 0x14000);
							map.removeFlags(x, y - 1, z, 1024);
							map.removeFlags(x - 1, y, z, 4096);
						}
					}
				}
				if (!data.isBlockRangeFlag()) {
					if (type == 0) {
						if (rotation == 0) {
							map.removeFlags(x, y, z, RSCollision.RANGE_WEST);
							map.removeFlags(x - 1, y, z, RSCollision.RANGE_EAST);
						}
						if (rotation == 1) {
							map.removeFlags(x, y, z, RSCollision.RANGE_NORTH);
							map.removeFlags(x, y + 1, z, RSCollision.RANGE_SOUTH);
						}
						if (rotation == 2) {
							map.removeFlags(x, y, z, RSCollision.RANGE_EAST);
							map.removeFlags(x + 1, y, z, RSCollision.RANGE_WEST);
						}
						if (rotation == 3) {
							map.removeFlags(x, y, z, RSCollision.RANGE_SOUTH);
							map.removeFlags(x, y - 1, z, RSCollision.RANGE_NORTH);
						}
					}
					if (type == 1 || type == 3) {
						if (rotation == 0) {
							map.removeFlags(x, y, z, RSCollision.RANGE_NORTH_WEST);
							map.removeFlags(x - 1, y + 1, z, RSCollision.RANGE_SOUTH_EAST);
						}
						if (rotation == 1) {
							map.removeFlags(x, y, z, RSCollision.RANGE_NORTH_EAST);
							map.removeFlags(1 + x, 1 + y, z, RSCollision.RANGE_SOUTH_WEST);
						}
						if (rotation == 2) {
							map.removeFlags(x, y, z, RSCollision.RANGE_SOUTH_EAST);
							map.removeFlags(x + 1, -1 + y, z, RSCollision.RANGE_NORTH_WEST);
						}
						if (rotation == 3) {
							map.removeFlags(x, y, z, RSCollision.RANGE_SOUTH_WEST);
							map.removeFlags(-1 + x, y - 1, z, RSCollision.RANGE_NORTH_EAST);
						}
					}
					if (type == 2) {
						if (rotation == 0) {
							map.removeFlags(x, y, z, RSCollision.RANGE_WEST | RSCollision.RANGE_NORTH);
							map.removeFlags(-1 + x, y, z, RSCollision.RANGE_EAST);
							map.removeFlags(x, 1 + y, z, RSCollision.RANGE_SOUTH);
						}
						if (rotation == 1) {
							map.removeFlags(x, y, z, RSCollision.RANGE_NORTH | RSCollision.RANGE_EAST);
							map.removeFlags(x, 1 + y, z, RSCollision.RANGE_SOUTH);
							map.removeFlags(x + 1, y, z, RSCollision.RANGE_WEST);
						}
						if (rotation == 2) {
							map.removeFlags(x, y, z, RSCollision.RANGE_EAST | RSCollision.RANGE_SOUTH);
							map.removeFlags(1 + x, y, z, RSCollision.RANGE_WEST);
							map.removeFlags(x, y - 1, z, RSCollision.RANGE_NORTH);
						}
						if (rotation == 3) {
							map.removeFlags(x, y, z, RSCollision.RANGE_SOUTH | RSCollision.RANGE_WEST);
							map.removeFlags(x, y - 1, z, RSCollision.RANGE_NORTH);
							map.removeFlags(-1 + x, y, z, RSCollision.RANGE_WEST);
						}
					}
				}
			}
		}
	}

	public int getAccessFlag() {
		int accessFlag = this.data.getAccessFlag();
		if (rotation != 0) {
			accessFlag = (accessFlag << rotation & 0xf) + (accessFlag >> 4 - rotation);
		}
		return accessFlag;
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
