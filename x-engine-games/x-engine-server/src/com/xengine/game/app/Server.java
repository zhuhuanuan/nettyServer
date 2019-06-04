package com.xengine.game.app;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

import com.xengine.frame.net.SpringContext;
import com.xengine.frame.net.redis.RedisCache;
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
		@Autowired
		Environment env;
		
		
		@Bean
		SocketServer socketServer() {
			return new SocketServer(socketInitializer(), env.getProperty("port", int.class));
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
		
		//redis 链接
		RedisCache cache() {
			if(StringUtils.isEmpty(env.getProperty("redispw"))) {
				return new RedisCache(env.getProperty("redisIp"), env.getProperty("redisHost", int.class), 
						env.getProperty("redisMaxActive", int.class), env.getProperty("redisMaxIdle", int.class), 
						env.getProperty("redisMaxWait", int.class));
			}else {
				return new RedisCache(env.getProperty("redisIp"), env.getProperty("redisHost", int.class), 
						env.getProperty("redispw"), env.getProperty("redisMaxActive", int.class), 
						env.getProperty("redisMaxIdle", int.class), env.getProperty("redisMaxWait", int.class));
			}
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
