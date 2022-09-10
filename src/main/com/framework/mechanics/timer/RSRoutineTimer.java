package com.framework.mechanics.timer;

import java.util.ArrayDeque;

import lombok.NonNull;

/**
 * A
 * 
 * @author Albert Beaupre
 * @param <O> The type of owner
 */
public abstract class RSRoutineTimer<O> extends RSTimer<O> {

	private ArrayDeque<RSTimerRoutine<O>> routines = new ArrayDeque<>();
	private boolean creating = true;

	/**
	 * Constructs a new {@code RSRoutineTimer} with a tick delay of 1 and will
	 * iterate for {@link Integer#MAX_VALUE}.
	 */
	public RSRoutineTimer() {
		super(1, Long.MAX_VALUE);
	}

	@Override
	public final void process(O owner) {
		if (creating) {
			creating = false;
			processSequence(owner);
		}

		if (routines.isEmpty()) {
			stop();
			return;
		}

		RSTimerRoutine<O> routine = routines.peek();
		if (routine.run(owner))
			return;

		routines.poll();
	}

	/**
	 * Processes this {@code RSRoutineTimer} for the given {@code owner}. This
	 * method is only called once, but any routines created within this method will
	 * be executed throughout their life.
	 * 
	 * @param owner the owner of the timer
	 */
	public abstract void processSequence(O owner);

	/**
	 * Adds the given {@code routine} to this {@code RSRoutineTimer}.
	 * 
	 * @param routine the routine to add
	 */
	public void addRoutine(@NonNull RSTimerRoutine<O> routine) {
		this.routines.add(routine);
	}
}
