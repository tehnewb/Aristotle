package versions.ver637.network.coders.handlers;

import com.framework.network.RSFrame;
import com.google.common.primitives.Ints;

import versions.ver637.model.player.Player;
import versions.ver637.network.coders.FrameHandler;
import versions.ver637.pane.ComponentClick;
import versions.ver637.pane.GamePane;
import versions.ver637.pane.Interface;

public class InterfaceHandler implements FrameHandler {

	@Override
	public void handleFrame(Player player, RSFrame frame) {
		int interfaceID = frame.readShort();
		int componentID = frame.readShort();
		int slot = frame.readLEShortA();
		int itemID = frame.readShort();
		int option = Ints.indexOf(opcodesHandled(), frame.opcode());

		GamePane pane = player.getPane();
		Interface window = pane.getChildForID(interfaceID);
		if (window == null)
			return;

		window.click(new ComponentClick(window, componentID, itemID, slot, option));
	}

	@Override
	public int[] opcodesHandled() {
		return new int[] { 6, 13, 0, 15, 46, 67, 82, 39, 73 };
	}

}
