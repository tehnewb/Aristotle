package versions.ver637.network.coders.frames;

import com.framework.network.RSFrame;

public class ExitToLoginFrame extends RSFrame {

	public static final int ExitToLoginOpcode = 23;

	public ExitToLoginFrame() {
		super(ExitToLoginOpcode, StandardType);
	}

}
