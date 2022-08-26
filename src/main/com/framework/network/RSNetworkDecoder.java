package com.framework.network;

import java.util.List;

import com.framework.io.RSStream;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class RSNetworkDecoder extends ByteToMessageDecoder {

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf buffer, List<Object> out) throws Exception {
		RSNetworkSession session = ctx.channel().attr(RSNetworkHandler.SessionKey).get();
		byte[] arr = new byte[buffer.readableBytes()];
		buffer.readBytes(arr);
		RSStream in = new RSStream(arr);
		session.getCoder().decode(session, in, out);
	}

}
