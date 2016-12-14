package com.jeremiahxu.nio.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

/**
 * 服务端处理类
 * 
 * @author Jeremiah Xu
 */
public class SimpleServerHandler extends ChannelInboundHandlerAdapter {
	protected Logger log = LoggerFactory.getLogger(this.getClass());
	private ServerService response;

	public SimpleServerHandler(ServerService response) {
		this.response = response;
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		try {
			ByteBuf buffer = (ByteBuf) msg;
			byte[] request = new byte[buffer.readableBytes()];
			buffer.readBytes(request);
			byte[] response = this.response.response(request);
			ctx.writeAndFlush(Unpooled.copiedBuffer(response)).addListener(
					ChannelFutureListener.CLOSE);
		} catch (Exception e) {
			log.error("SimpleServerHandler.channelRead():", e);
		} finally {
			ReferenceCountUtil.release(msg);
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (4)
		log.error("", cause);
		ctx.close();
	}

	public ServerService getResponse() {
		return response;
	}

	public void setResponse(ServerService response) {
		this.response = response;
	}

}