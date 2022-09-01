package versions.ver637.pane.chat;

import versions.ver637.pane.ComponentClick;
import versions.ver637.pane.GamePaneInterface;

public class ChatOptionsInterface extends GamePaneInterface {

	public ChatOptionsInterface() {
		super(751, true);
	}

	@Override
	public void onOpen() {
		this.setVarp(281, 1000); // tutorial unlocked
	}

	@Override
	public void click(ComponentClick data) {

	}

	@Override
	public void onClose() {

	}

	@Override
	public boolean clickThrough() {
		return false;
	}

	@Override
	public int position(boolean resizable) {
		return resizable ? 16 : 67;
	}

}
