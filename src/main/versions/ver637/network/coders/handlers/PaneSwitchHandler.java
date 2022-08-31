package versions.ver637.network.coders.handlers;

import com.framework.network.RSFrame;

import versions.ver637.model.player.Player;
import versions.ver637.network.coders.FrameHandler;
import versions.ver637.pane.FixedPane;
import versions.ver637.pane.ResizablePane;

public class PaneSwitchHandler implements FrameHandler {

	@Override
	public void handleFrame(Player player, RSFrame frame) {
		int mode = frame.readByte();

		if (mode == 1) {
			player.setWindowPane(new FixedPane());
		} else {
			player.setWindowPane(new ResizablePane());
		}
	}

	@Override
	public int[] opcodesHandled() {
		return new int[] { 7 };
	}

}
