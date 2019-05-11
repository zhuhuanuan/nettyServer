package com.xengine.frame.net.server;


import com.xengine.frame.net.IHandler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import lombok.AllArgsConstructor;

/**
* @author 朱华煖
 */
@AllArgsConstructor
public class SocketInitializer extends ChannelInitializer<SocketChannel> {
	
	/**netty Handler 接口*/
	private IHandler handler;
    @Override
    public void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast("decoder", new MyDecoder());
        pipeline.addLast("encoder", new MyEncoder());
        pipeline.addLast("handler", new SocketHandler(handler));
    }
}
