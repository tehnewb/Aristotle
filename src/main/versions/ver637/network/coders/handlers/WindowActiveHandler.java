package versions.ver637.network.coders.handlers;

import com.framework.network.RSFrame;

import versions.ver637.model.player.Player;
import versions.ver637.network.coders.FrameHandler;

public class WindowActiveHandler implements FrameHandler {

	public static final int WindowActiveOpcode = 62;

	@Override
	public void handleFrame(Player player, RSFrame frame) {
		boolean active = frame.readByte() == 1;

		if (active) {
			//do nothing
		} else {
			//do nothing again
		}
	}

	@Override
	public int[] opcodesHandled() {
		return new int[] { WindowActiveOpcode };
	}

}
