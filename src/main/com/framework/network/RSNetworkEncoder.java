package com.framework.network;

import com.framework.io.RSStream;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class RSNetworkEncoder extends MessageToByteEncoder<RSStream> {

	@Override
	protected void encode(ChannelHandlerContext ctx, RSStream msg, ByteBuf out) throws Exception {
		RSNetworkSession session = ctx.channel().attr(RSNetworkHandler.SessionKey).get();
		RSStream encoded = session.getCoder().encode(session, msg);
		out.writeBytes(encoded.buffer(), 0, encoded.position());
	}

}
