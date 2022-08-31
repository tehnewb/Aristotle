package com.framework.util;

import java.util.StringTokenizer;

public class StringUtil {

	/**
	 * Valid display name characters
	 */
	public static final char VALID_DISPLAY_NAME_CHARS[] = { '_', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '-', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', ' ' };

	/**
	 * Array of vowels
	 */
	private static final String[] VOWELS = { "a", "e", "i", "o", "u", "A", "E", "I", "O", "U" };

	/**
	 * Returns a the given {@code string} with "an" or "a" in front of it if the
	 * given string started with a vowel or not.
	 * 
	 * @param string the string to return a prefix with
	 * @return the prefixed string
	 */
	public static String withPrefix(String string) {
		for (String vowel : VOWELS) {
			if (string.startsWith(vowel)) {
				return "an " + string;
			}
		}
		return "a " + string;
	}

	/**
	 * Returns true if the given string {@code s} is a valid name for runescape.
	 * 
	 * @param s the name
	 * @return true if valid; false otherwise
	 */
	public static boolean isValidName(String s) {
		return s.toLowerCase().replace(" ", "_").matches("^[a-zA-Z0-9_ ]{1,13}$") && s.length() <= 12 && s.length() > 0;
	}

	/**
	 * Joins the given array of {@code strings} and then wraps them by the given
	 * {@code lineWidth}, which is the amount of characters that can be on one line.
	 * If the given {@code wrapWords} flag is true, words will be cut off to
	 * wrapping, otherwise the worth will be kept whole. Returns an array of wrapped
	 * strings, with each string in the array being the newly wrapped line of text.
	 * 
	 * @param lineWidth the max amount of characters on 1 line
	 * @param wrapWords determines the word wrap or not
	 * @param strings   the array of strings to join and wrap
	 * @return the array of wrapped strings.
	 */
	public static String[] wrap(int lineWidth, boolean wrapWords, String... strings) {
		if (wrapWords) {
			StringBuilder builder = new StringBuilder();
			String[] words = String.join(" ", strings).split(" ");
			String currentLine = "";
			for (int i = 0; i < words.length; i++) {
				String w = i < words.length - 1 ? words[i] + " " : words[i];
				builder.append(" " + words[i]);
				if (currentLine.length() + w.length() > lineWidth) {
					builder.append('\n');
					currentLine = "";
				} else
					currentLine += w;
			}
			return builder.toString().trim().split("\n");
		} else {
			String compact = String.join(" ", strings);
			String[] lines = new String[(compact.length() + lineWidth - 1) / lineWidth];
			for (int i = 0, index = 0; i < compact.length(); i += lineWidth)
				lines[index++] = compact.substring(i, Math.min(compact.length(), i + lineWidth));
			return lines;
		}
	}

	/**
	 * Returns the given {@code string} with an uppercase first letter and the rest
	 * of the text being lowercase.
	 * 
	 * @param string the string to make the first letter uppercase
	 * @return the re-formatted string
	 */
	public static String upperFirst(String string) {
		return string.substring(0, 1).toUpperCase() + string.substring(1).toLowerCase();
	}

	/**
	 * Returns the given {@code ipAddress} as an integer.
	 * 
	 * @param ipAddress the ip address
	 * @return the integer calculated value
	 */
	public static int IPAddressToNumber(String ipAddress) {
		StringTokenizer st = new StringTokenizer(ipAddress, ".");
		int[] ip = new int[4];
		int i = 0;
		while (st.hasMoreTokens())
			ip[i++] = Integer.parseInt(st.nextToken());
		return ((ip[0] << 24) | (ip[1] << 16) | (ip[2] << 8) | (ip[3]));
	}

}
