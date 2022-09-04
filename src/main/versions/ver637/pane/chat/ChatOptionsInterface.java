package versions.ver637.pane.chat;

import versions.ver637.pane.GameInterfaceAdapter;

public class ChatOptionsInterface extends GameInterfaceAdapter {

	public static final int ChatOptionsID = 751;

	public ChatOptionsInterface() {
		super(ChatOptionsID, true);
	}

	@Override
	public void onOpen() {
		this.setVarp(281, 1000); // tutorial unlocked
	}

	@Override
	public boolean clickThrough() {
		return true;
	}

	@Override
	public int position(boolean resizable) {
		return resizable ? 16 : 67;
	}

}
