package versions.ver637.pane.tabs;

import versions.ver637.model.player.FriendVariables;
import versions.ver637.pane.ComponentClick;
import versions.ver637.pane.GamePaneInterface;

public class FriendListTab extends GamePaneInterface {

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
	public void click(ComponentClick data) {

	}

	@Override
	public void onClose() {
		FriendVariables.alertOffline(player);
	}

	@Override
	public boolean clickThrough() {
		return false;
	}

	@Override
	public int position(boolean resizable) {
		return resizable ? 96 : 202;
	}

}
