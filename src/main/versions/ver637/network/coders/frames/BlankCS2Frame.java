package versions.ver637.network.coders.frames;

import com.framework.network.RSFrame;

public class BlankCS2Frame extends RSFrame {

	public static final int BlankCS2Opcode = 98;

	public BlankCS2Frame(int scriptID) {
		super(BlankCS2Opcode, VarShortType);
		writeShort(0);
		writeRSString("");
		writeInt(scriptID);
	}

}
