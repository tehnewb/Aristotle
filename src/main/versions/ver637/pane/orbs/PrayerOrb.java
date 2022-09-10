package versions.ver637.pane.orbs;

import versions.ver637.model.player.prayer.Prayer;
import versions.ver637.model.player.prayer.PrayerVariables;
import versions.ver637.pane.ComponentClick;
import versions.ver637.pane.GameInterfaceAdapter;

public class PrayerOrb extends GameInterfaceAdapter {

	public static final int PrayerOrbID = 749;

	public PrayerOrb() {
		super(PrayerOrbID, true);
	}

	@Override
	public void click(ComponentClick data) {
		if (data.componentID() == 1) {
			if (data.option() == 0) {
				if (player.getPrayerVariables().isSelectingQuickPrayers()) {
					return;
				}
				player.getPrayerVariables().setQuickPrayersActivated(!player.getPrayerVariables().isQuickPrayersActivated());

				Prayer[] quickPrayers = player.getPrayerVariables().getQuickPrayers();

				if (quickPrayers.length == 0) {
					player.sendMessage("You don't have any quick prayers selected.");
					return;
				}

				for (Prayer prayer : quickPrayers) {
					if (player.getPrayerVariables().isQuickPrayersActivated()) {
						if (!player.getPrayerVariables().isActivated(prayer))
							PrayerVariables.switchPrayerActivation(player, prayer.getSlot());
					} else {
						player.getPrayerVariables().deactivate(prayer);
						PrayerVariables.refreshPrayers(player);
					}
				}
			} else if (data.option() == 1) {
				player.getPrayerVariables().setSelectingQuickPrayers(!player.getPrayerVariables().isSelectingQuickPrayers());

				boolean on = player.getPrayerVariables().isSelectingQuickPrayers();
				setVarc(182, 0);
				setVarc(181, on ? 1 : 0);
				setVarc(168, 6);
				PrayerVariables.refreshPrayers(player);
			}
		}
	}

	@Override
	public int position(boolean resizable) {
		return resizable ? 175 : 184;
	}

	@Override
	public boolean clickThrough() {
		return true;
	}

}
