package com.framework.event;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

/**
 * The RSEventInvoker is a wrapper class and invoker for the assigned method to
 * this record.
 * 
 * @author Albert Beaupre
 */
@RequiredArgsConstructor
@Getter
@Accessors(fluent = true)
public class RSEventInvoker {

	private final Class<?> toInvokeFor;
	private final Object object;
	private final Method method;

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
