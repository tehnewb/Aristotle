package versions.ver637.model.player.skills;

import java.util.Arrays;

import com.framework.RSFramework;

import lombok.Getter;
import lombok.Setter;
import versions.ver637.model.player.Player;
import versions.ver637.network.coders.frames.SkillFrame;
import versions.ver637.pane.tabs.SkillTab;

public class SkillVariables {

	public static final double MaximumExperience = 200000000;

	@Getter
	@Setter
	private double experienceCounter;

	@Getter
	private Skill[] skills = new Skill[SkillType.values().length];

	public SkillVariables() {
		Arrays.setAll(skills, i -> new Skill(SkillType.forID(i)));

		setLevel(SkillType.Hitpoints, 10);
	}

	/**
	 * Returns the combat level with the current skill levels of this
	 * {@code Skills}.
	 * 
	 * @return the combat level
	 */
	public int getCombatLevel() {
		int attack = getLevel(SkillType.Attack);
		int defence = getLevel(SkillType.Defence);
		int strength = getLevel(SkillType.Strength);
		int hp = getLevel(SkillType.Hitpoints);
		int prayer = getLevel(SkillType.Prayer);
		int ranged = getLevel(SkillType.Range);
		int magic = getLevel(SkillType.Magic);

		int combatLevel = (int) ((defence + hp + Math.floor(prayer / 2)) * 0.25) + 1;
		double melee = (attack + strength) * 0.325;
		double ranger = Math.floor(ranged * 1.5) * 0.325;
		double mage = Math.floor(magic * 1.5) * 0.325;
		if (melee >= ranger && melee >= mage) {
			combatLevel += melee;
		} else if (ranger >= melee && ranger >= mage) {
			combatLevel += ranger;
		} else if (mage >= melee && mage >= ranger) {
			combatLevel += mage;
		}
		return Math.max(3, combatLevel + (getLevel(SkillType.Summoning) / 8));
	}

	public static void addExperience(Player player, SkillType type, double experience) {
		SkillVariables variables = player.getSkillVariables();
		Skill skill = variables.getSkill(type);

		double newExperience = skill.getExperience() + experience;
		int oldLevel = skill.getLevel();
		int newLevel = getLevelForExperience(newExperience);

		skill.setLevel(newLevel);
		skill.setExperience(newExperience);

		if (newLevel > oldLevel) {
			RSFramework.post(new SkillLevelUpEvent(player, type, oldLevel, newLevel));
			player.getSession().write(new SkillFrame(skill));
		}

		player.getPane().setVarp(SkillTab.ExperienceCounterVarp, (int) (variables.getExperienceCounter() * 10));
	}

	public static void initializeSkills(Player player) {
		SkillVariables variables = player.getSkillVariables();

		for (Skill skill : variables.getSkills()) {
			player.getSession().write(new SkillFrame(skill));
		}
		updateTargetLevels(player);
	}

	public static void updateTargetLevels(Player player) {
		SkillVariables variables = player.getSkillVariables();
		int targetValues = 0;
		int targetLevelValues = 0;
		for (Skill skill : variables.getSkills()) {
			if (skill.getTarget() > 0) {
				SkillType type = skill.getType();

				targetValues |= type.getTargetConfigValue();
				player.getPane().setVarp(type.getTargetConfigID(), (int) skill.getTarget());

				if (skill.isTargetingLevel()) {
					targetLevelValues |= type.getTargetConfigValue();
				}
			}
		}
		player.getPane().setVarp(SkillTab.SkillTargetIDVarp, targetValues);
		player.getPane().setVarp(SkillTab.SkillTargetValueVarp, targetLevelValues);
	}

	/**
	 * Sets the target for the skill with the given {@code type}. If the
	 * {@code targetingLevel} flag is given as true, then the target will be
	 * considered a level target instead of an experience target.
	 * 
	 * @param type           the type of skill
	 * @param target         the skill target
	 * @param targetingLevel the level target flag
	 */
	public void setTarget(SkillType type, double target, boolean targetingLevel) {
		this.skills[type.getID()].setTarget(target);
		this.skills[type.getID()].setTargetingLevel(targetingLevel);
	}

	/**
	 * Clears the target for the skill with the given {@code type} by setting the
	 * target for the skill to zero.
	 * 
	 * @param type the type of skill
	 */
	public void clearTarget(SkillType type) {
		this.skills[type.getID()].setTarget(0);
		this.skills[type.getID()].setTargetingLevel(false);
	}

	public void setLevel(SkillType type, int level) {
		Skill skill = getSkill(type);
		skill.setLevel(level);
		skill.setExperience(getExperienceForLevel(level));
	}

	public int getLevel(SkillType type) {
		return skills[type.getID()].getLevel();
	}

	public Skill getSkill(SkillType type) {
		return skills[type.getID()];
	}

	public void modify(SkillType type, double amount) {
		Skill skill = getSkill(type);
		skill.modifyByPercentage(amount);
	}

	public void modify(SkillType type, int amount) {
		Skill skill = getSkill(type);
		skill.modifyByAmount(amount);
	}

	/**
	 * Returns the experience required to reach the given level.
	 * 
	 * @param level the level to reach
	 * @return the experience required
	 */
	public static int getExperienceForLevel(int level) {
		int points = 0;
		int output = 0;
		for (int lvl = 1; lvl <= level; lvl++) {
			points += Math.floor(lvl + 300.0 * Math.pow(2.0, lvl / 7.0));
			if (lvl >= level)
				return output;
			output = (int) Math.floor(points / 4);
		}
		return 0;
	}

	/**
	 * Returns the level of the skill if the skill has experience equal to the given
	 * {@code experience}.
	 * 
	 * @param skillID    the id of the skill
	 * @param experience the experience of the skill
	 * @return the level for the given experience
	 */
	public static int getLevelForExperience(double experience) {
		int points = 0;
		int output = 0;
		for (int lvl = 1; lvl < 121; lvl++) {
			points += Math.floor(lvl + 300.0 * Math.pow(2.0, lvl / 7.0));
			output = (int) Math.floor(points / 4);
			if ((output - 1) >= experience)
				return lvl;
		}
		return 120;
	}

}
