package versions.ver637.pane.chat;

import versions.ver637.pane.ComponentClick;
import versions.ver637.pane.GamePaneInterface;

public class PrivateMessageInterface extends GamePaneInterface {

	public PrivateMessageInterface() {
		super(754, true);
	}

	@Override
	public void click(ComponentClick data) {

	}

	@Override
	public void onOpen() {

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
		return resizable ? 70 : 16;
	}

}
