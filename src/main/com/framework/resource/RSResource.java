package com.framework.resource;

import java.util.function.Consumer;

import com.framework.RSFramework;

/**
 * A {@code RSResource} is meant to retrieve an object requiring heavy usage
 * that might be done off the main thread. This may be needed for saving or
 * loading files, or large calculations.
 *
 * @param <R> the type of object to return
 */
public interface RSResource<R> {

	/**
	 * This method is used for loading the resource and is blocking until it
	 * finishes.
	 * 
	 * @return the loaded resource
	 * @throws Exception if an error occurs during load
	 */
	R load() throws Exception;

	/**
	 * This is a callback method for when the {@link #call()} method is finished and
	 * the given {@code resource} parameter will be the object returned from the
	 * {@link #call()} method.
	 * 
	 * @param resource the resource returned from the call method
	 */
	void finish(R resource);

	/**
	 * Queues this {@code RSResourceCallable} to the worker in the
	 * {@code RSFramework}.
	 */
	default void queue() {
		RSFramework.queueResource(this);
	}

	/**
	 * Queues this {@code RSResourceCallable} to the worker in the
	 * {@code RSFramework}.
	 * 
	 * @param callback the callback executed once the resource has been processed
	 */
	default void queue(Consumer<R> callback) {
		RSFramework.queueResource(this, callback);
	}
}
