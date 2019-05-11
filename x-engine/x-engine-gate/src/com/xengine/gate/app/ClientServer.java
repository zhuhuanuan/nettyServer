package com.xengine.gate.app;



import com.xengine.frame.net.client.SockectClient;
import com.xengine.frame.net.client.SocketClientInitializer;

import lombok.extern.slf4j.Slf4j;

/**
 * 模拟客户端
*	@author 朱华煖
 */
@Slf4j
public class ClientServer {
	
	/**
	 * <p>Description:服务器启动入口 </p>  
	 *
	 */
	public static void main(String[] args) {
		try {
			// 启动Netty Client
			Thread thread = new Thread(new SockectClient(new SocketClientInitializer(new ClientHandler()), "127.0.0.1", 8850, "s"));
			thread.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}
