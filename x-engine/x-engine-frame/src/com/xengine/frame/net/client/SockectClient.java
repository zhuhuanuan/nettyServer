package com.xengine.frame.net.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.AdaptiveRecvByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
* <p>Description: netty socket 客户端</p>  
* @author 朱华煖 
 */
@Slf4j
public class SockectClient implements Runnable {
	
	/**host 地址*/
	private String host = "";
	
	/**端口*/
	private int port;
	
	/**服务器唯一标示*/
	private String serverKey;
	
	/**netty socket 初始器*/
	private SocketClientInitializer initializer;
	
	
	/**连接失败后等待重连时间 单位 秒*/
	private int maxWaitTime = 5000;//5秒
	
	/**
	 * 
	* <p>Title: </p>  
	*
	* <p>Description: netty Socket 客户端构造器</p> 
	* 
	* @param initializer
	* @param host
	* @param port
	* @param serverKey
	 */
	public SockectClient(SocketClientInitializer initializer, String host, int port, String serverKey) {
		this.initializer = initializer;
		this.host = host;
		this.port = port;
		this.serverKey = serverKey;
	}
	
	/**
	 * <p>Title: </p>  
	 *
	 * <p>Description: netty Socket 客户端构造器</p> 
	 * 
	 * @param initializer
	 * @param host
	 * @param port
	 * @param type
	 * @param maxWaitTime 最大等待时间,秒
	 */
	public SockectClient(SocketClientInitializer initializer, String host, int port, int maxWaitTime) {
		this.initializer = initializer;
		this.host = host;
		this.port = port;
		if(maxWaitTime>0){
			this.maxWaitTime =  maxWaitTime * 1000 ;//秒
		}
	}

	@Override
	public void run() {
		EventLoopGroup group = new NioEventLoopGroup();
		int count = 0;
		int time = 1000;
		log.debug("开始连接房间服，rs = {} ",serverKey);
		try{
			while(true){
				try {
					count++;
					connect(group, initializer);
				} catch (Exception  e) {
					log.error(e.getMessage());
					try {
						if(count > 300){
							time = maxWaitTime;
						}
						log.debug("房间服为启动，{}毫秒 后重试",time);
						Thread.sleep(time);
					} catch (InterruptedException e1) {
						log.error(e1.getMessage());
					}
					continue;
				}
				log.debug("连接房间服成功，rs = {} ",serverKey);
				break;
			}
		}finally{
			group.shutdownGracefully();
		}
	}

	/**
	 * 
	 * <p>Title: connect</p> 
	 * 
	 * <p>Description:连接服务 </p>  
	 *
	 * @param group
	 * @param pipelineFactory
	 * @return
	 * @throws Exception
	 */
	private void connect(EventLoopGroup group, ChannelInboundHandlerAdapter pipelineFactory) throws Exception{
		Bootstrap b = new Bootstrap();
		b.option(ChannelOption.SO_KEEPALIVE, true)
        .option(ChannelOption.SO_SNDBUF, 1024*1024)
        .option(ChannelOption.SO_RCVBUF, 1024*1024)
        .option(ChannelOption.TCP_NODELAY, true)
        .option(ChannelOption.RCVBUF_ALLOCATOR, AdaptiveRecvByteBufAllocator.DEFAULT)
        .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
		b.group(group).channel(NioSocketChannel.class)
				.handler(pipelineFactory);
		ChannelFuture ch = b.connect(host, port).sync();
		ch.channel().closeFuture().sync();
	}
	
}
