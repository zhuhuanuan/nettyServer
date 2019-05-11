package com.xengine.gate.app;

import com.xengine.frame.net.IHandler;
import com.xengine.frame.net.constant.SmartCarProtocol;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 
* <p>Title: GateClientHandler</p> 
* 
* <p>Description: 网关服客户端 处理类</p>  
*
* @author 朱华煖
* 
 */
@Slf4j
@AllArgsConstructor
public class ClientHandler implements IHandler {
	

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		//发送一个信息个服务端
		System.err.println("链接服务端成功   发送 你好啊  服务端  给服务端");
		String b="你好!服务端";
		System.err.println("发送的长度  -- >"+b.getBytes().length);
		 SmartCarProtocol response = new SmartCarProtocol(b.getBytes().length,1001,1020, b.getBytes());  
		ChannelFuture writeAndFlush = ctx.writeAndFlush(response);
		if(writeAndFlush.isSuccess()) {
			System.err.println("发送成功 ");
		}else {
			System.err.println("发送失败");
		}
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) {
		
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		ctx.close();
	}

	@Override
	public void channelRead0(ChannelHandlerContext ctx, Object msg) {
		System.err.println("收到服务端的消息 ----------"); 
		 SmartCarProtocol body = (SmartCarProtocol) msg;  
		 System.err.println("收到服务端的消息  --- > "+body.toString()); 
		
	}

}
