package versions.ver637.pane;

import lombok.Getter;
import versions.ver637.model.player.Player;
import versions.ver637.network.coders.frames.CS2Frame;
import versions.ver637.network.coders.frames.CS2StringFrame;
import versions.ver637.network.coders.frames.VarbitFrame;
import versions.ver637.network.coders.frames.VarcFrame;
import versions.ver637.network.coders.frames.VarpFrame;
import versions.ver637.pane.chat.ChatOptionsInterface;
import versions.ver637.pane.chat.ChatPaneInterface;
import versions.ver637.pane.chat.IntegerRequest;
import versions.ver637.pane.chat.LongStringRequest;
import versions.ver637.pane.chat.PrivateMessageInterface;
import versions.ver637.pane.chat.StringRequest;
import versions.ver637.pane.orbs.RunOrbInterface;
import versions.ver637.pane.tabs.AchievementTab;
import versions.ver637.pane.tabs.ClanChatTab;
import versions.ver637.pane.tabs.FriendListTab;
import versions.ver637.pane.tabs.GraphicSettingsTab;
import versions.ver637.pane.tabs.IgnoreListTab;
import versions.ver637.pane.tabs.InventoryTab;
import versions.ver637.pane.tabs.LogoutTab;
import versions.ver637.pane.tabs.MusicTab;
import versions.ver637.pane.tabs.NotesTab;
import versions.ver637.pane.tabs.SkillTab;

public class GamePane extends Interface {

	private final int[] varps = new int[3000];
	private final int[] varcs = new int[2000];
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

	/**
	 * Sends the client script for the corresponding {@code scriptID} with the given
	 * {@code arguments}.
	 * 
	 * @param scriptID  the id of the script
	 * @param arguments the arguments for the script
	 */
	public void sendScript(int scriptID, Object... arguments) {
		player.getSession().write(new CS2Frame(scriptID, arguments));
	}

	/**
	 * Requests for integer input from the player
	 * 
	 * @param request the request
	 */
	public void requestInteger(IntegerRequest request) {
		this.getChildForID(752).addChild(request);
	}

	/**
	 * Requests for a long string input from the player
	 * 
	 * @param request the request
	 */
	public void requestLongString(LongStringRequest request) {
		this.getChildForID(752).addChild(request);
	}

	/**
	 * Requests for string input from the player
	 * 
	 * @param request the request
	 */
	public void requestString(StringRequest request) {
		this.getChildForID(752).addChild(request);
	}

	public LongStringRequest getLongStringRequest() {
		return this.getChildForID(752).getChild(LongStringRequest.class);
	}

	public StringRequest getStringRequest() {
		return this.getChildForID(752).getChild(StringRequest.class);
	}

	public IntegerRequest getIntegerRequest() {
		return this.getChildForID(752).getChild(IntegerRequest.class);
	}

	/**
	 * Closes the previous request
	 */
	public void closeRequest() {
		this.getChildForID(752).removeChild(0);
	}

	@Override
	public void onOpen() {
		/**
		 * Tabs
		 */
		this.open(new AchievementTab());
		this.open(new SkillTab());
		this.open(new LogoutTab());
		this.open(new IgnoreListTab());
		this.open(new FriendListTab());
		this.open(new ClanChatTab());
		this.open(new InventoryTab());
		this.open(new GraphicSettingsTab());
		this.open(new MusicTab());
		this.open(new NotesTab());

		/**
		 * Orbs
		 */
		this.open(new RunOrbInterface());

		/**
		 * Chat
		 */
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
		return true;
	}

	@Override
	public int position(Interface parent) {
		return -1;
	}
}
