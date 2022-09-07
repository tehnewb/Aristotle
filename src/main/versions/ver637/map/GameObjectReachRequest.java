package versions.ver637.map;

import java.util.function.Consumer;

import com.framework.RSFramework;
import com.framework.map.RSLocation;
import com.framework.map.path.RSRoute;

import versions.ver637.model.player.Player;
import versions.ver637.model.player.flags.FaceLocationFlag;

public record GameObjectReachRequest(Player player, String option) implements Consumer<RSRoute> {

	@Override
	public void accept(RSRoute route) {
		GameObject target = GameObject.class.cast(route.target());
		RSLocation center = target.getLocation().translate(target.getSizeX() / 2, target.getSizeY() / 2, 0);
		player.getModel().registerFlag(new FaceLocationFlag(player.getLocation(), center));
		RSFramework.post(new PlayerUseGameObjectEvent(player, target, option));
	}

}
