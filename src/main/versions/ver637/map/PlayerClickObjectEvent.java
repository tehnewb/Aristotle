package versions.ver637.map;

import versions.ver637.model.player.Player;

public record PlayerClickObjectEvent(Player player, GameObject object, String option) {

}
