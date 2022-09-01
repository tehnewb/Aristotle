package versions.ver637.network.coders.handlers;

import com.framework.io.Huffman;
import com.framework.network.RSFrame;
import com.framework.util.StringUtil;

import versions.ver637.model.player.ChatVariables.Chat;
import versions.ver637.model.player.Player;
import versions.ver637.network.coders.FrameHandler;

public class ChatHandler implements FrameHandler {

	@Override
	public void handleFrame(Player player, RSFrame frame) {
		int chatEffects = frame.readUnsignedShort();
		int characters = frame.readUnsignedByte();

		byte[] buffer = frame.readBytes(frame.readableBytes());
		String decompressed = Huffman.decompress(buffer, characters);
		String text = StringUtil.formatForRuneScape(decompressed);

		player.getChatVariables().setCurrentChat(new Chat(text, chatEffects));
	}

	@Override
	public int[] opcodesHandled() {
		return new int[] { 16 };
	}

}
