package com.framework.map;

import java.util.stream.Stream;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Represents an 8-directional movement
 * 
 * @author Albert Beaupre
 */
@RequiredArgsConstructor
@Getter
public enum RSDirection {
	North(0, 1),
	South(0, -1),
	East(1, 0),
	West(-1, 0),
	NorthEast(1, 1),
	NorthWest(-1, 1),
	SouthEast(1, -1),
	SouthWest(-1, -1),
	None(0, 0);

	private final int deltaX;
	private final int deltaY;

	/**
	 * Returns the {@code RSDirection} with the exact given deltas.
	 * 
	 * @param deltaX the x delta
	 * @param deltaY the y delta
	 * @return the direction
	 */
	public static RSDirection of(int deltaX, int deltaY) {
		return Stream.of(values()).filter(d -> d.deltaX == deltaX && d.deltaY == deltaY).findFirst().orElse(None);
	}

}
