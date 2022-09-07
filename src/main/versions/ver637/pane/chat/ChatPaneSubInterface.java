package versions.ver637.pane.chat;

import versions.ver637.pane.ComponentClick;
import versions.ver637.pane.ComponentSettings;
import versions.ver637.pane.ComponentSwap;
import versions.ver637.pane.Interface;

public class ChatPaneSubInterface extends Interface {

	public static final int ChatPaneSubID = 137;

	public ChatPaneSubInterface() {
		super(ChatPaneSubID, true);
	}

	@Override
	public void onOpen() {
		ComponentSettings settings = new ComponentSettings();
		for (int i = 0; i < 9; i++)
			settings.setSecondaryOption(i, true);
		this.getComponent(58).setSettings(settings, 0, 99);
	}

	@Override
	public void click(ComponentClick data) {

	}

	@Override
	public void onClose() {

	}

	@Override
	public boolean clickThrough() {
		return true;
	}

	@Override
	public int position(Interface parent) {
		return 9;
	}

	@Override
	public void swap(ComponentSwap data) {

	}

}
