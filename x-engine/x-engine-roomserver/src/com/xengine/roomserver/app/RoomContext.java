package com.xengine.roomserver.app;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;

import com.xengine.frame.net.RandomUtil;
import com.xengine.frame.net.SpringContext;

import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 *  房间服上下文
 */
@Slf4j
public abstract class RoomContext{

	/**任务处理最大线程数*/
	protected static final int maxThreadNum = 500;

	/**默认玩家预留数量，用于内存分配*/
	private final static int deafultPlayers = 128;

	/**spring 上下文*/
	@Autowired
	protected SpringContext springContext;



	/**处理玩家任务线程池*/
	protected ExecutorService[] executorServiceList = new ExecutorService[maxThreadNum];

	/**玩家所在线程*/
	protected ConcurrentHashMap<Long, ExecutorService> executorMap = new ConcurrentHashMap<>(deafultPlayers);


	/*服务器是否开启**/
	protected boolean isServerStart = false;


	/**玩家保存任务*/


	/**网关服连接对象*/
	private ChannelHandlerContext gateCtxt;

	public ChannelHandlerContext getGateCtxt() {
		return gateCtxt;
	}

	public void setGateCtxt(ChannelHandlerContext gateCtxt) {
		this.gateCtxt = gateCtxt;
	}

	/**
	 *  构造器
	 */
	public RoomContext() {
		// 处理业务逻辑线程池 顺序队列处理
		for (int i = 0; i < maxThreadNum; i++) {
			executorServiceList[i] = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS,
					new LinkedBlockingQueue<Runnable>());
		}
		
	}

	/**
	 *  获取玩家任务处理线程
	 */
	public ExecutorService getExcutor(long playerId) {
		ExecutorService executorService = null;
		if (executorMap.containsKey(playerId)) {
			executorService = executorMap.get(playerId);
		} else {
			// 随机分配一个线程
			int rand = RandomUtil.getRandomInt(0, maxThreadNum - 1);
			executorService = executorServiceList[rand];
			executorMap.put(playerId, executorService);
		}
		return executorService;
	}







	/**
	 *  服务器是否正常运行 
	 */
	public boolean isServerStart() {
		return isServerStart;
	}



	/**
	 * ServerStop
	 */
	public void ServerStop() {
		this.isServerStart = false;
	}


	/**
	 * 获取spring 上下文
	 */
	public SpringContext getSpringContext() {
		return springContext;
	}

	/**
	 * 设置spring上下文 
	 */
	public void setSpringContext(SpringContext springContext) {
		this.springContext = springContext;
	}

}
