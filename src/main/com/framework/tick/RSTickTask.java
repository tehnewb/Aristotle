package com.framework.tick;

import com.framework.entity.RSEntityList;

import lombok.NonNull;

/**
 * The {@code RSTickWorker} handles the updating of all RSTicks added to it.
 * 
 * @author Albert Beaupre
 */
public class RSTickTask implements Runnable {

	private RSEntityList<RSTick> ticks = new RSEntityList<>(100, true);

	@Override
	public final void run() {
		try {
			for (int tickIndex = ticks.head(); tickIndex <= ticks.tail(); tickIndex++) {
				RSTick tick = ticks.get(tickIndex);

				if (tick == null)
					continue;

				tick.update();

				if (tick.stopped()) {
					this.ticks.remove(tickIndex);
				}
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	/**
	 * Adds the given {@code tick} to the array of ticks for updating.
	 * 
	 * @param tick the tick to add for updating
	 */
	public final void addTick(@NonNull RSTick tick) {
		ticks.add(tick);
	}

}
