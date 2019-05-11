package com.xengine.frame.net.server;

import com.xengine.frame.net.constant.SmartCarProtocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 编码器
 * @author 朱华煖
 */
public class MyEncoder extends MessageToByteEncoder<SmartCarProtocol>{

	@Override
	protected void encode(ChannelHandlerContext ctx, SmartCarProtocol msg, ByteBuf out) throws Exception {
			
		System.err.println("写给客户端长度   = "+msg.getContentLength());
		System.err.println("写给客户端命令号   = "+msg.getCmd());
		System.err.println("写给客户端模块号   = "+msg.getModular());
		//写入信息  msg的具体内容
		//1.写入消息开头的信息标志 int类型
//		out.writeInt(msg.getHead_data());
		//2.写入信息的长度  int类型
		out.writeInt(msg.getContentLength());
		//命令号
		out.writeInt(msg.getCmd());
		//模块
		out.writeInt(msg.getModular());
		//3.写入信息的内容   byte[]类型
		out.writeBytes(msg.getContent());
		
	}

}
