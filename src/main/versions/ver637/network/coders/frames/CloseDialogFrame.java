package versions.ver637.network.coders.frames;

import com.framework.network.RSFrame;

public class CloseDialogFrame extends RSFrame {

	public static final int CloseDialogOpcode = 82;

	public CloseDialogFrame() {
		super(CloseDialogOpcode, StandardType);
	}

}
