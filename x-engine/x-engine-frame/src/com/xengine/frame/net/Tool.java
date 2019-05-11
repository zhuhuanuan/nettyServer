package com.xengine.frame.net;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;

import io.netty.channel.ChannelHandlerContext;

/**
 * 游戏工具类
 * 
 * 例如 常用的静态功能方法，画图方法
 * @author 刘锦新
 *
 */
public class Tool {
	
	private static Logger logger = LoggerFactory.getLogger(Tool.class);

	public static void sleep(long time)
	{
		if(time <= 0)
		{
			return;
		}
		try
		{
			Thread.sleep(time);
		}catch(Exception e)
		{
			//e.printStackTrace();
		}
	}
	
	public static void safeCloseInputStream(InputStream ips)
	{
		if(ips == null)
		{
			return;
		}

		try
		{
			ips.close();
		}catch(Exception e)
		{
			// e.printStackTrace();
		}
	}

	public static void safeCloseOutputStream(OutputStream ops)
	{
		if(ops == null)
		{
			return;
		}

		try
		{
			ops.close();
		}catch(Exception e)
		{
			// e.printStackTrace();
		}
	}
	

	/**
	 * 将-128,127的byte转换成0,255的int整数
	 * @param b
	 * @return
	 */
	public static short byte2short(byte b)
	{
		//return (short) (b < 0 ? 256 + b : b);
		return (short)(b & 0xff);
	}
	
	public static int parseInt(String text,int defaultValue){
		
    	if (text == null || text.length() <= 0)
    		return defaultValue;
    	try {
    		return Integer.parseInt(text.trim());
    	} catch (Exception ex) {
    	}
    	return defaultValue;
	}
	public static int parseInt(String text){
		return parseInt(text,0);
	}
	
	public static int parseInt16(String text,int defaultValue){
		
    	if (text == null || text.length() <= 0)
    		return defaultValue;
    	try {
    		return Integer.parseInt(text.trim(),16);
    	} catch (Exception ex) {
    	}
    	return defaultValue;
	}
	
	public final static String[] split(String msg,String sep){
		if(msg == null || "".equals(msg) || sep == null || "".equals(sep)){
			return null;
		}
		Vector list = new Vector();
		int index = msg.indexOf(sep) ;
		while(index >= 0){
			String str = msg.substring(0,index);
			list.addElement(str);
			msg = msg.substring(index+1);
			index = msg.indexOf(sep);
		}
		list.addElement(msg);
		String[] tempResult = new String[list.size()];
		int idx = 0;
		String tempStr = null;
		for(int i=0; i<list.size(); i++){
			tempStr = (String)list.elementAt(i);
			if(tempStr == null || "".equals(tempStr)){
				continue;
			}
			tempResult[idx] = tempStr;
			idx++;
		}
		
		String[] result = new String[idx];
		System.arraycopy(tempResult, 0, result, 0, idx);
		return result;
	}
	  
    /**
     * 获得2个矩形区域的交集
     * @param x1
     * @param y1
     * @param w1
     * @param h1
     * @param x2
     * @param y2
     * @param w2
     * @param h2
     * @return 交集数据 ,交集矩形的X,Y,Width,Height
     */
    public static int[] rectGetIntersection(
    		int x1, int y1, int w1, int h1, 
    		int x2, int y2, int w2, int h2)
    {
    	
    	int x = x1;
    	if(x2 > x1){
    		x = x2;
    	}
    	
    	int y = y1;
    	if(y2 > y1){
    		y = y2;
    	}
    	
    	int endX1 = x1 + w1;
    	int endX2 = x2 + w2;
    	
    	int endX = endX1;
    	if(endX2 < endX1){
    		endX = endX2;
    	}
    	
    	int endY1 = y1 + h1;
    	int endY2 = y2 + h2;
    	
    	int endY = endY1;
    	if(endY2 < endY1){
    		endY = endY2;
    	}
    	
    	int w = endX - x;
    	int h = endY - y;
    	
    	if(w < 0 || h < 0){
    		w = 0;
    		h = 0;
    	}
    	return new int[]{x,y,w,h};
    }
    
