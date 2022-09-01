package versions.ver637.pane.chat;

import versions.ver637.pane.ComponentClick;
import versions.ver637.pane.GamePaneInterface;

public class ChatPaneInterface extends GamePaneInterface {

	public ChatPaneInterface() {
		super(752, true);
	}

	@Override
	public void onOpen() {
		this.addChild(new ChatPaneSubInterface());

	}

	@Override
	public void click(ComponentClick data) {

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
		return resizable ? 69 : 192;
	}

}
