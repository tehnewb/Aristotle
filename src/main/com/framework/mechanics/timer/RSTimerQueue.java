package com.framework.mechanics.timer;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * <p>
 * The timers’ system is used to execute periodic scripts for players and NPCs.
 * Each timer can only execute at a defined interval. The timer will start at
 * the given interval, and count down until it reaches zero, upon which the
 * script itself is invoked. Timers execute before queues for NPCs, and after
 * queues for players. Timers do not execute on the tick on which they were
 * queued. This is important to keep in mind, as this is what allows
 * prayer-flicking to not drain prayer points in OldSchool RuneScape. New timers
 * are presumably queued into a separate collection. During the processing part
 * of timers, existing timers are ticked down and processed if needed. After
 * that, the new timers’ collection is iterated, and the respective timers are
 * moved onto the existing timers’ collection. <em>The new timers do not get
 * executed this tick.</em>
 * </p>
 * 
 * @author Albert Beaupre
 *
 * @param <O> The type of owner
 */
@RequiredArgsConstructor
public abstract class RSTimerQueue<O> {

	private CopyOnWriteArrayList<RSTimer<O>> waitingTimers = new CopyOnWriteArrayList<>();
	private ArrayList<RSTimer<O>> currentTimers = new ArrayList<>();

	protected final O owner;

	/**
	 * Processes all current timers in this {@code RSTimerQueue}, and adds any
	 * waiting timers to the current timers for processing on the next tick.
	 */
	public void process() {
		if (currentTimers.isEmpty() && waitingTimers.isEmpty())
			return;

		currentTimers.removeIf(timer -> {
			timer.tick();
			if (timer.type() == RSTimerType.Normal && hasNonModalInterface())
				return false; //skip normals if non modal is open

			return !timer.update(owner);
		});

		currentTimers.addAll(waitingTimers);
		waitingTimers.clear();
	}

	/**
	 * Adds the given {@code timer} to the waiting timers of this
	 * {@code RSTimerQueue}. After the next tick, then the waiting timers will be
	 * added to the current timers for processing.
	 * 
	 * @param timer the timer to add
	 */
	public void addTimer(@NonNull RSTimer<O> timer) {
		waitingTimers.add(timer);
	}

	/**
	 * Returns true if the owner of this {@code RSTimerQueue} has a non modal
	 * interface open.
	 * 
	 * @return true if non modal open; false otherwise
	 */
	public abstract boolean hasNonModalInterface();

}
