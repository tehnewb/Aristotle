package versions.ver637.pane;

import java.util.function.Consumer;

import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Accessors(fluent = true, chain = true)
public class GameInterfaceBuilder extends GameInterface {

	private int positionFixed;
	private int positionResizable;
	private boolean clickThrough;
	private Consumer<GameInterface> onClose;
	private Consumer<GameInterface> onOpen;
	private Consumer<ComponentClick> onClick;

	public GameInterfaceBuilder(int interfaceID, boolean modal) {
		super(interfaceID, modal);
	}

	@Override
	public int position(boolean resizable) {
		return resizable ? positionResizable : positionFixed;
	}

	@Override
	public void click(ComponentClick data) {
		onClick.accept(data);
	}

	@Override
	public void onOpen() {
		onOpen.accept(this);
	}

	@Override
	public void onClose() {
		onClose.accept(this);
	}

	@Override
	public boolean clickThrough() {
		return clickThrough;
	}

}
