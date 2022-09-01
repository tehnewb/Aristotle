package versions.ver637.pane;

import java.util.HashMap;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import versions.ver637.model.player.Player;
import versions.ver637.network.coders.frames.CloseInterfaceFrame;
import versions.ver637.network.coders.frames.InterfaceFrame;

/**
 * 
 * @author Albert Beaupre
 */
public abstract class Interface {

	protected final HashMap<Integer, Interface> children;
	protected final HashMap<Integer, InterfaceComponent> components;

	@Getter
	@Setter
	private Interface parent;

	@Getter
	private short ID;

	@Getter
	@Setter
	protected Player player;

	@Getter
	@Setter
	private boolean modal;

	/**
	 * Constructs a new {@code Interface} with the given {@code interfaceID} and
	 * {@code position}. The position will determine where this {@code Interface}
	 * will be placed on its parenting interface.
	 * 
	 * @param interfaceID the id of this interface
	 * @param position    the position to its parent
	 */
	public Interface(int interfaceID, boolean modal) {
		this.ID = (short) interfaceID;
		this.modal = modal;
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

	public abstract void click(ComponentClick data);

	/**
	 * Executes when the window opens
	 */
	public abstract void onOpen();

	/**
	 * Executes when the window closes
	 */
	public abstract void onClose();

	/**
	 * Returns true if this {@code Interface} can be clicked through.
	 * 
	 * @return true if clicked through; false otherwise
	 */
	public abstract boolean clickThrough();

	/**
	 * Returns the position of this {@code Interface} based on the given
	 * {@code parent}.
	 * 
	 * @param parent the parenting interface
	 * @return the position
	 */
	public abstract int position(Interface parent);

	/**
	 * Adds the given {@code window} as a child to this {@code Interface}.
	 * 
	 * @param window the window to add
	 */
	public void addChild(@NonNull Interface window) {
		window.parent = this;
		window.player = player;
		window.onOpen();
		children.put((int) window.position(this), window);

		player.getSession().write(new InterfaceFrame(this.getID(), window.getID(), window.position(this), window.clickThrough()));
	}

	/**
	 * Adds an {@code InterfaceComponent} as a component to this {@code Interface}.
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
		Interface window = children.remove(position);
		if (window != null)
			window.onClose();

		player.getSession().write(new CloseInterfaceFrame(this.getID(), position));
	}

	/**
	 * Returns the {@code Interface} with the corresponding {@code ID}.
	 * 
	 * @param ID the id of the component
	 * @return the component
	 */
	public InterfaceComponent getComponent(int ID) {
		InterfaceComponent component = components.get(ID);
		if (component != null && component.getPlayer() == null)
			component.setPlayer(player);
		return component;
	}

	/**
	 * Returns the child for the given {@code ID}. If there is no child the that ID,
	 * then null is returned.
	 * 
	 * @param ID the id of the child
	 * @return the child found; possibly null
	 */
	public Interface getChildForID(int ID) {
		return children.values().stream().filter(i -> i.getID() == ID).findFirst().orElse(null);
	}

}