    /**
     * 常用方法;
     * 判断2个矩形框是否碰撞;
     * 
     * 主要用于判断精灵/精灵切块 是否在 屏幕内;
     * @param _x1
     * @param _y1
     * @param _w1
     * @param _h1
     * @param _x2
     * @param _y2
     * @param _w2
     * @param _h2
     * @return
     */
    public final static boolean isColliding(int _x1, int _y1, int _w1, int _h1,
    		int _x2, int _y2, int _w2, int _h2)
    {
        if(_x1 + _w1 <= _x2      ) return false;
        if(_x1       >= _x2 + _w2) return false;
        if(_y1 + _h1 <= _y2      ) return false;
        if(_y1       >= _y2 + _h2) return false;
        return true;
    }
    
    /**常用方法，判断点是否(pointx,pointy)在Rect(x,y,w,h)区域内
     * @param x
     * @param y
     * @param w
     * @param h
     * @param pointx
     * @param pointy
     * @return
     */
    public final static boolean rectIn(int x, int y, int w, int h, int pointx, int pointy)
    {
        if(x > pointx || y > pointy){
        	return false;
        }
        if((x + w) < pointx ){
        	return false;
        }
        if((y + h) < pointy){
        	return false;
        }
        return true;
    }
    
    public static boolean rectIntersect(int x1, int y1, int w1, int h1, int x2, int y2, int w2, int h2)
    {
       return Tool.isColliding(x1, y1, w1, h1, x2, y2, w2, h2);
    }
    
    /**
     * (x1,y1,w1,h1)是否完全包含(x2,y2,w2,h2)
     * @param x1
     * @param y1
     * @param w1
     * @param h1
     * @param x2
     * @param y2
     * @param w2
     * @param h2
     * @return
     */
    public static boolean rectContain(int x1, int y1, int w1, int h1, int x2, int y2, int w2, int h2)
    {
        if(x1 > x2){
        	return false;
        }
        int i2;
        int j2;
        i2 = x1 + w1;
        j2 = x2 + w2;
        if(i2 < j2 || y1 > y2){
        	return false;
        }
        int k2;
        i2 = y1 + h1;
        k2 = y2 + h2;
        if(i2 < k2){
        	return false;
        }
        return true;
    }
    
    // ============================================================================== //
    // 数组操作
    public static short getShort(byte[] arr, int idx){
    	
    	if (arr==null || (idx + 1 >= arr.length)) {
    		return 0;
    	}
    	int v = arr[idx]  & 0xff;
		v = (v << 8) |  (arr[idx+1]  & 0xFF);
        return (short)v;
	}
    
    public static int getInt(byte[] arr, int idx){
    	
    	if (arr==null || (idx +3 >= arr.length)) {
    		return 0;
    	}
    	
    	int v = arr[idx]  & 0xff;
		v = (v << 8) |  (arr[idx+1]  & 0xFF);
		v = (v << 8) | ( arr[idx+2] & 0xFF);
		v = (v << 8) | ( arr[idx+3] & 0xFF);
        return v;
    }
    
    public static int getLong(byte[] arr, int idx){
    	
    	if (arr==null || (idx + 7 >= arr.length)) {
    		return 0;
    	}
    	
    	int v = arr[idx]  & 0xff;
		v = (v << 8) |  (arr[idx+1] & 0xFF);
		v = (v << 8) | ( arr[idx+2] & 0xFF);
		v = (v << 8) | ( arr[idx+3] & 0xFF);
		v = (v << 8) | ( arr[idx+4] & 0xFF);
		v = (v << 8) | ( arr[idx+5] & 0xFF);
		v = (v << 8) | ( arr[idx+6] & 0xFF);
		v = (v << 8) | ( arr[idx+7] & 0xFF);
        return v;
    }
    public static String getUTF(byte[] datas, int offset)
	{
		if(datas == null || offset >= datas.length - 2)
		{
			return null;
		}
		
		int index = offset;
		short length = getShort(datas, index);
		index += 2;
		if(index + length > datas.length)
		{
			return null;
		}
		
		try
		{
			return new String(datas, index, length, "UTF-8");
		}catch(UnsupportedEncodingException e)
		{
			return new String(datas, index, length);
		}
	}
    // ============================================================================== //
    
    
    public static Random random = new Random();
    
    /**
     * rand(int range) only return [0-range) exclude range.
     * Maximum is (range-1), no check on negative range, don't try.
     * It is similar to CLDC1.1 ran.nextInt(n)
     * @param range
     * @return
     */
    public final static int rand(int range) {
        if (range==0) return 0;
        // assert range>0
        // 0 may have division by zero error.
        return Math.abs(random.nextInt() % range);
    }
   
