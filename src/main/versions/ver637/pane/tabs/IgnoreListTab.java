package versions.ver637.pane.tabs;

import versions.ver637.model.player.FriendVariables;
import versions.ver637.pane.GameInterfaceAdapter;

public class IgnoreListTab extends GameInterfaceAdapter {

	public static final int IgnoreListID = 551;

	public IgnoreListTab() {
		super(IgnoreListID, true);
	}

	@Override
	public void onOpen() {
		FriendVariables.initializeIgnoreList(player);
	}

	@Override
	public boolean clickThrough() {
		return true;
	}

	@Override
	public int position(boolean resizable) {
		return resizable ? 97 : 212;
	}

}
