package versions.ver637.network.coders.handlers;

import com.framework.network.RSFrame;

import versions.ver637.model.player.Player;
import versions.ver637.network.coders.FrameHandler;
import versions.ver637.pane.GamePane;

public class PaneSwitchHandler implements FrameHandler {

	@Override
	public void handleFrame(Player player, RSFrame frame) {
		int mode = frame.readByte();

		boolean resizable = mode >= 2;
		if (player.getPane() != null && player.getPane().isResizable() == resizable)
			return;
		player.setWindowPane(new GamePane(player, resizable));
	}

	@Override
	public int[] opcodesHandled() {
		return new int[] { 7 };
	}

}
