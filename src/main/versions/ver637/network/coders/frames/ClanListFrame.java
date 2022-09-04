package versions.ver637.network.coders.frames;

import com.framework.network.RSFrame;
import com.framework.util.StringUtil;

import versions.ver637.model.player.Player;
import versions.ver637.model.player.clan.Clan;
import versions.ver637.model.player.clan.ClanMember;

public class ClanListFrame extends RSFrame {

	public static final int ClanListOpcode = 28;

	public ClanListFrame(Clan clan) {
		super(ClanListOpcode, VarShortType);

		writeRSString(clan.getOwner());
		writeByte(0);
		writeLong(StringUtil.toLong(clan.getName()));
		writeByte(clan.getRankThatCanKick()); // kick requirement
		writeByte(clan.getMembers().size());
		for (ClanMember member : clan.getMembers()) {
			writeRSString(StringUtil.upperFirst(member.username()));
			writeByte(1); // display name
			writeRSString(StringUtil.upperFirst(member.username()));
			writeShort(1);
			writeByte(member.rank()); // clan rank

			Player other = Player.get(member.username());
			if (other == null) {
				writeRSString("<col=FF0000>Offline");
			} else {
				writeRSString(other.getModel().isInWorld() ? "Aristotle" : "<col=FFFF00>Lobby");
			}
		}
	}

	public ClanListFrame() {
		super(ClanListOpcode, VarShortType);
	}
}
