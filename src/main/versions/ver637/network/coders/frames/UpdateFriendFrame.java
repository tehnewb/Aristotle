package versions.ver637.network.coders.frames;

import com.framework.network.RSFrame;
import com.framework.util.StringUtil;

import versions.ver637.network.account.Account;

public class UpdateFriendFrame extends RSFrame {

	public enum FriendState {
		Offline,
		World,
		Lobby
	}

	public static final int UpdateFriendOpcode = 10;

	public UpdateFriendFrame(Account friend, FriendState state) {
		super(UpdateFriendOpcode, VarShortType);
		String previousName = friend.getAppearanceVariables().previousUserName();
		String displayName = friend.getAppearanceVariables().username();

		if (displayName == null)
			displayName = friend.getUsername();

		displayName = StringUtil.upperFirst(displayName);

		writeByte(0);
		writeRSString(displayName);
		writeRSString(previousName == null ? "" : StringUtil.upperFirst(previousName));
		writeShort(state.ordinal());
		writeByte(0); // clan rank
		if (state.equals(FriendState.Lobby) || state.equals(FriendState.World)) {
			writeRSString(state.equals(FriendState.World) ? "Aristotle" : "Lobby");
			writeByte(0);
		}
	}

	public UpdateFriendFrame() {
		super(UpdateFriendOpcode, VarShortType);
	}
}
