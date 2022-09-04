package versions.ver637.network.coders.frames;

import com.framework.network.RSFrame;

public class ExitToLobbyFrame extends RSFrame {

	public static final int ExitToLobbyOpcode = 45;

	public ExitToLobbyFrame() {
		super(ExitToLobbyOpcode, StandardType);
	}

}
