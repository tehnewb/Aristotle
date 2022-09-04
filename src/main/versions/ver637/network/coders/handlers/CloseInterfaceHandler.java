package versions.ver637.network.coders.handlers;

import com.framework.network.RSFrame;

import versions.ver637.model.player.Player;
import versions.ver637.network.coders.FrameHandler;
import versions.ver637.pane.GamePane;
import versions.ver637.pane.Interface;

public class CloseInterfaceHandler implements FrameHandler {

	@Override
	public void handleFrame(Player player, RSFrame frame) {
		GamePane pane = player.getPane();
		Interface window = pane.getChildForPosition(pane.isResizable() ? 9 : 18);
		if (window == null)
			return;

		pane.removeChild(pane.isResizable() ? 9 : 18);
	}

	@Override
	public int[] opcodesHandled() {
		return new int[] { 32 };
	}

}
