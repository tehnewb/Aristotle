package com.framework.util;

import java.util.Random;

public class MathUtil {

	private static final Random random = new Random();

	/**
	 * Clamp Integer values to a given range
	 *
	 * @param value the value to clamp
	 * @param min   the minimum value
	 * @param max   the maximum value
	 * @return the clamped value
	 */
	public static int clamp(int value, int min, int max) {
		return Math.max(min, Math.min(max, value));
	}

	/**
	 * Clamp Float values to a given range
	 *
	 * @param value the value to clamp
	 * @param min   the minimum value
	 * @param max   the maximum value
	 * @return the clamped value
	 */
	public static float clamp(float value, float min, float max) {
		return Math.max(min, Math.min(max, value));
	}

	/**
	 * Clamp Long values to a given range
	 *
	 * @param value the value to clamp
	 * @param min   the minimum value
	 * @param max   the maximum value
	 * @return the clamped value
	 */
	public static long clamp(long value, long min, long max) {
		return Math.max(min, Math.min(max, value));
	}

	/**
	 * Clamp Double values to a given range
	 *
	 * @param value the value to clamp
	 * @param min   the minimum value
	 * @param max   the maximum value
	 * @return the clamped value
	 */
	public static double clamp(double value, double min, double max) {
		return Math.max(min, Math.min(max, value));
	}

	/**
	 * Returns a guassian value based on the given {@code meanModifier} and maximum;
	 * 
	 * @param meanModifier the mean modifier
	 * @param maximum      the maximum
	 * @return the gaussian generated value
	 */
	public static double gaussian(double meanModifier, double maximum) {
		double mean = maximum * meanModifier;
		double deviation = mean * 1.79;
		double value = 0;
		do {
			value = Math.floor(mean + random.nextGaussian() * deviation);
		} while (value < 0 || value > maximum);
		return value;
	}
}
