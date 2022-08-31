package versions.ver637.network.account;

import com.framework.RSFramework;
import com.framework.network.RSConnectionListener;
import com.framework.network.RSNetworkSession;

import versions.ver637.model.player.Player;

public class AccountLogoutListener implements RSConnectionListener {

	@Override
	public void onDisconnect(RSNetworkSession session) {
		Player player = session.get("Player", Player.class);
		if (player == null)
			return;

		RSFramework.queueResource(new AccountSaveResource(player.getAccount()));

		Player.removeFromOnline(player);
	}

	@Override
	public void onConnect(RSNetworkSession session) {

	}

}
