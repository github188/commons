package cn.gzjp.common.modules.wsHepler.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.HttpEntity;
import org.apache.http.entity.StringEntity;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import cn.gzjp.common.modules.wsHepler.iface.HandleReturnIface;

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
		StringEntity entity = new StringEntity(messageFormat(msg), getCharSet());
		HandleReturnIface<String> handle = getDefaultHandle();
		return execute(entity,handle);
	}

	public HandleReturnIface<String> getDefaultHandle() {
		HandleReturnIface handle = new HandleReturnIface<String>() {
			public String handle(String body) throws ParserConfigurationException, SAXException, IOException {
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder = factory.newDocumentBuilder();
				byte[] buf = body.getBytes(getCharSet());
				Document doc = builder.parse(new ByteArrayInputStream(buf));
				
				/**
				 *如
				 *<soap:Body>
				 * 	<sayHelloResponse xmlns="http://service.test.hzying.com">
				 * 		<out xmlns="http://service.test.hzying.com">Hello, jp.
				 * 		</out>
				 *  </sayHelloResponse>
				 * </soap:Body>
				 */
				Node node = doc.getFirstChild().getFirstChild();
				return node.getTextContent();
			}
			
		};
		return handle;
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
