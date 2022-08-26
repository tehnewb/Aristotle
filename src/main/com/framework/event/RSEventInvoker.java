package com.framework.event;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * The RSEventInvoker is a wrapper class and invoker for the assigned method to
 * this record.
 * 
 * @author Albert Beaupre
 */
public record RSEventInvoker(Class<?> toInvokeFor, Object object, Method method) {

	/**
	 * Invokes the method of this {@code RSEventInvoker} using the given
	 * {@code event} as the argument for the method.
	 * 
	 * @param event the event
	 */
	public final void invoke(Object event) {
		try {
			method.invoke(object, event);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}

}
