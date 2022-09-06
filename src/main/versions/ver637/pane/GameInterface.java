package versions.ver637.pane;

/**
 * An interface meant to be added to the {@code GamePane}.
 * 
 * @author Albert Beaupre
 */
public abstract class GameInterface extends Interface {

	/**
	 * Constructs a new {@code GamePaneInterface} with the given
	 * {@code interfaceID}.
	 * 
	 * @param interfaceID the id of the interface
	 */
	public GameInterface(int interfaceID, boolean modal) {
		super(interfaceID, modal);
	}

	/**
	 * Returns the position of this {@code GamePaneInterface} based on the given
	 * {@code resizable} flag.
	 * 
	 * @param resizable if the parenting interface is the resizable game pane
	 * @return the position
	 */
	public abstract int position(boolean resizable);

	/**
	 * Sets the varc at the given {@code index} to the given {@code value}.
	 * 
	 * @param index the index of the varc
	 * @param value the value to set
	 */
	public void setVarc(int index, int value) {
		GamePane parent = GamePane.class.cast(this.getParent());

		parent.setVarc(index, value);
	}

	/**
	 * Sets the varp at the given {@code index} to the given {@code value}.
	 * 
	 * @param index the index of the varp
	 * @param value the value to set
	 */
	public void setVarp(int index, int value) {
		GamePane parent = GamePane.class.cast(this.getParent());

		parent.setVarp(index, value);
	}

	/**
	 * Sets the varbit at the given {@code index} to the given {@code value}.
	 * 
	 * @param index the index of the varbit
	 * @param value the value to set
	 */
	public void setVarbit(int index, int value) {
		GamePane parent = GamePane.class.cast(this.getParent());

		parent.setVarbit(index, value);
	}

	/**
	 * Sets the CS2String at the given {@code index} to the given {@code string}.
	 * 
	 * @param index  the index of the string
	 * @param string the string to set
	 */
	public void setCS2String(int index, String string) {
		GamePane parent = GamePane.class.cast(this.getParent());

		parent.setCS2String(index, string);
	}

	public void sendScript(int scriptID, Object... arguments) {
		GamePane parent = GamePane.class.cast(this.getParent());

		parent.sendScript(scriptID, arguments);
	}

	@Override
	public final int position(Interface parent) {
		return position(GamePane.class.cast(parent).isResizable());
	}

}
