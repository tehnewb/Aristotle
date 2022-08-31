package versions.ver637.pane;

import java.util.HashMap;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import versions.ver637.model.player.Player;

/**
 * 
 * @author Albert Beaupre
 */
public abstract class InterfaceWindow {

	private final HashMap<Integer, InterfaceWindow> children;
	private final HashMap<Integer, InterfaceComponent> components;

	@Getter
	private InterfaceWindow parent;

	@Getter
	private short ID;

	@Getter
	private byte position;

	@Getter
	@Setter
	protected Player player;

	/**
	 * Constructs a new {@code RSInterface} with the given {@code interfaceID} and
	 * {@code position}. The position will determine where this {@code RSInterface}
	 * will be placed on its parenting window.
	 * 
	 * @param interfaceID the interface id of this window
	 * @param position    the position
	 */
	public InterfaceWindow(int interfaceID, int position) {
		this.ID = (short) interfaceID;
		this.position = (byte) position;
		this.children = new HashMap<>();
		this.components = new HashMap<>();

		InterfaceData data = InterfaceResource.getData(interfaceID);
		for (InterfaceComponentData componentData : data.components) {
			InterfaceComponent component = new InterfaceComponent(componentData.componentID);
			component.setParent(this);
			component.setHidden(componentData.hidden);
			component.setText(componentData.text);
			component.setOptions(componentData.optionNames);

			this.addComponent(component);
		}
	}

	/**
	 * Executes when the window opens
	 */
	public abstract void onOpen();

	/**
	 * Executes when the window closes
	 */
	public abstract void onClose();

	/**
	 * Returns true if this {@code RSInterface} can be clicked through.
	 * 
	 * @return true if clicked through; false otherwise
	 */
	public abstract boolean clickThrough();

	/**
	 * Adds the given {@code window} as a child to this {@code RSInterface}.
	 * 
	 * @param window the window to add
	 */
	public void addChild(@NonNull InterfaceWindow window) {
		window.parent = this;
		children.put((int) window.getPosition(), window);
	}

	/**
	 * Adds an {@code RSComponent} as a component to this {@code RSInterface}.
	 * 
	 * @param component the component to add
	 */
	public void addComponent(@NonNull InterfaceComponent component) {
		component.setParent(this);
		components.put(component.getID(), component);
	}

	/**
	 * Removes the child at the given {@code position}.
	 */
	public void removeChild(int position) {
		children.remove(position);
	}

	/**
	 * Returns the {@code RSComponent} with the corresponding {@code ID}.
	 * 
	 * @param ID the id of the component
	 * @return the component
	 */
	public InterfaceComponent getComponent(int ID) {
		return components.get(ID);
	}

}
