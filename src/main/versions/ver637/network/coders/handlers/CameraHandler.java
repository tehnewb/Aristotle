package versions.ver637.network.coders.handlers;

import com.framework.network.RSFrame;

import versions.ver637.model.player.Player;
import versions.ver637.network.coders.FrameHandler;

public class CameraHandler implements FrameHandler {

	public static final int CameraOpcode = 30;

	@Override
	public void handleFrame(Player player, RSFrame frame) {

	}

	@Override
	public int[] opcodesHandled() {
		return new int[] { CameraOpcode };
	}

}
