package versions.ver637.map;

import versions.ver637.model.player.Player;

public record PlayerUseLocaleEvent(Player player, Locale locale, String option) {}
