package versions.ver637.pane;

import lombok.Getter;
import versions.ver637.model.player.Player;
import versions.ver637.network.coders.frames.CS2StringFrame;
import versions.ver637.network.coders.frames.CloseInterfaceFrame;
import versions.ver637.network.coders.frames.InterfaceFrame;
import versions.ver637.network.coders.frames.VarbitFrame;
import versions.ver637.network.coders.frames.VarcFrame;
import versions.ver637.network.coders.frames.VarpFrame;

public class GamePane extends InterfaceWindow {

	@Getter
	private final Player player;

	private final int[] varps = new int[2000];
	private final int[] varcs = new int[1000];
	private final int[] varbits = new int[10000];
	private final String[] cs2Strings = new String[1000];

	public GamePane(Player player, boolean resizable) {
		super(resizable ? 746 : 548, -1);
		this.player = player;
	}

	/**
	 * Opens the given {@code window} for this {@code GamePane}.
	 * 
	 * @param window the interface to open
	 */
	public final void open(InterfaceWindow window) {
		this.addChild(window);

		player.getSession().write(new InterfaceFrame(this.getID(), window.getID(), window.getPosition(), window.clickThrough()));
	}

	/**
	 * Closes the given {@code window}.
	 * 
	 * @param window the interface to close
	 */
	public void close(InterfaceWindow window) {
		this.removeChild(window.getPosition());

		player.getSession().write(new CloseInterfaceFrame(window.getParent().getID(), window.getPosition()));
	}

	/**
	 * Returns the CS2 String at the given {@code index}. This value will return
	 * null if no CS2 string has been set.
	 * 
	 * @param index the index of the string
	 * @return the string set; possibly null
	 */
	public String getCS2String(int index) {
		return cs2Strings[index];
	}

	/**
	 * Sets the CS2String at the given {@code index} to the given {@code string}.
	 * 
	 * @param index  the index of the string
	 * @param string the string to set
	 */
	public void setCS2String(int index, String string) {
		this.cs2Strings[index] = string;

		player.getSession().write(new CS2StringFrame(index, string == null ? "" : string));
	}

	/**
	 * Returns the varp set at the given {@code index}.
	 * 
	 * @param index the index of the varp
	 * @return the varp set
	 */
	public int getVarp(int index) {
		return varps[index];
	}

	/**
	 * Sets the varp at the given {@code index} to the given {@code value}.
	 * 
	 * @param index the index of the varp
	 * @param value the value to set
	 */
	public void setVarp(int index, int value) {
		this.varps[index] = value;

		player.getSession().write(new VarpFrame(index, value));
	}

	/**
	 * Returns the varc set at the given {@code index}.
	 * 
	 * @param index the index of the varc
	 * @return the varc set
	 */
	public int getVarc(int index) {
		return varcs[index];
	}

	/**
	 * Sets the varc at the given {@code index} to the given {@code value}.
	 * 
	 * @param index the index of the varc
	 * @param value the value to set
	 */
	public void setVarc(int index, int value) {
		this.varcs[index] = value;

		player.getSession().write(new VarcFrame(index, value));
	}

	/**
	 * Returns the varbit set at the given {@code index}.
	 * 
	 * @param index the index of the varbit
	 * @return the varbit set
	 */
	public int getVarbit(int index) {
		return varbits[index];
	}

	/**
	 * Sets the varbit at the given {@code index} to the given {@code value}.
	 * 
	 * @param index the index of the varbit
	 * @param value the value to set
	 */
	public void setVarbit(int index, int value) {
		this.varbits[index] = value;

		player.getSession().write(new VarbitFrame(index, value));
	}

	@Override
	public void onOpen() {}

	@Override
	public void onClose() {}

	@Override
	public boolean clickThrough() {
		return false;
	}
}
