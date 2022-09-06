package versions.ver637.pane.chat;

import versions.ver637.network.coders.frames.CloseDialogFrame;
import versions.ver637.pane.ComponentClick;
import versions.ver637.pane.Interface;

public class RequestInterface extends Interface {

	public enum RequestType {
		Integer,
		LongString,
		String
	}

	public static final int RequestID = 73;

	private final String title;
	private final RequestType type;

	public RequestInterface(String title, RequestType type) {
		super(RequestID, false);
		this.title = title;
		this.type = type;
	}

	@Override
	public void onOpen() {
		switch (type) {
			case Integer -> player.getPane().sendScript(108, title);
			case String -> player.getPane().sendScript(109, title);
			case LongString -> player.getPane().sendScript(110, title);
		}
	}

	@Override
	public void click(ComponentClick data) {}

	@Override
	public void onClose() {
		player.getSession().write(new CloseDialogFrame());
	}

	@Override
	public boolean clickThrough() {
		return false;
	}

	@Override
	public int position(Interface parent) {
		return 0;
	}

}
