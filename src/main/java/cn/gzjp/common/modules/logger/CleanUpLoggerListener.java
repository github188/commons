package cn.gzjp.common.modules.logger;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;

/**
 * 若tomcat/jboss 使用线程池,则需要在web.xml中配制此listener 
 * @Description: TODO
 * @ClassName: CleanUpLoggerListener 
 * @author huangzy@gzjp.cn
 * @date 2014年6月20日
 */
public class CleanUpLoggerListener implements ServletRequestListener {
	
	@Override
	public void requestDestroyed(ServletRequestEvent arg0) {
		//System.out.println(LoggerIdentify.generateAndSetIdentify()+",用户结束请求,"+Thread.currentThread().getName());
		LoggerIdentify.cleanUp();
	}

	@Override
	public void requestInitialized(ServletRequestEvent arg0) {
		//System.out.println(LoggerIdentify.generateAndSetIdentify()+",用户开始请求,"+Thread.currentThread().getName());
		LoggerIdentify.generateAndSetIdentify();
	}

}
