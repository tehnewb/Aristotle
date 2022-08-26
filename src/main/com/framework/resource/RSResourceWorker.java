package com.framework.resource;

import java.util.concurrent.ConcurrentLinkedDeque;

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
	private final ConcurrentLinkedDeque<RSResource> ResourceQueue = new ConcurrentLinkedDeque<>();

	@Override
	public void run() {
		while (!ResourceQueue.isEmpty()) {
			try {
				RSResource next = ResourceQueue.poll();
				Object resource = next.load();
				next.finish(resource);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Queues the given {@code callable} to be executed by the worker. The
	 * {@link RSResource#call()} method will be called to retrieve the object and
	 * once finished, the {@link RSResource#finish(Object)} method will be called
	 * passing the retrieved object.
	 * 
	 * @param callable the callable to queue
	 */
	public final void queue(@NonNull RSResource callable) {
		ResourceQueue.add(callable);
	}

}
