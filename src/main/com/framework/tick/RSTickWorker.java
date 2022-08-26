package com.framework.tick;

import java.util.Arrays;

import lombok.NonNull;

/**
 * The {@code RSTickWorker} handles the updating of all RSTicks added to it.
 * 
 * @author Albert Beaupre
 */
public final class RSTickWorker implements Runnable {

	private RSTick[] ticks = new RSTick[1000];

	@Override
	public void run() {
		for (int tickIndex = 0; tickIndex < ticks.length; tickIndex++) {
			RSTick tick = this.ticks[tickIndex];

			if (tick == null)
				continue;

			tick.update();

			if (tick.stopped()) {
				this.ticks[tickIndex] = null;
			}
		}
	}

	/**
	 * Adds the given {@code tick} to the array of ticks for updating.
	 * 
	 * @param tick the tick to add for updating
	 */
	public final void addTick(@NonNull RSTick tick) {
		for (int i = 0; i < ticks.length; i++) {
			if (ticks[i] == null) {
				ticks[i] = tick;
				return;
			}
		}
		// should not get to this point until we have reached the array length
		ticks = Arrays.copyOf(ticks, ticks.length + 20);
	}

}
