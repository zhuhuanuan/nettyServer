package com.xengine.frame.net.client;

import com.xengine.frame.net.IHandler;
import com.xengine.frame.net.server.SocketHandler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import lombok.AllArgsConstructor;

/**
 * 客户端
 * @author 朱华煖
 */
@AllArgsConstructor
public class SocketClientInitializer extends ChannelInitializer<SocketChannel>{
	/**netty Handler 接口*/
	private IHandler handler;
	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast("decoder", new MyClientDecoder());
        pipeline.addLast("encoder", new MyClientEncoder());
        pipeline.addLast("handler", new SocketHandler(handler));
	}

}
