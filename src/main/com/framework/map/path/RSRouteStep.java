package com.framework.map.path;

import com.framework.map.RSDirection;
import com.framework.map.RSLocation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

/**
 * The {@code RSRouteStep} contains an {@code RSDirection} and
 * {@code RSLocation}. The direction is determined from where the previous step
 * was, and the location is the exact coordinate of where this step is at.
 * 
 * @author Albert Beaupre
 */
@RequiredArgsConstructor
@Getter
@Accessors(fluent = true)
public class RSRouteStep {

	private final RSDirection direction;
	private final RSLocation location;

	@Override
	public String toString() {
		return "RSRouteStep[direction=%s, location=%s]".formatted(direction, location);
	}

}
