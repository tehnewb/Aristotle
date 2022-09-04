package versions.ver637.pane.tabs;

import versions.ver637.pane.ComponentClick;
import versions.ver637.pane.GameInterface;

public class InventoryTab extends GameInterface {

	public static final int InventoryID = 149;

	public InventoryTab() {
		super(InventoryID, true);
	}

	@Override
	public void onOpen() {}

	@Override
	public void click(ComponentClick data) {}

	@Override
	public void onClose() {}

	@Override
	public boolean clickThrough() {
		return true;
	}

	@Override
	public int position(boolean resizable) {
		return resizable ? 91 : 206;
	}

}
