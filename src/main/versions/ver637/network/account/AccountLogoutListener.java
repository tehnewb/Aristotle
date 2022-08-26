package versions.ver637.network.account;

import com.framework.event.RSController;
import com.framework.event.RSEventMethod;
import com.framework.network.RSNetworkSession;
import com.framework.network.RSSessionDisconnectEvent;

import versions.ver637.model.player.Player;

@RSController
public class AccountLogoutListener {

	@RSEventMethod
	public void onDisconnect(RSSessionDisconnectEvent event) {
		RSNetworkSession session = event.getSession();
		Player player = session.get("Player", Player.class);
		if (player == null)
			return;

		Player.remove(player.getIndex());
	}

}
