package com.framework.network;

import java.util.concurrent.ConcurrentLinkedQueue;

import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * The {@code RSNetworkSession} represents a session created and handled once a
 * connection has been made between the server and the client. This session is
 * meant to be the same object as long as the connection is kept. Outgoing and
 * incoming RSStream messages are handled by the coder set to this session.
 * 
 * @author Albert Beaupre
 */
@RequiredArgsConstructor
public class RSNetworkSession {

	@Getter
	@NonNull
	private final Channel channel;

	@Getter
	@Setter
	@NonNull
	private RSSessionCoder coder;

	@Getter
	@Setter
	@NonNull
	private RSConnectionListener connectionListener = RSConnectionListener.DefaultListener;

	private final ConcurrentLinkedQueue<Object> queued = new ConcurrentLinkedQueue<>();

	/**
	 * Writes the given {@code object} to the channel of this
	 * {@code RSNetworkSession}.
	 * 
	 * @param object the object to write
	 */
	public void write(@NonNull Object object) {
		queued.add(object);
	}

	/**
	 * Finishes the write procedure of all messages queued to this
	 * {@code RSNetworkSession} and finally flushes the channel them after.
	 */
	public void flushMessages() {
		if (queued.isEmpty())
			return;

		while (!queued.isEmpty()) {
			Object object = queued.poll();
			channel.write(object);
		}
		channel.flush();
	}

	/**
	 * Sets the attribute to the channel of this {@code RSNetworkSession}. The
	 * object set <b>can be null</b>.
	 * 
	 * @param <T>    the type of attribute object
	 * @param key    the key name of the attribute
	 * @param object the object to set to the attribute
	 */
	public <T> void set(@NonNull String key, T object) {
		channel.attr(AttributeKey.valueOf(key)).set(object);
	}

	/**
	 * Returns the object of the attribute set with the corresponding {@code key}.
	 * This value can possibly return null.
	 * 
	 * @param <T>   the type of object
	 * @param key   the key name of the attribute
	 * @param clazz the cast type of the attribute
	 * @return the object set to the attribute, possibly null
	 */
	public <T> T get(@NonNull String key, @NonNull Class<T> clazz) {
		return clazz.cast(channel.attr(AttributeKey.valueOf(key)).get());
	}

}
