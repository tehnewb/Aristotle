package versions.ver637.network.coders.frames;

import com.framework.network.RSFrame;
import com.framework.util.StringUtil;

import versions.ver637.model.player.Player;
import versions.ver637.model.player.clan.Clan;
import versions.ver637.model.player.clan.ClanMember;
import versions.ver637.model.player.clan.ClanVariables;
import versions.ver637.network.account.Account;

public class UpdateFriendFrame extends RSFrame {

	public enum FriendState {
		Offline,
		World,
		Lobby
	}

	public static final int UpdateFriendOpcode = 10;

	public UpdateFriendFrame(Player player, Account friend, FriendState state) {
		super(UpdateFriendOpcode, VarShortType);
		String previousName = friend.getAppearanceVariables().previousUserName();
		String displayName = friend.getAppearanceVariables().username();

		if (displayName == null)
			displayName = friend.getUsername();

		displayName = StringUtil.upperFirst(displayName);

		Clan personalClan = ClanVariables.getPersonalClan(player);
		int friendRank = 0;
		if (personalClan != null) {
			ClanMember member = personalClan.getMember(friend.getAppearanceVariables().username());
			if (member != null)
				friendRank = member.rank();
		}

		writeByte(0);
		writeRSString(displayName);
		writeRSString(previousName == null ? "" : StringUtil.upperFirst(previousName));
		writeShort(state.ordinal());
		writeByte(friendRank); // clan rank
		if (state.equals(FriendState.Lobby) || state.equals(FriendState.World)) {
			writeRSString(state.equals(FriendState.World) ? "Aristotle" : "Lobby");
			writeByte(0);
		}
	}

	public UpdateFriendFrame() {
		super(UpdateFriendOpcode, VarShortType);
	}
}
