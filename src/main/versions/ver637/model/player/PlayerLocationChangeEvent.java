package versions.ver637.model.player;

import com.framework.map.RSLocation;

public record PlayerLocationChangeEvent(Player player, RSLocation from, RSLocation to) {

}
