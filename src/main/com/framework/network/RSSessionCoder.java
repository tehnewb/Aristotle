package com.framework.network;

import java.util.List;

import com.framework.io.RSStream;

/**
 * The {@code RSSessionCoder} handles the encoding and decoding for an
 * {@code RSNetworkSession}.
 * 
 * @author Albert Beaupre
 */
public interface RSSessionCoder {

	/**
	 * Takes the given {@code in} stream and returns the encoded stream. If no
	 * encoding is done, just return the {@code in} stream.
	 * 
	 * @param session the session this is encoding for
	 * @param in      the stream to encode
	 * @return the encoded stream
	 */
	RSStream encode(RSNetworkSession session, RSStream in);

	/**
	 * Decodes the given {@code in} stream for the given {@code session}. Add all
	 * outgoing messages/streams/objects to the given {@code out} list.
	 * 
	 * @param session the session decoding for
	 * @param in      the stream decoding
	 * @param out     the outgoing messages
	 */
	void decode(RSNetworkSession session, RSStream in, List<Object> out);

}
