package com.framework.mechanics.timer;

import lombok.RequiredArgsConstructor;

/**
 * An {@code RSTimer} will iterate after the amount of ticks passed that have
 * been set to this timer. This will occur for as many times this timer has been
 * set to iterate for.
 * 
 * @author Albert Beaupre
 *
 * @param <O> The type of owner
 */
@RequiredArgsConstructor
public abstract class RSTimer<O> {

	private final int ticks;
	private final long iteratesFor;
	protected int tickCount;
	protected long occurrences;

	/**
	 * Updates this {@code RSTimer} by decreasing the ticks and calling the
	 * {@link #process(Object)} method. This method returns true if this
	 * {@code RSTimer} can update.
	 * 
	 * @param owner the owner of the timer
	 * @return true if the timer can update; false otherwise
	 */
	protected boolean update(O owner) {
		if (tickCount >= ticks) {
			tickCount = 0;
			occurrences++;
			process(owner);
		}
		return occurrences < iteratesFor;
	}

	/**
	 * Increases the tick count of this {@code RSTimer} without processing it.
	 */
	protected void tick() {
		this.tickCount++;
	}

	/**
	 * Stops this {@code RSTimer} from running.
	 */
	public void stop() {
		this.occurrences = Integer.MAX_VALUE;
	}

	/**
	 * Processes this {@code RSTimer} for the given {@code owner}.
	 * 
	 * @param owner the owner of the script
	 */
	public abstract void process(O owner);

	/**
	 * The {@code RSTimerType} for this {@code RSTimer}.
	 * 
	 * @return the timer type
	 */
	public abstract RSTimerType type();

}
