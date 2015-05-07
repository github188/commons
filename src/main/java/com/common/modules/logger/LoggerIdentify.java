package com.common.modules.logger;

import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * 记录日志 标识,一次会话产生一个唯一标识.
 * @ClassName: LoggerIdentify 
 * @Description: TODO
 * @author huangzy@gzjp.cn
 * @date 2014年7月22日 上午9:29:55
 */
public class LoggerIdentify{
	private static final ThreadLocal<String> identifyThreadLocal = new ThreadLocal<String>();

	public static String get() {
		return identifyThreadLocal.get();
	}
	
	public static void set(String identify){
		identifyThreadLocal.set(identify);
	}
	
	public static void cleanUp(){
		identifyThreadLocal.remove();
	}
	
	public static String generateAndSetIdentify(){
		String identify = (String)LoggerIdentify.get();
		if(StringUtils.isBlank(identify)){
			identify = generateIdentify();
			LoggerIdentify.set(identify);
		}
		return identify;
	}
	
	/**
	 * 生成日志标识
	 * @return
	 */
	private static String generateIdentify(){
		String identify = Thread.currentThread().getId()+""+System.currentTimeMillis()+""+RandomUtils.nextInt(999999);
		return identify;
	}
}
