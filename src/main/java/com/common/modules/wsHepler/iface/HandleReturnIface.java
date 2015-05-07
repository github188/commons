package com.common.modules.wsHepler.iface;

/**
 * 处理soap 返回的主体部分
 * @author huangzy
 * 2014年6月26日
 */
public interface HandleReturnIface<T> {
	public T handle(String body) throws Exception;
}