    /**
	 * random producer
	 * @param start the start point 
	 * @param end the end point 
	 * @return the random value，scope is[start,end]；
	 */
    public final static int rand(int start, int end) {
        int k1 = random.nextInt();
        if (k1 < 0)
            k1 = -k1;
        return k1 % ((end - start) + 1) + start;
    }
    
    public static int[] getCopyData(int[] data){
    	
    	int[] copyData = null;
    	
    	if(data != null){
    		copyData = new int[data.length];
    		System.arraycopy(data, 0, copyData, 0, data.length);
    	}
    	return copyData;
    }

    
    public static int getOffsetValue(int pixel,int totalSize,int screenSize,int screenSizeC) {

		int dis;        
		//地图比屏幕宽;
		if (totalSize > screenSize) {     
			dis = screenSizeC - pixel;            
			//中间以左
			if (dis > 0)
				dis = 0;           
			//中间以右
			else if (dis < screenSize - totalSize)
				dis = screenSize - totalSize;            
		}
		else {
			dis = (screenSize - totalSize) / 2;
		}      
		return dis;
	}
    
    public static final int entropy(int _cur, int _target)
    {
    	return entropy(_cur,_target,false);
    }

    public static final int entropy(int _cur,int _target,boolean isAdjust){
    	if (_cur != _target)
    	{
    		if(isAdjust&&Math.abs(_cur-_target)<30){
    			_cur = _target;
    		}
    		else{
    			int m_oldH = _cur;
    			
//    			_cur = (_cur * 3 + _target) >> 2;
    			//_cur = (_cur * speed1 + _target) >> speed2;
    			
    			_cur = (_cur * 3 + _target * 2) / 5;
    			
//    			_cur += ((_target-_cur) * 2) / 5;
    			
    			if (m_oldH == _cur){
    				_cur = _target;
    			}
    		}
    	}
    	return _cur;
    }
    
    /**
	 * Function name:sqrt
	 * Description: 简单开平方
	 * @param n
	 * @return
	 */
	public static int sqrt(int n) {
		int r, l, t; //r: 方根; l: 余数; t: 试除数; 
		if (n < 100) {
			r = 9;
			while (n < r * r)
				r--;
		} else {
			r = sqrt(n / 100);
			l = n - r * r * 100;
			t = l / (r * 20);
			while (t * (r * 20 + t) > l) {
				t--;
			}
			r = r * 10 + t;
		}
		return r;
	}
	
	public static int getCost(int x1,int y1,int x2,int y2) {
	    // 获得坐标点间差值 公式：(x1, y1)-(x2, y2)
	    int m = Math.abs(x1 - x2);
	    int n = Math.abs(y1 - y2);
	    // 取两节点间欧几理德距离（直线距离）做为估价值，用以获得成本
	    return sqrt(m * m + n * n);
	  }


	// ---------------------- (x,y) Key 相关 ----------------- //
	// 如果要使用负数的话：getXKey,getYKey要 int -> short
	public static int setKeyXY(int x, int y){
		return (x & 0xffff) | ((y & 0xffff) << 16);
	}
	public static int getXKey(int key){
		return (key & 0xffff);
	}
	public static int getYKey(int key){
		return ((key >> 16) & 0xffff);
	}
	// ----------------------------------------------------- //
    
    public static String appendString(String[] strs,int startIndex,int endIndex){
    	StringBuffer sb = new StringBuffer();
    	
    	if(strs != null){
    		for(int i=startIndex;i<endIndex; i++){
    			
    			if(i<0 || i>= strs.length){
    				continue;
    			}
    			
    			if(strs[i] == null){
    				continue;
    			}
    			sb.append(strs[i]);
    		}
    	}
    	
    	return sb.toString();
    }
    
    
	public static void debug(Object object)
	{
		if(object == null)
		{
			return;
		}
//		if(LogicCommon.isOperTest==true){
//			logger.info("DEBUG: " + object.toString());
//		}
	}

	public static String join(short[] list, String sep)
	{
		StringBuffer sb = new StringBuffer();
		
		if(list == null){
			return "";
		}
		
		for(int i=0; i<list.length; i++){
			sb.append(list[i]);
			sb.append(sep);
		}
		
		return sb.toString();
	}
	
    public static String join(byte[] list, String sep)
	{
		StringBuffer sb = new StringBuffer();
		
		if(list == null){
			return "";
		}
		
		for(int i=0; i<list.length; i++){
			sb.append(list[i]);
			sb.append(sep);
		}
		
		return sb.toString();
	}
    public static String join(int[] list, String sep)
	{
		StringBuffer sb = new StringBuffer();
		
		if(list == null){
			return "";
		}
		
		for(int i=0; i<list.length; i++){
			sb.append(list[i]);
			sb.append(sep);
		}
		
		return sb.toString();
	}
    
