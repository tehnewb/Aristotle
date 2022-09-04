package versions.ver637.network.coders.handlers;

import com.framework.io.Huffman;
import com.framework.network.RSFrame;
import com.framework.util.StringUtil;

import versions.ver637.model.player.ChatVariables.Chat;
import versions.ver637.model.player.FriendVariables;
import versions.ver637.model.player.Player;
import versions.ver637.network.coders.FrameHandler;

public class FriendHandler implements FrameHandler {

	public static final int AddFriendOpcode = 2;
	public static final int RemoveFriendOpcode = 77;
	public static final int SendPrivateMessageOpcode = 41;

	@Override
	public void handleFrame(Player player, RSFrame frame) {
		switch (frame.opcode()) {
			case AddFriendOpcode -> {
				String name = StringUtil.upperFirst(frame.readRSString());

				FriendVariables.addFriend(player, name);
			}
			case RemoveFriendOpcode -> {
				String name = StringUtil.upperFirst(frame.readRSString());

				FriendVariables.removeFriend(player, name);
			}
			case SendPrivateMessageOpcode -> {
				String name = StringUtil.upperFirst(frame.readRSString());
				int characterCount = frame.readUnsignedByte();
				byte[] buffer = frame.readBytes(frame.readableBytes());
				String message = Huffman.decompress(buffer, characterCount);
				String formatted = StringUtil.formatForRuneScape(message);
				FriendVariables.sendPrivateMessage(player, name, new Chat(formatted, 0));
			}
		}
	}

	@Override
	public int[] opcodesHandled() {
		return new int[] { AddFriendOpcode, RemoveFriendOpcode, SendPrivateMessageOpcode };
	}

}
