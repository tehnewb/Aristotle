package com.framework.mechanics.timer;

/**
 * A {@code RSScriptRoutine} is a "blocking" routine for a script.
 * 
 * @author Albert Beaupre
 */
public interface RSTimerRoutine<O> {

	/**
	 * Returns true if this {@code RSScriptRoutine} can continue running. Returns
	 * false otherwise.
	 * 
	 * @return true if continue; false otherwise
	 */
	public boolean run(O owner);

}
