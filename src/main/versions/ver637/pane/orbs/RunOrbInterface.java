package versions.ver637.pane.orbs;

import com.framework.tick.RSGameTick;
import com.framework.util.MathUtil;

import versions.ver637.model.player.LocationVariables;
import versions.ver637.model.player.Player;
import versions.ver637.network.coders.frames.RunEnergyFrame;
import versions.ver637.pane.ComponentClick;
import versions.ver637.pane.GamePaneInterface;

public class RunOrbInterface extends GamePaneInterface {

	public static final int RunOrbID = 750;

	public RunOrbInterface() {
		super(RunOrbID, true);
	}

	@Override
	public void onOpen() {
		this.setVarp(173, player.getLocationVariables().isRunning() ? 1 : 0);

		player.getTickVariables().addTick(new RunOrbTick(this));
	}

	@Override
	public void click(ComponentClick click) {
		if (click.componentID() == 1) {
			if (click.option() == 0) { // Toggle
				player.getLocationVariables().setRunning(!player.getLocationVariables().isRunning());
				this.setVarp(173, player.getLocationVariables().isRunning() ? 1 : 0);
			} else if (click.option() == 1) { // Rest

			}
		}
	}

	@Override
	public void onClose() {
		player.getTickVariables().removeTick(RunOrbTick.TickName);
	}

	@Override
	public boolean clickThrough() {
		return false;
	}

	@Override
	public int position(boolean resizable) {
		return resizable ? 176 : 185;
	}

	private class RunOrbTick extends RSGameTick {

		public static final String TickName = "RunOrbTick";

		private final RunOrbInterface runOrbInterface;

		public RunOrbTick(RunOrbInterface runOrbInterface) {
			super(TickName);
			this.runOrbInterface = runOrbInterface;
		}

		@Override
		protected void tick() {
			Player player = runOrbInterface.getPlayer();
			LocationVariables variables = player.getAccount().getLocationVariables();
			int currentEnergy = variables.getRunEnergy();
			/**
			 * https://oldschool.runescape.wiki/w/Energy
			 */
			if (variables.isMoving() && variables.isRunning()) {
				double weight = 0.0;
				double MAX_WEIGHT = 64D;
				int decreaseAmount = (int) (67 + ((67 * MathUtil.clamp(weight, 0, MAX_WEIGHT)) / MAX_WEIGHT));
				int newRunEnergy = MathUtil.clamp(currentEnergy - decreaseAmount, 0, 10000);
				variables.setRunEnergy(newRunEnergy);
				if (newRunEnergy < 100) {
					runOrbInterface.setVarp(173, 0);
					variables.setRunning(false);
				}
			} else {
				int agilityLevel = 99;
				int restoreAmount = (agilityLevel / 6) + 8;

				if (variables.isResting())
					restoreAmount += 100;

				int newRunEnergy = MathUtil.clamp(currentEnergy + restoreAmount, 0, 10000);
				variables.setRunEnergy(newRunEnergy);

			}
			int energy = (int) ((variables.getRunEnergy() / 10000D) * 100);
			player.getSession().write(new RunEnergyFrame(energy));
		}

	}

}
