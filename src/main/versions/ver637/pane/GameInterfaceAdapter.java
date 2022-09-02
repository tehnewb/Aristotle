package versions.ver637.pane;

public class GameInterfaceAdapter extends GameInterface {

	public GameInterfaceAdapter(int interfaceID, boolean modal) {
		super(interfaceID, modal);
	}

	@Override
	public int position(boolean resizable) {
		return resizable ? 9 : 18;
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
		return false;
	}

}
