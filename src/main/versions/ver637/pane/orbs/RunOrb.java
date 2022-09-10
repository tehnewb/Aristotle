package versions.ver637.pane.orbs;

import versions.ver637.pane.ComponentClick;
import versions.ver637.pane.GameInterfaceAdapter;

public class RunOrb extends GameInterfaceAdapter {

	public static final int RunOrbID = 750;
	public static final int RunOrbVarp = 173;

	public RunOrb() {
		super(RunOrbID, true);
	}

	@Override
	public void onOpen() {
		this.setVarp(RunOrbVarp, player.getLocationVariables().isRunning() ? 1 : 0);
	}

	@Override
	public void click(ComponentClick click) {
		if (click.componentID() == 1) {
			if (click.option() == 0) { // Toggle
				player.getLocationVariables().setRunning(!player.getLocationVariables().isRunning());
				this.setVarp(RunOrbVarp, player.getLocationVariables().isRunning() ? 1 : 0);
			} else if (click.option() == 1) { // Rest

			}
		}
	}

	@Override
	public void onClose() {}

	@Override
	public int position(boolean resizable) {
		return resizable ? 176 : 185;
	}

	@Override
	public boolean clickThrough() {
		return true;
	}

}
