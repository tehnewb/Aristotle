package versions.ver637.model.player.flags;

import com.framework.map.RSLocation;
import com.framework.network.RSFrame;

import lombok.RequiredArgsConstructor;
import versions.ver637.model.UpdateFlag;

@RequiredArgsConstructor
public class FaceLocationFlag implements UpdateFlag {

	private final RSLocation currentLocation;
	private final RSLocation lookingAtLocation;

	@Override
	public int mask() {
		return 0x4;
	}

	@Override
	public int ordinal() {
		return UpdateFlag.FaceLocationOrdinal;
	}

	@Override
	public void write(RSFrame block) {
		int deltaX = currentLocation.getX() - lookingAtLocation.getX();
		int deltaY = currentLocation.getY() - lookingAtLocation.getY();
		int atan = ((int) (Math.atan2(deltaX, deltaY) * 2607.5945876176133)) & 0x3fff;
		block.writeLEShort(atan);
	}

}
