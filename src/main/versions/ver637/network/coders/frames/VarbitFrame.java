package versions.ver637.network.coders.frames;

import com.framework.network.RSFrame;

public class VarbitFrame extends RSFrame {

	public static final int VarbitOpcode = 27;

	public VarbitFrame(int ID, int value) {
		super(VarbitOpcode, StandardType);
		writeInt1(ID);
		writeShort(value);
	}
}
