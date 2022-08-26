package versions.ver637.window;

import java.util.HashMap;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import versions.ver637.model.player.Player;

/**
 * 
 * @author Albert Beaupre
 */
public class RSWindow {

	private final HashMap<Integer, RSWindow> children;
	private final HashMap<Integer, RSComponent> components;

	@Getter
	private RSWindow parent;

	@Getter
	private short ID;

	@Getter
	private byte position;

	@Getter
	@Setter
	protected Player player;

	public RSWindow(int interfaceID, int position) {
		this.ID = (short) interfaceID;
		this.position = (byte) position;
		this.children = new HashMap<>();
		this.components = new HashMap<>();
	}

	public void addChild(@NonNull RSWindow window) {
		window.parent = this;
		children.put((int) window.getPosition(), window);
	}

	public void addComponent(@NonNull RSComponent component) {
		component.setParent(this);
		components.put(component.getID(), component);
	}

}
