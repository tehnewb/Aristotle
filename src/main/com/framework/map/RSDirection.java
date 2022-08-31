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

	/**
	 * Returns the {@code RSDirection} with the exact given deltas.
	 * 
	 * @param deltaX the x delta
	 * @param deltaY the y delta
	 * @return the direction
	 */
	public static RSDirection combined(RSDirection first, RSDirection second) {
		int deltaX = first.deltaX + second.deltaX;
		int deltaY = first.deltaY + second.deltaY;
		return Stream.of(values()).filter(d -> d.deltaX == deltaX && d.deltaY == deltaY).findFirst().orElse(None);
	}

	/**
	 * Returns the direction that the given {@code to} location is from the given
	 * {@code from} location.
	 * 
	 * @param from the location coming from
	 * @param to   the location going to
	 * @return the direction
	 */
	public static RSDirection getDirectionGoingTowards(RSLocation from, RSLocation to) {
		int deltaX = to.getX() - from.getX();
		int deltaY = to.getY() - from.getY();

		if (deltaX < 0) {
			deltaX = Math.max(-1, deltaX);
		} else if (deltaX > 0) {
			deltaX = Math.min(1, deltaX);
		}
		if (deltaY < 0) {
			deltaY = Math.max(-1, deltaY);
		} else if (deltaY > 0) {
			deltaY = Math.min(1, deltaY);
		}
		return RSDirection.of(deltaX, deltaY);
	}

}
