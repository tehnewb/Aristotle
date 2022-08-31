package versions.ver637.network.coders.frames;

import com.framework.network.RSFrame;

public class CS2StringFrame extends RSFrame {

	public static final int CS2StringOpcode = 88;

	public CS2StringFrame(int ID, String text) {
		super(CS2StringOpcode, VarShortType);
		writeRSString(text);
		writeLEShortA(ID);
	}

}
