package com.common.modules.wsHepler.iface;

import org.apache.http.HttpEntity;

/**
 * 
 * @Description: TODO
 * @ClassName: XFireIface 
 * @author huangzy@gzjp.cn
 * @date 2014年6月26日 下午4:22:29
 */
public interface XFireIface{
	/**设置http请求头部*/
	public void setHttpHeader(String name,String value);
	/**取得soap 发送模板*/
	public String getTemplate();

	public <T> T execute(HttpEntity entity,HandleReturnIface<T> handle) throws Exception;
}
