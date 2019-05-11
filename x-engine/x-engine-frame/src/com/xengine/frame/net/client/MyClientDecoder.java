package com.xengine.frame.net.client;

import java.util.List;

import com.xengine.frame.net.constant.SmartCarProtocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

/**
 * 解码器
 * @author 朱华煖
 */
public class MyClientDecoder extends ByteToMessageDecoder {
	
    /** 
     * 协议开始的标准head_data，int类型，占据4个字节. 
     * 表示数据的长度contentLength，int类型，占据4个字节. 
     */ 
	public final int BASE_LENGTH = 4 + 4; 
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		//可读的长度必须大于基本长度
		if(in.readableBytes() >= BASE_LENGTH) {
			//防止socket字节流的攻击
			//防止 客户端传来的数据过大 
			//因为大的数据是不合理的
			if(in.readableBytes() > 2048) {
				in.skipBytes(in.readableBytes());
			}
			
			//消息的长度 
			int length=in.readInt();
			System.err.println("收到服务端长度 -- > "+length);
			//命令号
			int cmd =in.readInt();
			System.err.println("服务端 命令号  = "+cmd);
			//模块号
			int modular =in.readInt(); 
			System.err.println("服务端 模块号  = "+modular);
			length=length-8;
			byte[] data=new byte[length];
			in.readBytes(data);
			SmartCarProtocol pro=new SmartCarProtocol(data.length,cmd, modular,data);
			out.add(pro);
		}
		
	}

}
