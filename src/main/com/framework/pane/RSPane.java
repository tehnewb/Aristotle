package com.framework.pane;

/**
 * The {@code RSPane} is a type of window to display the entire game frame and
 * will hold varps and varcs.
 * 
 * @author Albert Beaupre
 */
public class RSPane extends RSWindow {

	private final int[] varp = new int[3000];
	private final int[] varc = new int[2000];
	private final String[] cs2Strings = new String[1000];

	public RSPane(int interfaceID) {
		super(interfaceID, -1);
	}

	public String getCS2String(int index) {
		return cs2Strings[index];
	}

	public void setCS2String(int index, String string) {
		this.cs2Strings[index] = string;
	}

	public int getVarp(int index) {
		return varp[index];
	}

	public void setVarp(int index, int value) {
		this.varp[index] = value;
	}

	public int getVarc(int index) {
		return varc[index];
	}

	public void setVarc(int index, int value) {
		this.varc[index] = value;
	}

}
