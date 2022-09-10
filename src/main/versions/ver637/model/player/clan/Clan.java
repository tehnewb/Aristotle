package versions.ver637.model.player.clan;

import java.util.ArrayList;
import java.util.HashMap;

import com.framework.RSFramework;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import versions.ver637.model.player.Player;
import versions.ver637.network.coders.frames.ClanListFrame;

@Getter
@Setter
public class Clan {
	private static HashMap<String, Clan> clans = new HashMap<>();

	private ArrayList<ClanMember> members = new ArrayList<>();

	private final String owner;
	private String name;
	private int rankThatCanKick = ClanVariables.OnlyMe;
	private int rankThatCanJoin = ClanVariables.AnyFriends;
	private int rankThatCanTalk = ClanVariables.Anyone;
	private int rankThatCanShareLoot = ClanVariables.Anyone;

	public Clan(@NonNull String owner, @NonNull String name) {
		this.owner = owner;
		this.name = name;
	}

	public void refresh() {
		for (ClanMember m : members) {
			Player other = Player.get(m.username());
			if (other == null)
				continue;
			if (other.getClanVariables().getClanName() == null || !other.getClanVariables().getClanName().equalsIgnoreCase(this.name))
				continue;
			other.getSession().write(new ClanListFrame(this));
		}
	}

	public void addMember(@NonNull ClanMember member) {
		if (!members.stream().anyMatch(m -> m.username().equalsIgnoreCase(member.username()))) {
			members.add(member);

			save();
			refresh();
		}
	}

	public void removeMember(@NonNull String username) {
		if (members.removeIf(m -> m.username().equalsIgnoreCase(username))) {
			save();
			refresh();
		}
	}

	public ClanMember getMember(String username) {
		return members.stream().filter(m -> m.username().equalsIgnoreCase(username)).findFirst().orElse(null);
	}

	public void setName(@NonNull String newName) {
		if (!newName.equals(name)) {
			removeClan(name);

			this.name = newName;
			addClan(this);
		}

		this.name = newName;
		refresh();
	}

	public void save() {
		RSFramework.queueResource(new ClanSaveResource(this));
	}

	public void delete() {

		RSFramework.queueResource(new ClanDeleteResource(this.name));
	}

	public static Clan getClan(@NonNull String name) {
		return clans.get(name);
	}

	public static Clan getClanByOwner(String owner) {
		for (Clan clan : clans.values()) {
			if (clan.getOwner().equalsIgnoreCase(owner))
				return clan;
		}
		return null;
	}

	public static void createClan(Clan clan) {
		if (clans.containsKey(clan.name))
			return;
		addClan(clan);
	}

	public static void removeClan(@NonNull String name) {
		Clan clan = clans.remove(name);

		if (clan == null)
			return;

		clan.delete();
	}

	public static void addClan(@NonNull Clan clan) {
		if (clans.containsKey(clan.name))
			return;
		clans.put(clan.getName(), clan);

		clan.save();
	}

}
