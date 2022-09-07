package versions.ver637.pane.tabs;

import versions.ver637.pane.ComponentClick;
import versions.ver637.pane.ComponentSettings;
import versions.ver637.pane.GameInterfaceAdapter;

public class QuestTab extends GameInterfaceAdapter {

	public static final int QuestID = 190;
	public static final int QuestPointsVarp = 101;
	public static final int TotalQuestPointsVarp = 904;

	public QuestTab() {
		super(QuestID, true);
	}

	@Override
	public void onOpen() {
		this.setVarp(QuestPointsVarp, 1);
		this.setVarp(TotalQuestPointsVarp, 298);

		ComponentSettings settings = new ComponentSettings();
		settings.setSecondaryOption(0, true);
		settings.setSecondaryOption(1, true);
		settings.setSecondaryOption(2, true);
		this.getComponent(18).setSettings(settings, 0, 300);
	}

	@Override
	public void click(ComponentClick data) {

	}

	@Override
	public void onClose() {}

	@Override
	public boolean clickThrough() {
		return true;
	}

	@Override
	public int position(boolean resizable) {
		return resizable ? 90 : 205;
	}

}
