package versions.ver637.model.player.flags;

import com.framework.network.RSFrame;

import versions.ver637.model.UpdateFlag;

public class TeleportFlag implements UpdateFlag {

	@Override
	public int mask() {
		return 0x2000;
	}

	@Override
	public int ordinal() {
		return UpdateFlag.TeleportOrdinal;
	}

	@Override
	public void write(RSFrame frame) {
		frame.writeByteA(127);
	}

}
