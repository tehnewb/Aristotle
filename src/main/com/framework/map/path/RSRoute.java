package com.framework.map.path;

import java.util.Arrays;
import java.util.function.Consumer;

import com.framework.map.RSCollision;
import com.framework.map.RSDirection;
import com.framework.map.RSLocation;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;
import versions.ver637.map.WorldMap;

/**
 * The {@code RSRoute} contains a deque of {@code RSRouteStep} and is generally
 * used for player or npc traversal.
 * 
 * @author Albert Beaupre
 */
@Accessors(fluent = true, chain = true)
public class RSRoute {

	@Getter
	private RSLocation[] checkpoints;
	private RSLocation nextStep;
	private int checkpointIndex;

	public RSRoute(RSLocation... checkpoints) {
		if (checkpoints.length > 0) {
			this.checkpoints = checkpoints;
			this.nextStep = checkpoints[0];
		} else {
			this.checkpoints = null;
		}
	}

	@Getter
	@Setter
	private Object target;

	@Getter
	@Setter
	@NonNull
	private Consumer<RSRoute> reachRequest = r -> {};

	@Getter
	@Setter
	private boolean failed;

	/**
	 * Returns the next {@code RSRouteStep} of this {@code RSRoute} and removes it.
	 * 
	 * @return the next step
	 */
	public RSRouteStep next() {
		RSLocation checkpoint = this.checkpoints[this.checkpointIndex];
		RSLocation nextCheckpoint = this.checkpoints[this.checkpointIndex + 1];
		RSDirection directionTo = RSDirection.getDirectionGoingTowards(checkpoint, nextCheckpoint);
		/**
		 * The collision can change at any time during the route, such as a door
		 * closing. That's why this is here
		 */
		if (RSCollision.canTraverse(WorldMap.getMap(), nextStep, directionTo)) {

			this.nextStep = this.nextStep.neighbor(directionTo);

			if (nextStep.equals(nextCheckpoint))
				this.checkpointIndex++;
			if (this.checkpointIndex + 1 >= this.checkpoints.length)
				this.checkpoints = null;
		} else {
			this.checkpoints = null;
			return new RSRouteStep(RSDirection.None, nextStep);
		}
		return new RSRouteStep(directionTo, nextStep);
	}

	/**
	 * Returns the current {@code RSRouteStep} without removing it.
	 * 
	 * @return the current step
	 */
	public RSRouteStep get() {
		if (isEmpty())
			return new RSRouteStep(RSDirection.None, nextStep);
		RSLocation checkpoint = this.checkpoints[this.checkpointIndex];
		RSLocation nextCheckpoint = this.checkpoints[this.checkpointIndex + 1];
		RSDirection directionTo = RSDirection.getDirectionGoingTowards(checkpoint, nextCheckpoint);
		return new RSRouteStep(directionTo, nextStep.neighbor(directionTo));
	}

	/**
	 * Returns true if the {@code RSRoute} has reached its destination.
	 * 
	 * @return true if reached destination; false otherwise
	 */
	public boolean reachedLastCheckpoint() {
		return this.checkpoints != null && (this.checkpoints.length == 2 || this.checkpointIndex >= this.checkpoints.length - 1);
	}

	/**
	 * Clears this {@code RSRoute} of all steps.
	 */
	public void clear() {
		this.checkpoints = null;
		this.reachRequest = r -> {};
	}

	public boolean hasNext() {
		return !isEmpty() && this.checkpointIndex < this.checkpoints.length;
	}

	/**
	 * Returns true if there are no more steps within this {@code RSRoute}.
	 * 
	 * @return true if empty; otherwise false
	 */
	public boolean isEmpty() {
		return this.checkpoints == null;
	}

	@Override
	public String toString() {
		return Arrays.toString(checkpoints);
	}

}
