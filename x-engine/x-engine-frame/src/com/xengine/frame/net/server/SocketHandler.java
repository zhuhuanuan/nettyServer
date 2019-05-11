package com.xengine.frame.net.server;


import com.xengine.frame.net.IHandler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;

/**
* @author 朱华煖
 */
@Slf4j
public class SocketHandler extends SimpleChannelInboundHandler {
	/**netty Handler 接口*/
	private IHandler handler;
	
	/**
	 * 
	* <p>Title: </p>  
	*
	* <p>Description: SocketHandler 构造器</p> 
	* 
	* @param handler
	 */
	public SocketHandler(IHandler handler) {
		this.handler = handler;
	}
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		super.channelActive(ctx);
		handler.channelActive(ctx);
	}
	
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		super.channelInactive(ctx);
		handler.channelInactive(ctx);
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		super.exceptionCaught(ctx, cause);
		handler.exceptionCaught(ctx, cause);
	}
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
		try {
			handler.channelRead0(ctx, msg);
		} finally {
			ReferenceCountUtil.release(msg);
		} 
	}
	
	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		super.userEventTriggered(ctx, evt);
	}
	
}
