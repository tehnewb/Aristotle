package versions.ver637.pane.chat;

import versions.ver637.pane.GameInterfaceAdapter;

public class PrivateMessageInterface extends GameInterfaceAdapter {

	public static final int PrivateMessageID = 754;

	public PrivateMessageInterface() {
		super(PrivateMessageID, true);
	}

	@Override
	public boolean clickThrough() {
		return true;
	}

	@Override
	public int position(boolean resizable) {
		return resizable ? 70 : 16;
	}

}
