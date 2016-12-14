package com.jeremiahxu.nio.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class SimpleServer {
	protected Logger log = LoggerFactory.getLogger(this.getClass());
	private ServerService response;
	private int port;// server port

	public SimpleServer() {
	}

	public SimpleServer(int port, ServerService response) {
		this.port = port;
		this.response = response;
	}

	public void run() {
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
					.childHandler(new ChannelInitializer<SocketChannel>() {
						@Override
						public void initChannel(SocketChannel ch) throws Exception {
							ch.pipeline().addLast(new SimpleServerHandler(response));
						}
					}).option(ChannelOption.SO_BACKLOG, 128)
					.childOption(ChannelOption.SO_KEEPALIVE, true);
			// Bind and start to accept incoming connections.
			try {
				ChannelFuture f = b.bind(port).sync();
				log.info("simple server started.");
				f.channel().closeFuture().sync();
			} catch (InterruptedException e) {
				log.error("SimpleServer.run():", e);
			}
		} finally {
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public ServerService getResponse() {
		return response;
	}

	public void setResponse(ServerService response) {
		this.response = response;
	}

}
