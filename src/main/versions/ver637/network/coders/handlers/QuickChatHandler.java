package versions.ver637.network.coders.handlers;

import com.framework.network.RSFrame;

import versions.ver637.model.player.ChatVariables.QuickChat;
import versions.ver637.model.player.Player;
import versions.ver637.network.coders.FrameHandler;

public class QuickChatHandler implements FrameHandler {

	@Override
	public void handleFrame(Player player, RSFrame frame) {
		boolean additionalScriptsRequired = frame.readByte() == 1;
		int fileID = frame.readUnsignedShort();
		player.getChatVariables().setCurrentQuickChat(new QuickChat(fileID));

		if (additionalScriptsRequired) {
			//do nothing
		}
	}

	@Override
	public int[] opcodesHandled() {
		return new int[] { 61 };
	}

}
