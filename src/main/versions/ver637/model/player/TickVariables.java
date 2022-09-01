package versions.ver637.model.player;

import com.framework.entity.RSEntityList;
import com.framework.tick.RSGameTick;

import lombok.NonNull;

public class TickVariables {

	private transient RSEntityList<RSGameTick> ticks = new RSEntityList<>(100);

	/**
	 * Processes the ticks for the given {@code player}.
	 * 
	 * @param player the player processing the ticks for
	 */
	public static void processTicks(Player player) {
		TickVariables variables = player.getAccount().getTickVariables();

		for (RSGameTick tick : variables.ticks) {
			if (tick == null)
				continue;

			tick.update();

			if (tick.stopped())
				variables.removeTick(tick.getName());
		}
	}

	/**
	 * Adds the given {@code tick} to this {@code TickVariables} class. The tick
	 * will be processed every 600ms.
	 * 
	 * @param tick the tick to add
	 */
	public void addTick(@NonNull RSGameTick tick) {
		ticks.add(tick);
	}

	/**
	 * Removes the tick with the given {@code name}.
	 * 
	 * @param name the name of the tick to remove
	 */
	public void removeTick(String name) {
		for (int i = ticks.head(); i <= ticks.tail(); i++) {
			RSGameTick tick = ticks.get(i);
			if (tick == null)
				continue;

			if (tick.getName().equals(name))
				ticks.remove(i);
		}
	}

	/**
	 * Clears all ticks from this {@code TickVariables} class.
	 */
	public void clear() {
		ticks.clear();
	}

}
