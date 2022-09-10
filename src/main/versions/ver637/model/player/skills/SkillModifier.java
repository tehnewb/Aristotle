package versions.ver637.model.player.skills;

public record SkillModifier(SkillType type, boolean byPercentage, double modifier) {

	public static SkillModifier percentage(SkillType type, double percentage) {
		return new SkillModifier(type, true, percentage);
	}

	public static SkillModifier value(SkillType type, int amount) {
		return new SkillModifier(type, false, amount);
	}

}
