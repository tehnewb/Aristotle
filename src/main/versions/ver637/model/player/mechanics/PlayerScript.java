package versions.ver637.model.player.mechanics;

import com.framework.map.RSLocation;
import com.framework.mechanics.queue.RSScript;
import com.framework.mechanics.timer.RSTimer;

import versions.ver637.model.UpdateFlag;
import versions.ver637.model.item.Item;
import versions.ver637.model.player.LocationVariables;
import versions.ver637.model.player.Player;
import versions.ver637.model.player.flags.AnimationFlag;
import versions.ver637.model.player.flags.GraphicFlag;
import versions.ver637.model.player.flags.TeleportFlag;
import versions.ver637.pane.Interface;

public abstract class PlayerScript implements RSScript<Player> {

	protected Player player;

	@Override
	public final void process(Player player) {
		this.player = player;

		process();
	}

	/**
	 * Processes this {@code PlayerScript} for the given {@code player}.
	 */
	public abstract void process();

	/**
	 * Adds the given timer to the player
	 */
	public void timer(RSTimer<Player> timer) {
		player.getTimers().addTimer(timer);
	}

	/**
	 * Queues the given script to the player
	 */
	public void script(RSScript<Player> script) {
		player.getScripts().queue(script);
	}

	/**
	 * Delays the player from executing any other scripts until the given ticks
	 * pass.
	 */
	public void delay(int ticks) {
		player.getScripts().delay(ticks);
	}

	/**
	 * Teleports the player to the given location
	 */
	public void teleport(RSLocation destination) {
		LocationVariables.resetRoute(player);
		player.setLocation(destination);
		flag(new TeleportFlag());
	}

	/**
	 * Animates the player
	 */
	public void animate(int animationID) {
		this.flag(new AnimationFlag(animationID));
	}

	/**
	 * Sends a graphic to the player
	 */
	public void gfx(int gfxID) {
		this.flag(new GraphicFlag(gfxID));
	}

	/**
	 * Sends a message to the player
	 */
	public void msg(String string, Object... arguments) {
		player.sendMessage(string, arguments);
	}

	/**
	 * Opens an interface for the player
	 */
	public void openInterface(Interface window) {
		player.getPane().open(window);
	}

	/**
	 * Adds the given item to the player's inventory
	 */
	public void addItem(Item item) {
		player.getInventory().addItem(item);
	}

	/**
	 * Removes the given item from the player's inventory
	 */
	public void removeItem(Item item) {
		player.getInventory().removeItem(item);
	}

	/**
	 * Registers the given flag to the player's model
	 */
	public void flag(UpdateFlag flag) {
		player.getModel().registerFlag(flag);
	}

	public int getVarp(int index) {
		return player.getPane().getVarp(index);
	}

	public void setVarp(int index, int value) {
		player.getPane().setVarp(index, value);
	}

	public int getVarc(int index) {
		return player.getPane().getVarc(index);
	}

	public void setVarc(int index, int value) {
		player.getPane().setVarc(index, value);
	}

	public int getVarbit(int index) {
		return player.getPane().getVarbit(index);
	}

	public void setVarbit(int index, int value) {
		player.getPane().setVarbit(index, value);
	}

}
