package versions.ver637.map;

import versions.ver637.model.player.Player;

public record PlayerUseGameObjectEvent(Player player, GameObject locale, String option) {}
