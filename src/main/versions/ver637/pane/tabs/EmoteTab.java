package versions.ver637.pane.tabs;

import versions.ver637.pane.ComponentClick;
import versions.ver637.pane.GameInterfaceAdapter;

public class EmoteTab extends GameInterfaceAdapter {

	public static final int EmoteTab = 464;

	public EmoteTab() {
		super(EmoteTab, true);
	}

	@Override
	public void onOpen() {

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
		return resizable ? 100 : 215;
	}

}
