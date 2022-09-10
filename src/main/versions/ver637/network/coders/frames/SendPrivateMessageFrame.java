package versions.ver637.network.coders.frames;

import com.framework.io.Huffman;
import com.framework.network.RSFrame;

import versions.ver637.model.player.Player;

public class SendPrivateMessageFrame extends RSFrame {

	public static final int SendPrivateMessageOpcode = 76;

	public SendPrivateMessageFrame(Player receiver, String text) {
		super(SendPrivateMessageOpcode, VarByteType);

		byte[] bytes = new byte[256];
		int length = Huffman.compress(text, bytes, 0);
		writeRSString(receiver.getAppearanceVariables().getUsername());
		writeByte(text.length());
		writeBytes(bytes, 0, length);
	}

}
