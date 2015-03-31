package cn.gzjp.common.modules.logger;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;

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
