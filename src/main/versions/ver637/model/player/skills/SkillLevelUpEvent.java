package versions.ver637.model.player.skills;

import versions.ver637.model.player.Player;

public record SkillLevelUpEvent(Player player, SkillType type, int oldLevel, int newLevel) {}
