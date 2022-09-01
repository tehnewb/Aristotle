package versions.ver637.network.coders.frames;

import com.framework.network.RSFrame;
import com.framework.util.StringUtil;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class ChatMessageFrame extends RSFrame {

	@RequiredArgsConstructor
	public enum ChatType {
		Normal(0),
		Clan(11),
		Console(99),
		Trade(100),
		Duel(101),
		Assistance(102);

		@Getter
		private final int chatValue;
	}

	public static final int ChatMessageOpcode = 53;

	public ChatMessageFrame(String username, String message, ChatType type) {
		super(ChatMessageOpcode, VarByteType);

		writeByte(type.chatValue);
		writeInt(0);
		writeByte(type.equals(ChatType.Normal) ? 0 : 1);
		if (!type.equals(ChatType.Normal))
			writeRSString(StringUtil.upperFirst(username));
		writeRSString(message);
	}
}
