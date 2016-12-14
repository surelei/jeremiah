package com.jeremiahxu.nio.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

/**
 * 客户端处理类
 * 
 * @author Jeremiah Xu
 *
 */
public class SimpleClientHandler extends ChannelInboundHandlerAdapter {
	protected Logger log = LoggerFactory.getLogger(this.getClass());
	private ClientService request;

	public SimpleClientHandler(ClientService request) {
		this.request = request;
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		ctx.writeAndFlush(Unpooled.copiedBuffer(request.request()));
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		try {
			ByteBuf buffer = (ByteBuf) msg;
			byte[] response = new byte[buffer.readableBytes()];
			buffer.readBytes(response);
			this.request.response(response);
		} catch (Exception e) {
			log.error("SimpleClientHandler.channelRead():", e);
		} finally {
			ReferenceCountUtil.release(msg);
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		log.error("", cause);
		ctx.close();
	}

	public ClientService getRequest() {
		return request;
	}

	public void setRequest(ClientService request) {
		this.request = request;
	}

}
