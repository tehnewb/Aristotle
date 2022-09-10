package versions.ver637.model.player.skills;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class Skill {

	private SkillType type;
	private byte level;
	private byte modifier;
	private double experience;
	private boolean targetingLevel;
	private double target;

	public Skill(SkillType type) {
		this.type = type;
		this.level = 1;
	}

	/**
	 * Normalizes this {@code SkillData} by moving the modifier closer to zero by 1.
	 */
	public void normalize() {
		if (modifier < 0) {
			modifier++;
		} else if (modifier > 0) {
			modifier--;
		}
	}

	public void modifyByPercentage(double percentage) {
		if (percentage > 1)
			percentage /= 100;

		modifier += (int) (level * percentage);
	}

	public int getActualLevel() {
		return SkillVariables.getLevelForExperience(experience);
	}

	public int getModifiedLevel() {
		return level + modifier;
	}

	public void setLevel(int level) {
		this.level = (byte) level;
	}

	public void setModifier(int modifier) {
		this.modifier = (byte) modifier;
	}

	/**
	 * Adds the given {@code amount} to the modifier of this {@code SkillData}.
	 * 
	 * @param amount the amount to add
	 */
	public void modifyByAmount(int amount) {
		modifier += amount;
	}
}
