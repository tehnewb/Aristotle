package versions.ver637.pane.tabs;

import versions.ver637.network.coders.frames.ExitToLobbyFrame;
import versions.ver637.network.coders.frames.ExitToLoginFrame;
import versions.ver637.pane.ComponentClick;
import versions.ver637.pane.GameInterfaceAdapter;

public class LogoutTab extends GameInterfaceAdapter {

	public static final int LogoutID = 182;
	public static final int LobbyComponent = 5;
	public static final int LoginComponent = 10;

	public LogoutTab() {
		super(LogoutID, true);
	}

	@Override
	public void click(ComponentClick click) {
		switch (click.componentID()) {
			case LobbyComponent:
				player.getSession().write(new ExitToLobbyFrame());
				break;
			case LoginComponent:
				player.getSession().write(new ExitToLoginFrame());
				break;
		}
	}

	@Override
	public boolean clickThrough() {
		return true;
	}

	@Override
	public int position(boolean resizable) {
		return resizable ? 105 : 220;
	}

}
