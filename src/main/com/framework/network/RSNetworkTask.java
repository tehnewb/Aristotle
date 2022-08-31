package com.framework.network;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RSNetworkTask implements Runnable {

	@NonNull
	private final RSSessionCoder defaultCoder;

	@Getter
	private final int port;

	@Override
	public void run() {
		try {
			EventLoopGroup bossGroup = new NioEventLoopGroup();
			EventLoopGroup workerGroup = new NioEventLoopGroup();
			ServerBootstrap bootstrap = new ServerBootstrap();

			bootstrap.group(bossGroup, workerGroup);
			bootstrap.channel(NioServerSocketChannel.class);
			bootstrap.childOption(ChannelOption.TCP_NODELAY, true);
			bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
			bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
				@Override
				public void initChannel(SocketChannel channel) throws Exception {
					channel.pipeline().addLast("decoder", new RSNetworkDecoder());
					channel.pipeline().addLast("encoder", new RSNetworkEncoder());
					channel.pipeline().addLast("handler", new RSNetworkHandler(defaultCoder));
				}
			});
			bootstrap.bind(port).sync();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
}
