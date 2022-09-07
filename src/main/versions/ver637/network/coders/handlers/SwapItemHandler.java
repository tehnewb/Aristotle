package versions.ver637.network.coders.handlers;

import com.framework.network.RSFrame;

import versions.ver637.model.player.Player;
import versions.ver637.network.coders.FrameHandler;
import versions.ver637.pane.ComponentSwap;
import versions.ver637.pane.GamePane;
import versions.ver637.pane.Interface;
import versions.ver637.pane.InterfaceComponent;

public class SwapItemHandler implements FrameHandler {

	public static final int SwapItemOpcode = 10;

	@Override
	public void handleFrame(Player player, RSFrame frame) {
		int fromInterfacePacked = frame.readInt();
		int fromInterfaceID = fromInterfacePacked >> 16;
		int fromComponentID = fromInterfacePacked & 0xFF;

		int toItemID = frame.readShortA();
		int fromItemID = frame.readShort();

		int toInterfacePacked = frame.readLEInt();
		int toInterfaceID = toInterfacePacked >> 16;
		int toComponentID = toInterfacePacked & 0xFF;

		int fromSlot = frame.readLEShortA();
		int toSlot = frame.readLEShort();

		GamePane pane = player.getPane();
		Interface interFrom = pane.getChildForID(fromInterfaceID);
		Interface interTo = pane.getChildForID(toInterfaceID);

		if (interTo != null) {
			InterfaceComponent fromComponent = interFrom.getComponent(fromComponentID);
			InterfaceComponent toComponent = interTo.getComponent(toComponentID);
			interTo.swap(new ComponentSwap(interFrom, fromComponent, toComponent, fromSlot, toSlot, fromItemID, toItemID));
			return;
		}

	}

	@Override
	public int[] opcodesHandled() {
		return new int[] { SwapItemOpcode };
	}

}
