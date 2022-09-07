package versions.ver637.network.coders.frames;

import com.framework.network.RSFrame;

public class VarcFrame extends RSFrame {

	public static final int BigVarcOpcode = 5;
	public static final int SmallVarcOpcode = 51;

	public VarcFrame(int ID, int value) {
		super(value < Byte.MIN_VALUE || value > Byte.MAX_VALUE ? BigVarcOpcode : SmallVarcOpcode, StandardType);
		if (value < Byte.MIN_VALUE || value > Byte.MAX_VALUE) {
			writeLEShortA(ID);
			writeLEInt(value);
		} else {
			writeLEShort(ID);
			writeByte(value);
		}
	}
}
