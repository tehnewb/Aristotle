package versions.ver637.model;

import com.framework.entity.RSEntityList;
import com.framework.tick.RSGameTick;

import versions.ver637.model.player.Player;

public class GameProcessTick extends RSGameTick {

	public GameProcessTick() {
		super("Game Process Tick");
	}

	@Override
	protected void tick() {
		RSEntityList<Player> players = Player.getOnlinePlayers();
		for (int i = players.head(); i <= players.tail(); i++) {
			Player player = players.get(i);
			if (player == null)
				continue;

			if (player.getModel().isInWorld()) {
				player.getModel().prepare();
			}
		}
		for (int i = players.head(); i <= players.tail(); i++) {
			Player player = players.get(i);
			if (player == null)
				continue;

			if (player.getModel().isInWorld()) {
				player.getModel().update();
			}
		}

		for (int i = players.head(); i <= players.tail(); i++) {
			Player player = players.get(i);
			if (player == null)
				continue;

			if (player.getModel().isInWorld())
				player.getModel().finish();

			player.getSession().flushMessages();
		}
	}

}
