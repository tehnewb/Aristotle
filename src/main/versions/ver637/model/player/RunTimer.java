package versions.ver637.model.player;

import com.framework.mechanics.timer.RSTimer;
import com.framework.mechanics.timer.RSTimerType;
import com.framework.util.MathUtil;

import versions.ver637.model.player.skills.SkillType;
import versions.ver637.network.coders.frames.RunEnergyFrame;
import versions.ver637.pane.orbs.RunOrb;

public class RunTimer extends RSTimer<Player> {

	public RunTimer() {
		super(1, Long.MAX_VALUE);
	}

	@Override
	public void process(Player player) {
		LocationVariables variables = player.getAccount().getLocationVariables();
		int currentEnergy = variables.getRunEnergy();
		if (variables.isMoving() && variables.isRunning()) {
			float weight = player.getEquipmentVariables().getWeight();
			double MAX_WEIGHT = 64D;
			int decreaseAmount = (int) (67 + ((67 * MathUtil.clamp(weight, 0, MAX_WEIGHT)) / MAX_WEIGHT));
			int newRunEnergy = MathUtil.clamp(currentEnergy - decreaseAmount, 0, 10000);
			variables.setRunEnergy(newRunEnergy);
			if (newRunEnergy < 100) {
				player.getPane().setVarp(RunOrb.RunOrbVarp, 0);
				variables.setRunning(false);
			}
		} else {
			int agilityLevel = player.getSkillVariables().getLevelWithModifier(SkillType.Agility);
			int restoreAmount = (agilityLevel / 6) + 8;

			if (variables.isResting())
				restoreAmount += 200;

			int newRunEnergy = MathUtil.clamp(currentEnergy + restoreAmount, 0, 10000);
			variables.setRunEnergy(newRunEnergy);
		}
		int energy = (int) ((variables.getRunEnergy() / 10000D) * 100);
		player.getSession().write(new RunEnergyFrame(energy));
	}

	@Override
	public RSTimerType type() {
		return RSTimerType.Soft;
	}

}
