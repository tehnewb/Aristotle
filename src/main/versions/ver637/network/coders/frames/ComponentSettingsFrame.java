package versions.ver637.network.coders.frames;

import com.framework.network.RSFrame;

public class ComponentSettingsFrame extends RSFrame {

	public static final int ComponentSettingsOpcode = 119;

	public ComponentSettingsFrame(int settings, int interfaceID, int componentID, int offset, int length) {
		super(ComponentSettingsOpcode, StandardType);

		writeInt2(settings);
		writeShortA(length);
		writeShortA(offset);
		writeLEInt(interfaceID << 16 | componentID);
	}
}
