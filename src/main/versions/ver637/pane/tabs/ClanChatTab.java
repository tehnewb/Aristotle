package versions.ver637.pane.tabs;

import versions.ver637.model.player.clan.Clan;
import versions.ver637.model.player.clan.ClanVariables;
import versions.ver637.pane.ComponentClick;
import versions.ver637.pane.GameInterfaceAdapter;
import versions.ver637.pane.primary.ClanSetupInterface;

public class ClanChatTab extends GameInterfaceAdapter {

	public static final int ClanChatID = 589;

	public ClanChatTab() {
		super(ClanChatID, true);
	}

	@Override
	public void onOpen() {
		ClanVariables.alertClan(player);
	}

	@Override
	public void click(ComponentClick data) {
		if (data.componentID() == 16) {
			player.getPane().open(new ClanSetupInterface());
		}
	}

	@Override
	public void onClose() {
		Clan clan = ClanVariables.getClan(player);
		if (clan != null)
			clan.refresh();
	}

	@Override
	public boolean clickThrough() {
		return true;
	}

	@Override
	public int position(boolean resizable) {
		return resizable ? 98 : 213;
	}

}
