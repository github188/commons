package com.common.modules.wsHepler.impl;

import java.io.ByteArrayInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.common.modules.wsHepler.iface.HandleReturnIface;

public class StringHandle implements HandleReturnIface<String> {
	
	private String charset;
	
	private static final String CHARSET = "utf8";
	public StringHandle(){
		this(CHARSET);
	}
	
	public StringHandle(String charset){
		this.charset = charset;
	}
	
	@Override
	public String handle(String body) throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		byte[] buf = body.getBytes(charset);
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

}
