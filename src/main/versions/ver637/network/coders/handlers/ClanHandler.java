package versions.ver637.network.coders.handlers;

import com.framework.network.RSFrame;
import com.framework.util.StringUtil;

import versions.ver637.model.player.FriendVariables;
import versions.ver637.model.player.Player;
import versions.ver637.model.player.clan.Clan;
import versions.ver637.model.player.clan.ClanMember;
import versions.ver637.model.player.clan.ClanVariables;
import versions.ver637.network.coders.FrameHandler;

public class ClanHandler implements FrameHandler {

	public static final int JoinClanOpcode = 1;
	public static final int ChangeRankOpcode = 51;
	public static final int ClanMessageOpcode = 52;
	public static final int ClanPunishmentOpcode = 36;

	@Override
	public void handleFrame(Player player, RSFrame frame) {
		switch (frame.opcode()) {
			case JoinClanOpcode -> {
				if (frame.readableBytes() > 0) {
					String name = StringUtil.upperFirst(frame.readRSString());

					ClanVariables.joinClan(player, name);
				} else {
					ClanVariables.leaveClan(player);
				}
			}
			case ClanMessageOpcode -> {
				player.getChatVariables().setMessagingClan(frame.readByte() == 1);
			}
			case ClanPunishmentOpcode -> {
				String name = StringUtil.upperFirst(frame.readRSString());
				ClanVariables.punishPlayer(name);
			}
			case ChangeRankOpcode -> {
				String name = StringUtil.upperFirst(frame.readRSString());
				int rank = frame.readByteA();

				Clan clan = ClanVariables.getPersonalClan(player);
				if (clan == null) {
					player.sendMessage("You must set a clan prefix first");
				} else {
					ClanMember member = clan.getMember(name);
					if (member == null) {
						clan.addMember(new ClanMember(name, rank, 0));
					} else {
						member.rank(rank);
					}
					if (member != null) {
						clan.refresh();
					}
					FriendVariables.initializeFriendsList(player);
				}
			}
		}
	}

	@Override
	public int[] opcodesHandled() {
		return new int[] { JoinClanOpcode, ChangeRankOpcode, ClanMessageOpcode, ClanPunishmentOpcode };
	}

}
