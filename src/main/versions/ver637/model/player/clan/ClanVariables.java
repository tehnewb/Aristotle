package versions.ver637.model.player.clan;

import lombok.Getter;
import lombok.Setter;
import versions.ver637.model.player.Player;
import versions.ver637.network.coders.frames.ClanListFrame;

@Getter
@Setter
public class ClanVariables {
	public static final String[] RankNames = { "Anyone", "Recruit+", "Corporal+", "Seargeant+", "Lieutenant+", "Captain+", "General+", "Only Me", "Any Friends", "No-one" };
	public static final int Anyone = 0;
	public static final int Recruit = 1;
	public static final int Corporal = 2;
	public static final int Seargeant = 3;
	public static final int Lieutenant = 4;
	public static final int Captain = 5;
	public static final int General = 6;
	public static final int OnlyMe = 7;
	public static final int AnyFriends = 8;
	public static final int NoOne = 9;

	private String clanName = "Aristotle";

	public static void alertClan(Player player) {
		Clan clan = getClan(player);
		if (clan == null) {
			player.getSession().write(new ClanListFrame());
			player.getClanVariables().setClanName(null);
		} else {
			clan.refresh();
		}
	}

	public static void joinClan(Player player, String name) {
		Clan clan = Clan.getClan(name);
		if (clan == null && name.equalsIgnoreCase(player.getAccount().getUsername())) {
			player.sendMessage("You must set a prefix for your clan.");
		} else if (clan != null) {
			player.getClanVariables().setClanName(name);
			if (clan.getOwner().equalsIgnoreCase(player.getAccount().getUsername())) {
				clan.addMember(new ClanMember(player.getAccount().getUsername(), 7, 0));
			} else {
				clan.addMember(new ClanMember(player.getAccount().getUsername(), 0, 0));
			}
			ClanVariables.alertClan(player);
		} else {
			player.sendMessage("That clan name does not exist.");
		}
	}

	public static void punishPlayer(String name) {

	}

	public static void leaveClan(Player player) {
		Clan clan = getClan(player);
		if (clan == null)
			return;

		player.getClanVariables().setClanName(null);
		clan.removeMember(player.getAccount().getUsername());
		alertClan(player);
	}

	public static Clan getPersonalClan(Player player) {
		return Clan.getClanByOwner(player.getAppearanceVariables().username());
	}

	public static Clan getClan(Player player) {
		ClanVariables variables = player.getClanVariables();

		if (variables.getClanName() == null)
			return null;

		Clan clan = Clan.getClan(variables.getClanName());
		return clan;
	}

}
