package versions.ver637.network.coders.handlers;

import com.framework.network.RSFrame;

import versions.ver637.model.player.Player;
import versions.ver637.network.coders.FrameHandler;

public class KeyHandler implements FrameHandler {

	public static final int KeyboardOpcode = 26;

	@Override
	public void handleFrame(Player player, RSFrame frame) {

	}

	@Override
	public int[] opcodesHandled() {
		return new int[] { KeyboardOpcode };
	}

}
