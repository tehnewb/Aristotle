package versions.ver637.network.coders.frames;

import com.framework.network.RSFrame;

public class VarpFrame extends RSFrame {

	public static final int SmallVarpOpcode = 21;
	public static final int BigVarpOpcode = 27;

	public VarpFrame(int ID, int value) {
		super(value < 0 || value >= 128 ? BigVarpOpcode : SmallVarpOpcode, StandardType);
		if (value < 0 || value >= 128) {
			writeInt1(value);
			writeShort(ID);
		} else {
			writeShortA(ID);
			writeByteS(value);
		}
	}
}
