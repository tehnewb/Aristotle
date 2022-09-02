package versions.ver637.pane.chat;

import versions.ver637.pane.GameInterfaceAdapter;

public class ChatPaneInterface extends GameInterfaceAdapter {

	public static final int ChatPaneID = 752;

	public ChatPaneInterface() {
		super(ChatPaneID, true);
	}

	@Override
	public void onOpen() {
		this.addChild(new ChatPaneSubInterface());
	}

	@Override
	public boolean clickThrough() {
		return true;
	}

	@Override
	public int position(boolean resizable) {
		return resizable ? 69 : 192;
	}

}
