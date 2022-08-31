package versions.ver637.network.coders.frames;

import com.framework.network.RSFrame;

public class VarcFrame extends RSFrame {

	public static final int SmallVarcOpcode = 5;
	public static final int BigVarcOpcode = 51;

	public VarcFrame(int ID, int value) {
		super(value < 0 || value >= 128 ? BigVarcOpcode : SmallVarcOpcode, StandardType);
		if (value < 0 || value >= 128) {
			writeLEShort(ID);
			writeByte(value);
		} else {
			writeLEShortA(ID);
			writeLEInt(value);
		}
	}
}
