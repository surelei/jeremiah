package com.jeremiahxu.nio.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class SimpleClient {
	protected Logger log = LoggerFactory.getLogger(this.getClass());
	private ClientService request;
	private String host;
	private int port;

	public SimpleClient() {
	}

	public SimpleClient(String host, int port, ClientService request) {
		this.host = host;
		this.port = port;
		this.request = request;
	}

	public void send() {
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap();
			b.group(workerGroup);
			b.channel(NioSocketChannel.class);
			b.option(ChannelOption.SO_KEEPALIVE, true);
			b.handler(new ChannelInitializer<SocketChannel>() {
				@Override
				public void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast(new SimpleClientHandler(request));
				}
			});
			try {
				ChannelFuture f = b.connect(host, port).sync();
				log.info("simple client connected.");
				f.channel().closeFuture().sync();
			} catch (InterruptedException e) {
				log.error("SimpleClient.send():", e);
			}
		} finally {
			workerGroup.shutdownGracefully();
			log.info("simple client closed.");
		}
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public ClientService getRequest() {
		return request;
	}

	public void setRequest(ClientService request) {
		this.request = request;
	}

}
