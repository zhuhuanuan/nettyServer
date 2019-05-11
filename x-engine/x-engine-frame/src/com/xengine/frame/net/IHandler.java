package com.xengine.frame.net;



import io.netty.channel.ChannelHandlerContext;

/**
	@author 朱华煖
 */
public interface IHandler {
	
	/**
	 * 
	 * <p>Title: channelActive</p> 
	 * 
	 * <p>Description: 与终端建立连接</p>  
	 *
	 * @param ctx
	 */
	void channelActive(ChannelHandlerContext ctx) throws Exception;
	
	/**
	 * 
	 * <p>Title: channelInactive</p> 
	 * 
	 * <p>Description: 与终端断开连接</p>  
	 *
	 * @param ctx
	 */
	void channelInactive(ChannelHandlerContext ctx);
	
	/**
	 * 
	 * <p>Title: exceptionCaught</p> 
	 * 
	 * <p>Description: 连接异常</p>  
	 *
	 * @param ctx
	 * @param cause
	 */
	void exceptionCaught(ChannelHandlerContext ctx, Throwable cause);
	
	/**
	 * 
	 * <p>Title: channelRead0</p> 
	 * 
	 * <p>Description: 收到终端消息</p>  
	 *
	 * @param ctx
	 * @param msg
	 */
	void channelRead0(ChannelHandlerContext ctx, Object msg);
}
