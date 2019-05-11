package com.xengine.game.app;


import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

import com.xengine.frame.net.SpringContext;
import com.xengine.frame.net.server.SocketInitializer;
import com.xengine.frame.net.server.SocketServer;
import com.xengine.roomserver.app.RoomServerHandler;

import lombok.extern.slf4j.Slf4j;



/**
 * 服务器
* @author 朱华煖
 */
@Slf4j
public class Server{
	
	/**
	 *spring bean 配置
	 */
	@Configuration
	@PropertySource("classpath:/room.properties")
	@ImportResource("classpath:/quartz.xml")
	public static class BeanConfigure {
		
		@Bean
		SocketServer socketServer() {
			return new SocketServer(socketInitializer(), 8850);
		}
		
		@Bean
		SocketInitializer socketInitializer() {
			return new SocketInitializer(roomServerHandler());
		}
		
		@Bean
		RoomServerHandler roomServerHandler() {
			return new RoomServerHandler();
		}
		
		@Bean
		SpringContext springContext() {
			return new SpringContext();
		}
		
	}
	
	/**
	 * 服务器启动入口 
	 */
	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(BeanConfigure.class);;
		context.start();
		log.debug("---------------------");
		SocketServer socket = context.getBean(SocketServer.class);
		new Thread(socket).start();
	}

}
