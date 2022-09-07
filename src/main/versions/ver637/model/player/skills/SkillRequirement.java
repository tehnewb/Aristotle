package versions.ver637.model.player.skills;

import versions.ver637.model.player.Player;

public record SkillRequirement(SkillType type, int level) {

	public boolean has(Player player) {
		return player.getSkillVariables().getLevel(type) >= level;
	}

	public boolean hasWithModifier(Player player) {
		SkillVariables variables = player.getSkillVariables();
		return variables.getLevelWithModifier(type) >= level;
	}
}