    /**
     * 实用方法, 打印数组内容
     * @param objects
     * @param objectName
     */
    public static void printArray(Object[] objects, String objectName)
    {
    	if(objects == null) 
    	{
    		return;
    	}
    	
    	for(int i = 0 ; i < objects.length ; i ++)
    	{
    		debug(objectName + "[" + i + "]=" + objects[i]);
    	}
    }
    
    public static boolean isEmulator()
    {
    	//System.getProperty("microedition.platform") //获得手机平台
        byte byte0 = 0;
        try
        {
            if(Class.forName("java.applet.Applet") != null){
            	byte0 = 1;
            }
        }
        catch(Exception exception) { }
        try
        {
            if(byte0 == 0 && Class.forName("emulator.Emulator") != null){
            	byte0 = 2;
            }
        }
        catch(Exception exception1) { }
        //手机顽童 内存=8000000 
        if(byte0 == 0 && Runtime.getRuntime().totalMemory() - 0x3001dbL == 0x4a1025L)
            byte0 = 3;
        return byte0 != 0;
    }
    
    /**
     * 基础值+加成值，检查上下限返回
     * @param base 基础值
     * @param add 加成值
     * @param min 最小值
     * @param max 最大值
     * @return
     */
    public static int sumValue(int base, int add, int min, int max){
    	
    	int tepValue = base+add;
    	
    	if(base > 0 && add > 0 && tepValue <= 0){//越界了,使用最大值
    		tepValue = max;
    	}
    	
    	if(tepValue < min){
    		tepValue = min;
    	}
    	
    	if(tepValue > max){
    		tepValue = max;
    	}
    	
    	return tepValue;
    }

    /**
     * 通过技能ID和技能等级的逻辑计算获取技能惟一的Key
     * @param skillID
     * @param level
     * @return
     */
    public final static Integer getSkillKey(int skillID, byte level){
		return new Integer(((level) << 24 | skillID & 0x00FFFFFF));
	}
    
    /**
     * 通过一个集合String封装一个String数组
     * @param vector
     * @return
     */
    public static final String[] getStringArrayByVector(Vector vector){
    	if(vector == null || vector.size() <= 0){
    		return null;
    	}
    	
    	String[] array = new String[vector.size()];
    	for(int i=0; i<vector.size(); i++){
    		String str = (String) vector.elementAt(i);
    		if(str == null){
    			continue;
    		}
    		
    		array[i] = str;
    	}
    	return array;
    }
    
    /**
     * 判断是否数组越界
     * @param index
     * @param array
     * @return
     */
    public static boolean isArrayIndexOutOfBounds(int index, Object arrayObject){
    	
    	if(arrayObject == null){
    		return true;
    	}
    	
    	int length = 0;
    	if(arrayObject instanceof byte[]){
    		length = ((byte[])arrayObject).length;
    	}
    	else if(arrayObject instanceof short[]){
    		length = ((short[])arrayObject).length;
    	}
    	else if(arrayObject instanceof int[]){
    		length = ((int[])arrayObject).length;
    	}
    	else if(arrayObject instanceof String[]){
    		length = ((String[])arrayObject).length;
    	}
    	else if(arrayObject instanceof Vector){
    		length = ((Vector)arrayObject).size();
    	}
    	
    	if(index < 0 || index >= length){
    		return true;
    	}
    	
    	return false;
    }
    
    /**
     * 获得位数的掩码值
     * 例如
     * 1位 = 0x1;
     * 2位 = 0x3;
     * 3位 = 0x7;
     * @param bitNum
     * @return
     */
    public static int getMaskBitValue(int bitNum){
    	
    	if(bitNum >= 32){
    		return 0xFFFFFFFF;
    	}
    	
    	int value = 0;
    	for(int i=0;i<bitNum;i++){
    		value |= 1 << i;
    	}
    	return value;
    }
    
    /**
	 * 判断字符串是否为NULL | 空
	 * @param text
	 * @return
	 */
	public static boolean isNullText(String text){
		
		if(text == null){
			return true;
		}
		
		if(text.trim().equals("")){
			return true;
		}
		return false;
	}
	
