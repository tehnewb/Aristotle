package versions.ver637.network.coders.handlers;

import com.framework.network.RSFrame;
import com.framework.util.StringUtil;

import versions.ver637.model.player.FriendVariables;
import versions.ver637.model.player.Player;
import versions.ver637.network.coders.FrameHandler;

public class IgnoreHandler implements FrameHandler {

	public static final int AddIgnoreOpcode = 74;
	public static final int RemoveIgnoreOpcode = 20;

	@Override
	public void handleFrame(Player player, RSFrame frame) {
		switch (frame.opcode()) {
			case AddIgnoreOpcode -> {
				String name = StringUtil.upperFirst(frame.readRSString());

				FriendVariables.addIgnore(player, name);
			}
			case RemoveIgnoreOpcode -> {
				frame.readByte();
				String name = StringUtil.upperFirst(frame.readRSString());

				FriendVariables.removeIgnore(player, name);
			}
		}
	}

	@Override
	public int[] opcodesHandled() {
		return new int[] { AddIgnoreOpcode, RemoveIgnoreOpcode };
	}

}
