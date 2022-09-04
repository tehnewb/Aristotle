package versions.ver637.pane.tabs;

import versions.ver637.cache.EnumResource;
import versions.ver637.cache.EnumResource.EnumData;
import versions.ver637.model.player.music.MusicVariables;
import versions.ver637.pane.ComponentClick;
import versions.ver637.pane.ComponentSettings;
import versions.ver637.pane.GameInterface;

public class MusicTab extends GameInterface {

	public static final int MusicID = 187;

	public MusicTab() {
		super(MusicID, true);
	}

	@Override
	public void onOpen() {
		MusicVariables.initializeUnlocks(player);

		ComponentSettings settings = new ComponentSettings();
		settings.setInterfaceDepth(0);
		settings.setSecondaryOption(0, true);

		EnumData data = EnumResource.getEnumData(1351);

		this.getComponent(1).setSettings(settings, 0, data.getMap().size() * 2);
	}

	@Override
	public void click(ComponentClick data) {
		if (data.componentID() == 1) {
			int musicIndex = data.slot() / 2;
			MusicVariables.playMusic(player, musicIndex);
		}
	}

	@Override
	public void onClose() {

	}

	@Override
	public boolean clickThrough() {
		return true;
	}

	@Override
	public int position(boolean resizable) {
		return resizable ? 101 : 216;
	}

}
