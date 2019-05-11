package com.xengine.frame.net.constant;

import java.util.Arrays;

/**
 * 数据包格式
 * +---------+----------+-----------+-----------+-----------+
 * |协议开始标志   |   长度		|	命令号	|	模块号	|	数据	 	|
 * +---------+----------+-----------+-----------+-----------+
 * 1.协议开始标志 head_data，为int类型的数据，16进制表示为0x76
 * 2.传输数据的长度contentLength， int类型
 * 3.要传输的数据，长度不应该超过2048，防止socket流攻击
 * @author 朱华煖
 */
public class SmartCarProtocol {	
	
	/**
	 * 消息的开头的信息标志
	 */
	private int head_data=ConstantValue.HEND_DATA;
	
	/**
	 * 消息的长度
	 */
	private int contentLength;
	/**
	 * 命令号
	 */
	private int cmd;
	/**
	 * 模块号
	 */
	private int modular;
	
	/**
	 * 消息的内容
	 */
	private byte[] content;

	/**
	 * 初始化
	 * @param contentLength 长度
	 * @param cmd 命令号
	 * @param modular 模块号
	 * @param content 数据data
	 */
	public SmartCarProtocol(int contentLength,int cmd,int modular ,byte[] content) {
	     this.contentLength = contentLength;  
	     this.cmd=cmd;
	     this.modular=modular;
	     this.content = content;
	}
	
	public int getContentLength() {
		return contentLength;
	}

	public void setContentLength(int contentLength) {
		this.contentLength = contentLength;
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

	public int getHead_data() {
		return head_data;
	}

	
	public int getCmd() {
		return cmd;
	}

	public void setCmd(int cmd) {
		this.cmd = cmd;
	}

	public int getModular() {
		return modular;
	}

	public void setModular(int modular) {
		this.modular = modular;
	}

	@Override
	public String toString() {
		return "SmartCarProtocol [head_data=" + head_data + ", contentLength=" + contentLength + ", cmd=" + cmd
				+ ", modular=" + modular + ", content=" + Arrays.toString(content) + "]";
	}
	
}
