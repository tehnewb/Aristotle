package com.framework.pane;

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

	/**
	 * Constructs a new {@code RSWindow} with the given {@code interfaceID} and
	 * {@code position}. The position will determine where this {@code RSWindow}
	 * will be placed on its parenting window.
	 * 
	 * @param interfaceID the interface id of this window
	 * @param position    the position
	 */
	public RSWindow(int interfaceID, int position) {
		this.ID = (short) interfaceID;
		this.position = (byte) position;
		this.children = new HashMap<>();
		this.components = new HashMap<>();
	}

	/**
	 * Adds the given {@code window} as a child to this {@code RSWindow}.
	 * 
	 * @param window the window to add
	 */
	public void addChild(@NonNull RSWindow window) {
		window.parent = this;
		children.put((int) window.getPosition(), window);
	}

	/**
	 * Adds an {@code RSComponent} as a component to this {@code RSWindow}.
	 * 
	 * @param component the component to add
	 */
	public void addComponent(@NonNull RSComponent component) {
		component.setParent(this);
		components.put(component.getID(), component);
	}

	/**
	 * Returns the {@code RSComponent} with the corresponding {@code ID}.
	 * 
	 * @param ID the id of the component
	 * @return the component
	 */
	public RSComponent getComponent(int ID) {
		return components.get(ID);
	}

}
