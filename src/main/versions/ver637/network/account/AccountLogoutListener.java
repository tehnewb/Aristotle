package versions.ver637.network.account;

import com.framework.RSFramework;
import com.framework.network.RSConnectionListener;
import com.framework.network.RSNetworkSession;

import versions.ver637.model.player.FriendVariables;
import versions.ver637.model.player.Player;
import versions.ver637.model.player.clan.ClanVariables;

public class AccountLogoutListener implements RSConnectionListener {

	@Override
	public void onDisconnect(RSNetworkSession session) {
		Player player = session.get("Player", Player.class);
		if (player == null)
			return;

		player.getModel().setInWorld(false);
		Player.removeFromOnline(player);
		
		if (player.getPane() != null)
			player.getPane().onClose();
		
		FriendVariables.alertOffline(player);
		ClanVariables.alertClan(player);
		
		RSFramework.queueResource(new AccountSaveResource(player.getAccount()));
	}

	@Override
	public void onConnect(RSNetworkSession session) {

	}

}
