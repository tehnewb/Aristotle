package versions.ver637.pane.primary;

import versions.ver637.model.player.clan.Clan;
import versions.ver637.model.player.clan.ClanVariables;
import versions.ver637.pane.ComponentClick;
import versions.ver637.pane.GameInterfaceAdapter;

public class ClanSetupInterface extends GameInterfaceAdapter {

	public static final int ClanSetupID = 590;
	public static final int ClanNameComponent = 22;
	public static final int EnterChatComponent = 23;
	public static final int TalkOnChatComponent = 24;
	public static final int KickOnChatComponent = 25;
	public static final int LootShareComponent = 26;

	public ClanSetupInterface() {
		super(ClanSetupID, false);
	}

	@Override
	public void onOpen() {
		Clan clan = ClanVariables.getPersonalClan(player);
		if (clan != null) {
			this.getComponent(ClanNameComponent).setText(clan.getName());
			this.getComponent(EnterChatComponent).setText(ClanVariables.RankNames[clan.getRankThatCanJoin()]);
			this.getComponent(TalkOnChatComponent).setText(ClanVariables.RankNames[clan.getRankThatCanTalk()]);
			this.getComponent(KickOnChatComponent).setText(ClanVariables.RankNames[clan.getRankThatCanKick()]);
			this.getComponent(LootShareComponent).setText(ClanVariables.RankNames[clan.getRankThatCanShareLoot()]);
		}
	}

	@Override
	public void click(ComponentClick data) {
		System.out.println(data);
		if (data.componentID() == 3) {
			player.getPane().close(this);
			return;
		}
		Clan clan = ClanVariables.getPersonalClan(player);

		if (clan == null) {
			player.sendMessage("You must set a clan prefix first");
			return;
		}

		switch (data.componentID()) {
			case EnterChatComponent -> {
				switch (data.option()) {
					case 0 -> clan.setRankThatCanJoin(ClanVariables.Anyone);
					case 1 -> clan.setRankThatCanJoin(ClanVariables.AnyFriends);
					case 2 -> clan.setRankThatCanJoin(ClanVariables.Recruit);
					case 3 -> clan.setRankThatCanJoin(ClanVariables.Corporal);
					case 4 -> clan.setRankThatCanJoin(ClanVariables.Seargeant);
					case 5 -> clan.setRankThatCanJoin(ClanVariables.Lieutenant);
					case 6 -> clan.setRankThatCanJoin(ClanVariables.Captain);
					case 7 -> clan.setRankThatCanJoin(ClanVariables.General);
					case 8 -> clan.setRankThatCanJoin(ClanVariables.OnlyMe);
				}
			}
			case TalkOnChatComponent -> {
				switch (data.option()) {
					case 0 -> clan.setRankThatCanTalk(ClanVariables.Anyone);
					case 1 -> clan.setRankThatCanTalk(ClanVariables.AnyFriends);
					case 2 -> clan.setRankThatCanTalk(ClanVariables.Recruit);
					case 3 -> clan.setRankThatCanTalk(ClanVariables.Corporal);
					case 4 -> clan.setRankThatCanTalk(ClanVariables.Seargeant);
					case 5 -> clan.setRankThatCanTalk(ClanVariables.Lieutenant);
					case 6 -> clan.setRankThatCanTalk(ClanVariables.Captain);
					case 7 -> clan.setRankThatCanTalk(ClanVariables.General);
					case 8 -> clan.setRankThatCanTalk(ClanVariables.OnlyMe);
				}
			}
			case KickOnChatComponent -> {
				switch (data.option()) {
					case 3 -> clan.setRankThatCanKick(ClanVariables.Corporal);
					case 4 -> clan.setRankThatCanKick(ClanVariables.Seargeant);
					case 5 -> clan.setRankThatCanKick(ClanVariables.Lieutenant);
					case 6 -> clan.setRankThatCanKick(ClanVariables.Captain);
					case 7 -> clan.setRankThatCanKick(ClanVariables.General);
					case 8 -> clan.setRankThatCanKick(ClanVariables.OnlyMe);
				}
			}
			case LootShareComponent -> {
				switch (data.option()) {
					case 0 -> clan.setRankThatCanShareLoot(ClanVariables.NoOne);
					case 1 -> clan.setRankThatCanShareLoot(ClanVariables.AnyFriends);
					case 2 -> clan.setRankThatCanShareLoot(ClanVariables.Recruit);
					case 3 -> clan.setRankThatCanShareLoot(ClanVariables.Corporal);
					case 4 -> clan.setRankThatCanShareLoot(ClanVariables.Seargeant);
					case 5 -> clan.setRankThatCanShareLoot(ClanVariables.Lieutenant);
					case 6 -> clan.setRankThatCanShareLoot(ClanVariables.Captain);
					case 7 -> clan.setRankThatCanShareLoot(ClanVariables.General);
				}
			}
		}
		this.getComponent(ClanNameComponent).setText(clan.getName());
		this.getComponent(EnterChatComponent).setText(ClanVariables.RankNames[clan.getRankThatCanJoin()]);
		this.getComponent(TalkOnChatComponent).setText(ClanVariables.RankNames[clan.getRankThatCanTalk()]);
		this.getComponent(KickOnChatComponent).setText(ClanVariables.RankNames[clan.getRankThatCanKick()]);
		this.getComponent(LootShareComponent).setText(ClanVariables.RankNames[clan.getRankThatCanShareLoot()]);

		clan.save();
	}

	@Override
	public void onClose() {

	}

	@Override
	public boolean clickThrough() {
		return false;
	}

	@Override
	public int position(boolean resizable) {
		return resizable ? 9 : 18;
	}

}
