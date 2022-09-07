package com.framework.mechanics.queue;

import java.util.ArrayDeque;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class RSScriptQueue<O> {

	private ArrayDeque<RSScript<O>> queue = new ArrayDeque<>();

	@Getter
	protected final O owner;
	private boolean interrupted;

	/**
	 * Processes all {@code RSScript}s within this {@code RSScriptProcessor}.
	 */
	public void process() {
		if (queue.isEmpty())
			return;

		if (queue.stream().anyMatch(r -> r.type() == RSQueueType.Strong)) {
			closeNonModalInterfaces();

			queue.removeIf(r -> r.type() == RSQueueType.Weak);
		}
		
		if (interrupted) {
			queue.removeIf(r -> r.type() == RSQueueType.Weak);
			interrupted = false;
		}
		
		while (!queue.isEmpty()) {
			boolean removed = queue.removeIf(script -> {
				if (script.type() == RSQueueType.Strong || script.type() == RSQueueType.Soft)
					closeNonModalInterfaces();

				if (canProcess(script)) {
					script.process(owner);
					return true;
				}
				return false;
			});

			if (!removed)
				break;
		}
	}

	/**
	 * Queues the given {@code script} for processing.
	 * 
	 * @param script the script to queue
	 */
	public void queue(@NonNull RSScript<O> script) {
		queue.add(script);
	}

	public void interrupt() {
		this.interrupted = true;
	}

	/**
	 * Returns true if the given {@code script} can be processed.
	 * 
	 * @param script the script to check
	 * @return true if can process; false otherwise
	 */
	private boolean canProcess(RSScript<O> script) {
		return (script.type() == RSQueueType.Soft || !this.isDelayed()) && !hasNonModalInterfaces();
	}

	public boolean isDelayed() {
		return false;
	}

	/**
	 * Returns true if the owner of this {@code RSScriptProcessor} has any non modal
	 * interfaces open.
	 * 
	 * @return true if non modal open; false otherwise
	 */
	public abstract boolean hasNonModalInterfaces();

	/**
	 * Closes this
	 */
	public abstract void closeNonModalInterfaces();
}
