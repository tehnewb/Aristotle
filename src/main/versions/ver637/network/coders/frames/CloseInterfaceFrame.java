package versions.ver637.network.coders.frames;

import com.framework.network.RSFrame;

public class CloseInterfaceFrame extends RSFrame {

	public static final int CloseFrameOpcode = 61;

	public CloseInterfaceFrame(int parentID, int position) {
		super(CloseFrameOpcode, StandardType);
		writeLEInt(parentID << 16 | position);
	}

}
