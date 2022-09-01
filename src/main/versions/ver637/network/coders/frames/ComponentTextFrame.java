package versions.ver637.network.coders.frames;

import com.framework.network.RSFrame;

public class ComponentTextFrame extends RSFrame {

	public static final int ComponentTextOpcode = 33;

	public ComponentTextFrame(int interfaceID, int componentID, String text) {
		super(ComponentTextOpcode, VarShortType);

		writeRSString(text);
		writeLEInt(interfaceID << 16 | componentID);
	}

}
