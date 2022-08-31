package versions.ver637.network.coders.frames;

import com.framework.network.RSFrame;

public class InterfaceFrame extends RSFrame {

	private static final int InterfaceOpcode = 50;

	public InterfaceFrame(int parentID, int interfaceID, int position, boolean clickThrough) {
		super(InterfaceOpcode, StandardType);
		
		writeInt2(parentID * 65536 + position);
		writeLEShortA(position >> 16 | interfaceID);
		writeByteC(clickThrough ? 1 : 0);
	}
}
