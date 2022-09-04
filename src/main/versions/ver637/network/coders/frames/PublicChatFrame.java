package versions.ver637.network.coders.frames;

import com.framework.io.Huffman;
import com.framework.network.RSFrame;

import versions.ver637.model.player.Player;

public class PublicChatFrame extends RSFrame {

	public static final int PublicChatOpcode = 62;

	public PublicChatFrame(Player player, String text, int chatEffects) {
		super(PublicChatOpcode, VarByteType);

		writeShort(player.getIndex());
		writeShort(chatEffects);
		writeByte(player.getAccount().getRank());
		byte[] buffer = new byte[256];
		buffer[0] = (byte) text.length();
		int offset = 1 + Huffman.compress(text, buffer, 1);
		writeBytes(buffer, 0, offset);
	}

	public PublicChatFrame(Player player, int quickChatFileID, String extra) {
		super(PublicChatOpcode, VarByteType);

		writeShort(player.getIndex());
		writeShort(0x8000);
		writeByte(player.getAccount().getRank());
		writeShort(quickChatFileID);
	}

}
