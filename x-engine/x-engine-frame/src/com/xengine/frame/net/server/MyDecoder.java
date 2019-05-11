package com.xengine.frame.net.server;

import java.util.List;

import com.xengine.frame.net.constant.ConstantValue;
import com.xengine.frame.net.constant.SmartCarProtocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

/**
 * 解码器
 * @author 朱华煖
 */
public class MyDecoder extends ByteToMessageDecoder {
	
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
			if(in.readableBytes() >2048) {
				in.skipBytes(in.readableBytes());
			}
			//记录包头开始的index
			int beginReader;
			
			
			while(true) {
				//获取包头开始的index
				beginReader=in.readerIndex();
				//获取包头开始的index
				in.markReaderIndex();
				//读到了协议的开始标志，结束while循环
				if(in.readInt() == ConstantValue.HEND_DATA) {
					break;
				}
				//未读到包头，略过一个字节
				//每次略过，一个字节，去读取，包头信息的开始标志
				in.resetReaderIndex();
				in.readByte();
				
				//当略过，一个字节后   数据包的长度，又变得不满足，此时，应该结束。等待后面的数据到达
				if(in.readableBytes() < BASE_LENGTH) {
					return ;
				}
				
			}
			
			
			//消息的长度 
			int length=in.readInt();
			
			//判断请求数据包是否到齐
			if(in.readableBytes() < length) {
				in.readerIndex(beginReader);//数据包未齐
				return ;
			}
			//命令号
			int cmd =in.readInt();
			//模块号
			int modular =in.readInt(); 
			byte[] data=new byte[length];
			in.readBytes(data);
			SmartCarProtocol pro=new SmartCarProtocol(data.length,cmd, modular,data);
			out.add(pro);
		}
		
	}

}
