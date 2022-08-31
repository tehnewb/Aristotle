package com.framework.event;

import java.util.ArrayList;
import java.util.HashMap;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

/**
 * The {@code RSEventBus} holds a map of RSEventInvoker that correspond to a
 * specific type. If that specific type is posted to this {@code RSEventBus},
 * then it will invoke all RSEventInvokers assigned to the given type.
 * 
 * @author Albert Beaupre
 */
@Slf4j
public class RSEventBus {

	// Map holding all invokers corresponding to a specific class
	private final HashMap<Class<?>, ArrayList<RSEventInvoker>> InvokerMap = new HashMap<>();

	/**
	 * Adds the given {@code invoker} to this {@code RSEventBus}. This cannot be a
	 * null value, otherwise NullPointerException is thrown.
	 * 
	 * @param invoker the invoker to add
	 * @throws NullPointerException if invoker is null
	 */
	public void addInvoker(@NonNull RSEventInvoker invoker) {
		ArrayList<RSEventInvoker> currentInvokers = getCurrentInvokers(invoker.object().getClass());

		currentInvokers.add(invoker);
		InvokerMap.put(invoker.toInvokeFor(), currentInvokers);
		log.debug("Added invoker from {} to invoke for {}", invoker.object().getClass().getSimpleName(), invoker.toInvokeFor().getSimpleName());
	}

	/**
	 * Invokes the invoker that has a parenting type matching the given
	 * {@code event}.
	 * 
	 * @param event the event to pass as arguments to the invoker
	 */
	public void invoke(@NonNull Object event) {
		ArrayList<RSEventInvoker> currentInvokers = getCurrentInvokers(event.getClass());
		if (currentInvokers.isEmpty()) {
			log.warn("No invokers for event {}", event.getClass().getSimpleName());
			return;
		}
		for (int i = 0; i < currentInvokers.size(); i++) {
			RSEventInvoker invoker = currentInvokers.get(i);
			invoker.invoke(event);
		}
	}

	/**
	 * Returns an ArrayList of the current invokers with the same type as the given
	 * {@code clazz}. If no invokers are assigned to the type, then an empty list is
	 * returned.
	 * 
	 * @param clazz the clazz with the invokers
	 * @return the invokers corresponding to the given clazz
	 */
	private ArrayList<RSEventInvoker> getCurrentInvokers(Class<?> clazz) {
		return InvokerMap.getOrDefault(clazz, new ArrayList<RSEventInvoker>());
	}
}
