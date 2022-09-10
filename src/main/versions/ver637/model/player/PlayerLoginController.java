package versions.ver637.model.player;

import com.framework.event.RSController;
import com.framework.event.RSEventMethod;
import com.framework.util.StringUtil;

import versions.ver637.model.player.clan.ClanVariables;

@RSController
public class PlayerLoginController {

	@RSEventMethod
	public void onLogin(LoginEvent event) {
		Player player = event.player();
		if (event.lobby()) {
			FriendVariables.alertOnline(player);
			ClanVariables.alertClan(player);
		} else {
			player.getLocationVariables().setRegionLocation(player.getLocation());
			player.getAppearanceVariables().setUsername(StringUtil.upperFirst(player.getAccount().getUsername()));
			player.getModel().setInWorld(true);
			AppearanceVariables.updateAppearance(player);

			player.sendMessage("Welcome to Aristotle");
		}
	}

}
