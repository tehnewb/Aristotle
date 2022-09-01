package versions.ver637.pane;

import lombok.Getter;
import versions.ver637.model.player.Player;
import versions.ver637.network.coders.frames.CS2StringFrame;
import versions.ver637.network.coders.frames.VarbitFrame;
import versions.ver637.network.coders.frames.VarcFrame;
import versions.ver637.network.coders.frames.VarpFrame;
import versions.ver637.pane.chat.ChatOptionsInterface;
import versions.ver637.pane.chat.ChatPaneInterface;
import versions.ver637.pane.chat.PrivateMessageInterface;
import versions.ver637.pane.orbs.RunOrbInterface;
import versions.ver637.pane.tabs.FriendListTab;

public class GamePane extends Interface {

	private final int[] varps = new int[2000];
	private final int[] varcs = new int[1000];
	private final int[] varbits = new int[10000];
	private final String[] cs2Strings = new String[1000];

	@Getter
	private boolean resizable;

	/**
	 * Constructs a new {@code GamePane}. If the {@code resizable} flag is set to
	 * true, the ID of this game pane will be 746, otherwise it will be 548.
	 * 
	 * @param player    the player this game pane is fore
	 * @param resizable the resizable flag
	 */
	public GamePane(Player player, boolean resizable) {
		super(resizable ? 746 : 548, true);
		this.resizable = resizable;
		this.player = player;
	}

	/**
	 * Opens the given {@code window} for this {@code GamePane}.
	 * 
	 * @param window the interface to open
	 */
	public final void open(Interface window) {
		this.addChild(window);
	}

	/**
	 * Closes the given {@code window}.
	 * 
	 * @param window the interface to close
	 */
	public void close(Interface window) {
		this.removeChild(window.position(this));
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
	public void onOpen() {
		this.open(new FriendListTab());
		this.open(new RunOrbInterface());
		this.open(new ChatPaneInterface());
		this.open(new ChatOptionsInterface());
		this.open(new PrivateMessageInterface());
	}

	@Override
	public void click(ComponentClick data) {

	}

	@Override
	public void onClose() {
		for (Interface window : this.children.values()) {
			window.onClose();
		}
	}

	@Override
	public boolean clickThrough() {
		return false;
	}

	@Override
	public int position(Interface parent) {
		return -1;
	}
}
