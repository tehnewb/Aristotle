package versions.ver637.network.coders.frames;

import com.framework.network.RSFrame;

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
		String previous = friend.getAppearanceVariables().previousUserName();
		String displayName = friend.getAppearanceVariables().username();

		writeByte(0);
		writeRSString(displayName == null ? friend.getUsername() : displayName);
		writeRSString(previous == null ? "" : previous);
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
