package com.framework.mechanics.queue;

public interface RSScript<O> {

	public abstract void process(O owner);

	public abstract RSQueueType type();

}
