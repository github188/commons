package com.common.modules.logger;

import java.util.ArrayList;
import java.util.List;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;

/**
 * 过滤日志
 * @author huangzy
 * 2014年6月18日
 */
public class LogFilter extends Filter<ILoggingEvent> {
	public final static List<String> formatMsg = new ArrayList<String>();
	static {
		LogFomartEnum[] logEnumArr = LogFomartEnum.values();
		for(LogFomartEnum l:logEnumArr){
			formatMsg.add(l.getFomart());
		}
	}

	@Override
	public FilterReply decide(ILoggingEvent event) {

		String msg = event.getMessage();
		
		boolean match = false;
		for(String format:formatMsg){
			if(msg.contains(format)){
				match = true;
				break;
			}
		}
		
		return match?FilterReply.ACCEPT:FilterReply.DENY;
	}

}
