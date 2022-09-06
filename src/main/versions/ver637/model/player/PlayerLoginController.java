package versions.ver637.model.player;

import com.framework.event.RSController;
import com.framework.event.RSEventMethod;
import com.framework.util.StringUtil;

import versions.ver637.model.player.clan.ClanVariables;
import versions.ver637.model.player.flags.AppearanceFlag;

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
			player.getAppearanceVariables().username(StringUtil.upperFirst(player.getAccount().getUsername()));
			player.getModel().setInWorld(true);
			player.getModel().registerFlag(new AppearanceFlag(player.getAppearanceVariables()));

			player.sendMessage("Welcome to Aristotle");
		}
	}

}
