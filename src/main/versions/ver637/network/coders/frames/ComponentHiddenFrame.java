package versions.ver637.network.coders.frames;

import com.framework.network.RSFrame;

public class ComponentHiddenFrame extends RSFrame {

	public static final int ComponentHiddenOpcode = 3;

	public ComponentHiddenFrame(int interfaceID, int componentID, boolean hidden) {
		super(ComponentHiddenOpcode, StandardType);
		writeInt2((interfaceID << 16) | componentID);
		writeByteC(hidden ? 0 : 1);
	}
}
