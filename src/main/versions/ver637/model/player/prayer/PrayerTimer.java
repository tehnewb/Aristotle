package versions.ver637.model.player.prayer;

import com.framework.mechanics.timer.RSTimer;
import com.framework.mechanics.timer.RSTimerType;

import versions.ver637.model.player.Player;
import versions.ver637.model.player.equipment.EquipmentVariables;
import versions.ver637.model.player.skills.Skill;
import versions.ver637.model.player.skills.SkillType;
import versions.ver637.network.coders.frames.SkillFrame;

/**
 * https://oldschool.runescape.wiki/w/Prayer
 */
public class PrayerTimer extends RSTimer<Player> {

	private int drainCounter;

	public PrayerTimer() {
		super(1, Long.MAX_VALUE);
	}

	@Override
	public void process(Player player) {
		Prayer[] activated = player.getPrayerVariables().getActivatedPrayers();

		if (activated.length == 0)
			return;

		int prayerBonus = player.getEquipmentVariables().getBonuses()[EquipmentVariables.PrayerBonus];
		int drainResistance = 2 * prayerBonus + 60;

		for (Prayer prayer : activated) {
			drainCounter += prayer.getDrainEffect();
			if (drainCounter > drainResistance) {
				drainCounter -= drainResistance;
				player.getPrayerVariables().setPrayerPoints(player.getPrayerVariables().getPrayerPoints() - 1);
			}
		}

		Skill skill = player.getSkillVariables().getSkill(SkillType.Prayer);
		skill.setModifier(-(skill.getActualLevel() - player.getPrayerVariables().getPrayerPoints()));
		player.getSession().write(new SkillFrame(skill));
	}

	@Override
	public RSTimerType type() {
		return RSTimerType.Soft;
	}

}
