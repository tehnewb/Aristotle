package versions.ver637.network.coders.handlers;

import com.framework.network.RSFrame;

import versions.ver637.model.player.Player;
import versions.ver637.model.player.WalkScript;
import versions.ver637.network.coders.FrameHandler;

public class WalkRequestHandler implements FrameHandler {

	@Override
	public void handleFrame(Player player, RSFrame frame) {
		int x = frame.readShort();
		int y = frame.readShort();

		player.getScripts().queue(new WalkScript(x, y));
	}

	@Override
	public int[] opcodesHandled() {
		return new int[] { 35, 50 };
	}

}
