package versions.ver637.network.coders.frames;

import com.framework.io.Huffman;
import com.framework.network.RSFrame;
import com.framework.util.StringUtil;

import versions.ver637.model.player.Player;
import versions.ver637.model.player.clan.Clan;

public class ClanMessageFrame extends RSFrame {

	private static long MessageCount = 0;

	public static final int ClanMessageOpcode = 35;

	public ClanMessageFrame(Player from, Clan clan, String message) {
		super(ClanMessageOpcode, VarByteType);

		long messageID = (long) (++MessageCount + ((Math.random() * Long.MAX_VALUE) + (Math.random() * Long.MIN_VALUE)));

		byte[] bytes = new byte[256];
		bytes[0] = (byte) message.length();
		int length = 1 + Huffman.compress(message, bytes, 1);
		writeByte(0);
		writeRSString(from.getAppearanceVariables().username());
		writeLong(StringUtil.toLong(clan.getName()));
		writeShort((int) (messageID >> 32));
		writeMedium((int) (messageID - ((messageID >> 32) << 32)));
		writeByte(from.getAccount().getRank());
		writeBytes(bytes, 0, length);
	}

}
