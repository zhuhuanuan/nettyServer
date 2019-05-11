package com.xengine.roomserver.app;



import com.xengine.frame.net.IHandler;
import com.xengine.frame.net.Tool;
import com.xengine.frame.net.constant.SmartCarProtocol;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 
* GateServerHandler
* @author 朱华煖
 */
@Slf4j
public class RoomServerHandler implements IHandler {
	
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) {
		System.err.println("链接上服务器 -----------"+Tool.getIp(ctx));
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) {
		System.err.println("断开服务器 -----------"+Tool.getIp(ctx));
		
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		System.err.println("链接异常 ，断开服务器 -----------"+Tool.getIp(ctx)+"  e = "+cause.getMessage());
		ctx.close();
	}

	@Override
	public void channelRead0(ChannelHandlerContext ctx, Object msg) {
		System.err.println("收到客户端的消息 ----------"); 
		 SmartCarProtocol body = (SmartCarProtocol) msg;  
		 System.err.println("收到客户端的消息  --- > "+body.toString()); 
		 byte[] content = body.getContent();
		 String valueOf = new String(content);
		 System.err.println("data[] 解析为-------->"+valueOf.toString());
		 int contentLength = body.getContentLength();
		 
		 System.err.println("获得客户端发来的长度      1 = "+body.getContentLength());
		 System.err.println("获得客户端发来的命令号  2 = "+body.getCmd());
		 System.err.println("获得客户端发来的模块号  3 = "+body.getModular());
		 
		 SmartCarProtocol response = new SmartCarProtocol(contentLength+8,body.getCmd(),body.getModular(), body.getContent());  
		 ChannelFuture writeAndFlush = ctx.writeAndFlush(response);
		 if(writeAndFlush.isSuccess()) {
			 System.err.println("发动成功 -------");
		 }else {
			 System.err.println("发送失败-------");
		 }
		 System.err.println("发送数据給客户端 ------------结束");
		return;
	}
	
	
	
}
