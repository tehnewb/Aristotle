package com.framework.tick;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Predicate;

import com.framework.RSFramework;
import com.framework.entity.RSEntity;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@Accessors(fluent = true)
public abstract class RSTick extends RSEntity {

	@Getter
	private long delay;

	@Getter
	private long occurrences;

	private Predicate<RSTick> stopPredicate = p -> false;
	private Instant gap;

	/**
	 * Starts this tick by adding it to the RSTickWorker.
	 */
	public final void start() {
		if (stopped()) {
			throw new IllegalStateException("RSTick has already been stopped and cannot be started again. Use #restart");
		}
		this.gap = Instant.now().plusMillis(delay);

		RSFramework.addTick(this);
	}

	/**
	 * Restarts this {@code RSTick} by resetting the gap and stop predicate, and
	 * also re-adding it to the RSTickWorker.
	 */
	public final void restart() {
		this.occurrences = 0;
		this.stopPredicate = p -> false;
		this.gap = Instant.now().plusMillis(delay);
	}

	long start;

	/**
	 * Handles the updating process for this tick by calculating the gap between
	 * each tick based on the delay set.
	 */
	public final void update() {
		if (stopped())
			return; // do not continue any further if this tick is stopped

		if (gap == null)
			gap = Instant.now().plusMillis(delay);

		Duration difference = Duration.between(gap, Instant.now());
		if (difference.isPositive()) {
			occurrences++;
			tick();
			gap = gap.plusMillis(delay);
			start = System.currentTimeMillis();
		}
	}

	/**
	 * This method is called after the delay of the tick has been passed.
	 */
	protected abstract void tick();

	/**
	 * Sets the delay of this {@code Tick} to the given {@code delay} in
	 * milliseconds. This method is equivalent to delay(long,
	 * TimeUnit.MILLISECONDS);
	 * 
	 * @param delay the delay between each tick
	 * @return this instance for chaining
	 */
	public RSTick delay(long delay) {
		return this.delay(delay, TimeUnit.MILLISECONDS);
	}

	/**
	 * Sets the delay of this {@code Tick} to the given {@code delay} and converts
	 * the value to milliseconds.
	 * 
	 * @param delay the delay between each tick
	 * @return this instance for chaining
	 */
	public RSTick delay(long delay, TimeUnit unit) {
		this.delay = unit.toMillis(delay);
		return this;
	}

	/**
	 * Stops this tick from executing the {@link #tick()} method again.
	 */
	public void stop() {
		this.stopPredicate = p -> true;
	}

	/**
	 * The predicate to determine when to stop this tick from executing the
	 * {@link #tick()} method.
	 * 
	 * @param predicate the stop predicate
	 * @return this instance for chaining
	 */
	public RSTick stopIf(@NonNull Predicate<RSTick> predicate) {
		this.stopPredicate = predicate;
		return this;
	}

	/**
	 * Returns true if this {@code RSTick} is stopped.
	 * 
	 * @return true if stopped; false otherwise
	 */
	public boolean stopped() {
		return this.stopPredicate.test(this);
	}

	/**
	 * Determines that the tick assigned this Predicate will stop once it occurs
	 * this many times.
	 * 
	 * @param occurrences the amount of times the tick occurs before it stops
	 * @return the predicate
	 */
	public static Predicate<RSTick> occurrs(long occurrences) {
		return p -> p.occurrences >= occurrences;
	}

	/**
	 * Constructs a new {@code Tick} the given {@code consumer} to use as the action
	 * for the {@link #tick()} method.
	 * 
	 * @param consumer the action for the tick
	 * @return a new instance of a tick with the consumer as the action
	 */
	public static RSTick of(@NonNull Consumer<RSTick> consumer) {
		return new RSTickAdapter(consumer);
	}

	@RequiredArgsConstructor
	private static class RSTickAdapter extends RSTick {

		@NonNull
		private final Consumer<RSTick> consumer;

		@Override
		protected void tick() {
			consumer.accept(this);
		}

	}
}
