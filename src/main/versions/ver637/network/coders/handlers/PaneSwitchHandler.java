package versions.ver637.network.coders.handlers;

import com.framework.network.RSFrame;
import com.framework.network.RSNetworkSession;

import versions.ver637.model.player.Player;
import versions.ver637.network.coders.RSFrameHandler;
import versions.ver637.window.pane.FixedPane;
import versions.ver637.window.pane.ResizablePane;

public class PaneSwitchHandler implements RSFrameHandler {

	@Override
	public void handleFrame(RSNetworkSession session, RSFrame frame) {
		int mode = frame.readByte();

		Player player = session.get("Player", Player.class);
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
