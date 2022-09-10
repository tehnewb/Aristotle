package versions.ver637.pane.tabs;

import versions.ver637.model.player.prayer.PrayerVariables;
import versions.ver637.pane.ComponentClick;
import versions.ver637.pane.ComponentSettings;
import versions.ver637.pane.ComponentSwap;
import versions.ver637.pane.GameInterface;

public class PrayerTab extends GameInterface {

	public static final int PrayerID = 271;

	public PrayerTab() {
		super(PrayerID, true);
	}

	@Override
	public void onOpen() {
		ComponentSettings settings = new ComponentSettings();
		settings.setSecondaryOption(0, true);

		this.getComponent(8).setSettings(settings, 0, 30); // regular prayer slots
		this.getComponent(42).setSettings(settings, 0, 30); // quick prayer slots

		this.setVarc(181, 0); // activate prayer book

		PrayerVariables.refreshPrayers(player);
	}

	@Override
	public void click(ComponentClick data) {
		if (data.componentID() == 8 || data.componentID() == 42) {
			PrayerVariables.switchPrayerActivation(player, data.slot());
		} else if (data.componentID() == 43) { // quick prayers
			player.getPrayerVariables().setSelectingQuickPrayers(false);
			setVarc(182, 0);
			setVarc(181, 0);
		}
	}

	@Override
	public void swap(ComponentSwap data) {

	}

	@Override
	public void onClose() {

	}

	@Override
	public int position(boolean resizable) {
		return resizable ? 93 : 208;
	}

	@Override
	public boolean clickThrough() {
		return true;
	}

}
