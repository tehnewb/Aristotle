package versions.ver637.model.player.mechanics;

import java.util.function.Consumer;

import com.framework.map.RSLocation;
import com.framework.map.path.RSRouteBuilder;
import com.framework.mechanics.timer.RSRoutineTimer;
import com.framework.mechanics.timer.RSTimerRoutine;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import versions.ver637.model.player.LocationVariables;
import versions.ver637.model.player.Player;
import versions.ver637.model.player.flags.TeleportFlag;

@RequiredArgsConstructor
public abstract class PlayerRoutineTimer extends RSRoutineTimer<Player> {

	protected Player player;

	@Override
	public final void processSequence(Player player) {
		this.player = player;

		process();
	}

	/**
	 * Processes this {@code PlayerTimer} for the given {@code player}.
	 */
	public abstract void process();

	/**
	 * Sends a message to the player.
	 * 
	 * @param string    the string to send
	 * @param arguments the arguments within the string
	 */
	public PlayerRoutineTimer msg(String string, Object... arguments) {
		this.addRoutine(new MessageRoutine(string, arguments));
		return this;
	}

	/**
	 * Forces the player to walk to the given coordinates.
	 * 
	 * @param x the x coordinate
	 * @param y the y coordinate
	 */
	public PlayerRoutineTimer walkTo(int x, int y) {
		this.addRoutine(new WalkToRoutine(x, y));
		return this;
	}

	/**
	 * Waits for the given {@code ticks} to pass before continuing.
	 * 
	 * @param ticks the ticks to wait
	 */
	public PlayerRoutineTimer waitFor(int ticks) {
		this.addRoutine(new WaitForRoutine(ticks));
		return this;
	}

	/**
	 * Teleports the player to the given destination.
	 * 
	 * @param destination the destination to teleport to
	 */
	public PlayerRoutineTimer teleport(RSLocation destination) {
		this.addRoutine(new TeleportRoutine(destination));
		return this;
	}

	/**
	 * Executes the given consumer
	 */
	public PlayerRoutineTimer andThen(Consumer<Player> consumer) {
		this.addRoutine(new AndThenRoutine(consumer));
		return this;
	}

	@RequiredArgsConstructor
	private class WalkToRoutine implements RSTimerRoutine<Player> {
		private final int x, y;

		private RSLocation destination;

		@Override
		public boolean run(Player player) {
			if (destination == null) {
				RSRouteBuilder builder = new RSRouteBuilder();
				builder.startingAt(player.getLocation());
				builder.endingAt(destination = new RSLocation(x, y, player.getLocation().getZ()));
				LocationVariables.resetRoute(player);
				player.getLocationVariables().setRoute(builder.findPath());
			}
			return !player.getLocation().equals(destination);
		}
	}

	private record MessageRoutine(String string, Object... arguments) implements RSTimerRoutine<Player> {
		@Override
		public boolean run(Player player) {
			player.sendMessage(string, arguments);
			return false;
		}
	}

	@AllArgsConstructor
	private class WaitForRoutine implements RSTimerRoutine<Player> {
		private int ticksToWaitFor;

		@Override
		public boolean run(Player player) {
			return --ticksToWaitFor > 0;
		}
	}

	private record TeleportRoutine(RSLocation destination) implements RSTimerRoutine<Player> {
		@Override
		public boolean run(Player player) {
			LocationVariables.resetRoute(player);
			player.setLocation(destination);
			player.getModel().registerFlag(new TeleportFlag());
			return false;
		}
	}

	private record AndThenRoutine(Consumer<Player> consumer) implements RSTimerRoutine<Player> {

		@Override
		public boolean run(Player player) {
			consumer.accept(player);
			return false;
		}
	}
}
