package versions.ver637.network.coders.frames;

import com.framework.io.Huffman;
import com.framework.network.RSFrame;

import versions.ver637.model.player.Player;

public class ReceivePrivateMessageFrame extends RSFrame {

	private static long MessageCount = 0;

	public static final int ReceivePrivateMessageOpcode = 30;

	public ReceivePrivateMessageFrame(Player sender, String text) {
		super(ReceivePrivateMessageOpcode, VarByteType);

		long messageID = (long) (++MessageCount + ((Math.random() * Long.MAX_VALUE) + (Math.random() * Long.MIN_VALUE)));

		byte[] bytes = new byte[256];
		bytes[0] = (byte) text.length();
		int length = 1 + Huffman.compress(text, bytes, 1);
		writeByte(0); // has a previous name?
		writeRSString(sender.getAppearanceVariables().getUsername());
		writeShort((int) (messageID >> 32));
		writeMedium((int) (messageID - ((messageID >> 32) << 32)));
		writeByte(sender.getAccount().getRank());
		writeBytes(bytes, 0, length);
	}
}
