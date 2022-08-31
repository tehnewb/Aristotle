package com.framework.resource;

import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.function.Consumer;

import lombok.NonNull;

/**
 * The {@code RSResourceWorker} is intended for resource saving or loading and
 * heavy usage and calculations.
 * 
 * @author Albert Beaupre
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public final class RSResourceWorker implements Runnable {

	// Holds all queued tasks waiting to be called
	private final ConcurrentLinkedDeque<RSResourceCallback> ResourceQueue = new ConcurrentLinkedDeque<>();

	@Override
	public void run() {
		try {
			while (!ResourceQueue.isEmpty()) {
				RSResourceCallback next = ResourceQueue.poll();
				Object resource = next.resource().load();
				next.resource().finish(resource);
				next.callback().accept(resource);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Queues the given {@code resource} to be executed by the worker. The
	 * {@link RSResource#load()} method will be called to retrieve the object and
	 * once finished, the {@link RSResource#finish(Object)} method will be called
	 * passing the loaded object.
	 * 
	 * @param resource the resource to queue
	 */
	public final void queue(@NonNull RSResource resource) {
		ResourceQueue.add(new RSResourceCallback(resource, r -> {}));
	}

	/**
	 * Queues the given {@code resource} to be executed by the worker. The
	 * {@link RSResource#load()} method will be called to retrieve the object and
	 * once finished, the {@link RSResource#finish(Object)} method will be called
	 * passing the loaded object and the callback consumer will be accepted also
	 * passing the loaded object to the consumer.
	 * 
	 * @param resource the resource to queue
	 * @param callback the callback called once finished loading the resource
	 */
	public final <T> void queue(@NonNull RSResource<T> resource, Consumer<T> callback) {
		ResourceQueue.add(new RSResourceCallback<T>(resource, callback));
	}

}
