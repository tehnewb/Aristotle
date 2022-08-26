package com.framework.entity;

/**
 * 
 * @author Albert Beaupre
 */
public class RSEntity {

	private int index;

	/**
	 * Returns the index of this {@code RSEntity}, which is set by the
	 * {@code EntityList}.
	 * 
	 * @return the index
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * Sets the index of this {@code RSEntity}. This method should only be called by
	 * the {@code EntityList} class.
	 * 
	 * @param index the index to set to the RSEntity
	 */
	public void setIndex(int index) {
		this.index = index;
	}
}
