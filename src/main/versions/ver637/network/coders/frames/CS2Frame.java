package versions.ver637.network.coders.frames;

import com.framework.network.RSFrame;

public class CS2Frame extends RSFrame {

	public static final int CS2Opcode = 16;

	public CS2Frame(int scriptID, Object... params) {
		super(CS2Opcode, VarShortType);

		for (int i = 0; i < params.length; i++) // Write the types backwards
			writeByte((byte) (params[params.length - 1 - i] instanceof String ? 's' : 'i'));
		writeByte((byte) 0); // Null terminator

		for (int i = 0; i < params.length; i++) { // Write the parameters forwards.
			if (params[i] instanceof String) {
				writeRSString((String) params[i]);
			} else {
				writeInt((int) params[i]);
			}
		}
		writeInt(scriptID);
	}

}
