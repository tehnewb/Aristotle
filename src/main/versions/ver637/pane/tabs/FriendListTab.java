package versions.ver637.pane.tabs;

import versions.ver637.model.player.FriendVariables;
import versions.ver637.pane.GameInterfaceAdapter;

public class FriendListTab extends GameInterfaceAdapter {

	public static final int FriendListID = 550;

	public FriendListTab() {
		super(FriendListID, true);
	}

	@Override
	public void onOpen() {
		FriendVariables.initializeFriendsList(player);
		FriendVariables.alertOnline(player);
	}

	@Override
	public void onClose() {
		FriendVariables.alertOffline(player);
	}

	@Override
	public boolean clickThrough() {
		return true;
	}

	@Override
	public int position(boolean resizable) {
		return resizable ? 96 : 211;
	}

}
