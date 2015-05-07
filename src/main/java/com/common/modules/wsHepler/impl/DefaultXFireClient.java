package com.common.modules.wsHepler.impl;

import org.apache.http.HttpEntity;
import org.apache.http.entity.StringEntity;

import com.common.modules.wsHepler.iface.HandleReturnIface;

/**
 * @Description: httpclient 调用xfire服务入口
 * @ClassName: DefaultXFireClient 
 * @author huangzy@gzjp.cn
 */
public class DefaultXFireClient extends BaseXFireClient {
	private String template = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:zhuk=\"http://www.zhukejia.com\"><soapenv:Header/><soapenv:Body><zhuk:getReader><zhuk:in0><![CDATA[{0}]]></zhuk:in0></zhuk:getReader></soapenv:Body></soapenv:Envelope>";
	private String SOAPAction = "";
	public DefaultXFireClient(String url) {
		super(url);
	}
	
	public DefaultXFireClient(String url,String SOAPAction) {
		super(url);
		this.SOAPAction = SOAPAction;
	}
	
	public DefaultXFireClient(String url,String SOAPAction,String template) {
		super(url);
		this.SOAPAction = SOAPAction;
		this.template = template;
	}
	
	
	@Override
	public <T> T execute(HttpEntity entity, HandleReturnIface<T> handle)
			throws Exception {
		setHttpHeader("SOAPAction", this.SOAPAction);
		return super.execute(entity, handle);
	}

	public String execute(String msg) throws Exception{
		StringHandle stringHandle = new StringHandle(getCharSet());
		return this.execute(msg,stringHandle);
	}
	
	public <T> T execute(String msg,HandleReturnIface<T> handle) throws Exception{
		StringEntity entity = new StringEntity(messageFormat(msg), getCharSet());
		return this.execute(entity,handle);
	}

	public String getSOAPAction() {
		return SOAPAction;
	}

	public void setSOAPAction(String sOAPAction) {
		SOAPAction = sOAPAction;
	}

	public void setTemplate(String template) {
		this.template = template;
	}
	
	public String getTemplate() {
		return this.template;
	}
}
