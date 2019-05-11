package com.xengine.frame.net.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.AdaptiveRecvByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.net.ServerSocket;


/**
* <p>Description: netty Socket Server</p>  
* @author 朱华煖
 */
@Slf4j
public class SocketServer implements Runnable{
	
	/**channel 初始器*/
	private ChannelInitializer<SocketChannel> channelInitializer;
	
	/**服务端口*/
	private int port;

	/**
	 * 
	* <p>Title: </p>  
	*
	* <p>Description: SocketServer构造器</p> 
	* 
	* @param pipelineFactory
	* @param port
	 */
	public SocketServer(ChannelInitializer<SocketChannel> pipelineFactory, int port) {
		this.channelInitializer = pipelineFactory;
		this.port = port;
	}
	
	/**
	 * 
	 * <p>Title: bind</p> 
	 * 
	 * <p>Description: 绑定服务</p>  
	 *
	 * @return
	 * @throws InterruptedException
	 */
	public boolean bind() throws InterruptedException {
		if(channelInitializer == null || port <= 0){
			log.error("socker server initializer fail !!!");
			return false;
		}
		if (isPortAvailable(port)) {
			log.info("Server is establishing to listening at :" + port);
		} else {
			log.error("Server's port :" + port + " not available");
			System.exit(-1);
			return false;
		}
		// Configure the server.
	    EventLoopGroup bossGroup = new NioEventLoopGroup();
	    EventLoopGroup workerGroup = new NioEventLoopGroup();
	    try {
	        ServerBootstrap b = new ServerBootstrap();
	        b.group(bossGroup, workerGroup)
	        .channel(NioServerSocketChannel.class)
	         .option(ChannelOption.SO_BACKLOG, 1024)
	         .option(ChannelOption.SO_SNDBUF, 1024*1024)
	         .option(ChannelOption.SO_RCVBUF, 1024*1024)
	         .option(ChannelOption.TCP_NODELAY, true)
	         .option(ChannelOption.RCVBUF_ALLOCATOR, AdaptiveRecvByteBufAllocator.DEFAULT)
	         .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
	         .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
	         .childOption(ChannelOption.SO_KEEPALIVE, true)
	         .childOption(ChannelOption.CONNECT_TIMEOUT_MILLIS, 30000)
	         .childHandler(channelInitializer);
	 
	        // Start the server.
	        ChannelFuture f = b.bind(port).sync();
	        
	        log.debug("socket server is started bind prot ",port);
	        f.channel().closeFuture().sync();
	    } finally {
	        // Shut down all event loops to terminate all threads.
	        bossGroup.shutdownGracefully();
	        workerGroup.shutdownGracefully();
	        
	        // Wait until all threads are terminated.
	        bossGroup.terminationFuture().sync();
	        workerGroup.terminationFuture().sync();
	    }
	    return true;

	}
	/**
	 * 
	 * <p>Title: isPortAvailable</p> 
	 * 
	 * <p>Description: 查看端口是否可用</p>  
	 *
	 * @param port
	 * @return
	 */
	public static final boolean isPortAvailable(int port) {
		try {
			ServerSocket ss = new ServerSocket(port);
			ss.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public void run() {
		try {
			bind();
		} catch (InterruptedException e) {
			log.error("bind socket server exception!!!",e);
			System.exit(0);
		}
	}

}