	/**
	 * 位操作运算(设置值)
	 * @param flag true表示设1,false表示设0
	 * @param index 第几位
	 * @param value 设置的值
	 * @return
	 */
	public static int setBit(boolean flag, int index, int value){ 
		if(flag){
			value |=  index;
		}
		else{
			value &= ~index;
		}
		return value;
	}
	/**
	 * 位操作运算(判断值)
	 * @param index 第几位
	 * @param value 判断的值
	 * @return
	 */
	public static boolean isBit(int index, int value){
		return (value & index) != 0;
	}
	
	/**
	 * 获得掩码的位数（最多8位）
	 * 例如 1111 = 4位
	 * @param maskValue
	 * @return
	 */
	public static int getBitNum(int maskValue){
		
		int bitNum = 0;
		for(int i=0;i<8;i++){
			int bitValue = 1 << i;
			if(isBit(bitValue, maskValue)){
				bitNum ++;
			}
		}
		
		return bitNum;
	}
	
	/**
	 * 
	 * Function name:getSubList
	 * Description: 用于对一个列表进行分页处理
	 * @param <T>
	 * @param orgList: 源列表
	 * @param pageIndex：页码
	 * @param pageSize：分页条目数
	 * @param subList：分页后的列表
	 * @return：返回结果(-1分页参数异常 >=0总的页数)
	 */
	public static <T> List<T> getSubList(List<T> orgList,int pageIndex,int pageSize){
		List<T> subList = new ArrayList<T>();
		if(pageIndex<=0){//页面上限超了，返回第一页
			pageIndex = 1;
		}
		
		if(pageSize<=0 || orgList.size()==0){
			return subList;
		}
		
		int pageTotalNum = (orgList.size()%pageSize)==0?(orgList.size()/pageSize):((orgList.size()/pageSize)+1);
		if(pageTotalNum<pageIndex){//页码超了，返回最后一页
			pageIndex = pageTotalNum;
		}
		
		int start = pageSize*(pageIndex-1);
		int end = pageSize*pageIndex;
		end = end>orgList.size()?orgList.size():end;
		subList.addAll(orgList.subList(start, end));
		return subList;
	}
	
	/**
	 * 获取ip
	 * @param ctx
	 * @return
	 */
	public static String getIp(ChannelHandlerContext ctx){
		if (ctx.channel().isOpen()) {
			return ctx.channel().remoteAddress().toString().substring(1);
		}
		return "";
	
	}
	
	
	/**
	 * 检查list里是否有相同的元素
	 * @param list
	 * @return
	 */
	public static boolean isHaveSameIdInList(List<Long> list){
		List<Long> tempList = new ArrayList<>();
		for (Long long1 : list) {
			if(tempList.contains(long1)){
				return true;
			}else{
				tempList.add(long1);
			}
		}
		
		return false;
	}
	
	/**
	 * Function name:getRmiClient
	 * Description: 取得rmi实例
	 * @param url:远程服务器地址,如:"rmi://127.0.0.1:3000/dbServer"
	 * @return
	 */
	public static Object getRmiClient(String url,Class<?> c){
		
		RmiProxyFactoryBean factory = new RmiProxyFactoryBean();
		factory.setServiceInterface(c);
		factory.setServiceUrl(url);// "rmi://127.0.0.1:3000/dbServer"
		factory.afterPropertiesSet();
		factory.setRefreshStubOnConnectFailure(true);
		return factory.getObject();

	}
	
	/**
	 * 
	 * <p>Title: changLong</p> 
	 * 
	 * <p>Description: 把一个long类型数据变成两个int表达</p>  
	 *
	 * @param num
	 * @return
	 */
	public static int[] changLong(long num) {
		int intNum1 = (int) num;
		int intNum2 = (int) (num >> 32);
		return new int[] {intNum1,intNum2};
	}
	 
	/**
	 * 
	 * <p>Title: getLongFromInt</p> 
	 * 
	 * <p>Description: 用两个int 合并成一个long数据</p>  
	 *
	 * @param num1
	 * @param num2
	 * @return
	 */
	public static long getLongFromInt(int num1, int num2) {
		// 简写
		long l1 = (num2 & 0x00000000ffffffffL) << 32;
		long l2 = num1 & 0x00000000ffffffffL;
	 
		long num = l1 | l2;
		return num;
	}
	
	public static void main(String[] args) {
		int[] b = changLong(100033333333333334l);
		System.out.println(" " + b[0] + " " + b[1]);
		System.out.println(getLongFromInt(0, 0));
		System.out.println(Long.MAX_VALUE);
		System.out.println(Integer.MAX_VALUE);
	}
}
