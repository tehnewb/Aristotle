package com.framework.network;

import java.nio.ByteBuffer;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.AttributeKey;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RSNetworkHandler extends ChannelInboundHandlerAdapter {

	public static final AttributeKey<RSNetworkSession> SessionKey = AttributeKey.valueOf("Session");
	private final RSSessionCoder defaultCoder;

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		RSNetworkSession session = new RSNetworkSession(ctx.channel(), defaultCoder);
		ctx.channel().attr(SessionKey).set(session);
		ctx.channel().closeFuture().addListener(new RSSessionDisconnectListener(session));
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object message) throws Exception {
		if (message instanceof ByteBuffer) {
			ByteBuffer buffer = ByteBuffer.class.cast(message);
			ctx.writeAndFlush(Unpooled.wrappedBuffer(buffer));
		} else if (byte[].class.isAssignableFrom(message.getClass())) {
			ctx.write(Unpooled.wrappedBuffer(byte[].class.cast(message)));
		} else {
			ctx.writeAndFlush(message);
		}
	}
}
