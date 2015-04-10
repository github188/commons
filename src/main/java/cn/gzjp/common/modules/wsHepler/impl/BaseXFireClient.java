package cn.gzjp.common.modules.wsHepler.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.soap.util.xml.DOM2Writer;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import cn.gzjp.common.modules.wsHepler.iface.HandleReturnIface;
import cn.gzjp.common.modules.wsHepler.iface.XFireIface;

/**
 * httpclient 调用xfire服务 基类
 * @Description: TODO
 * @ClassName: BaseXFireClient 
 * @author huangzy@gzjp.cn
 */
public abstract class BaseXFireClient implements XFireIface{
	private String charSet = "utf8";
	
	private HttpPost httppost = null;
	public BaseXFireClient(String url){
		httppost = new HttpPost(url);
	}
	
	public <T> T execute(HttpEntity entity,HandleReturnIface<T> handle) throws Exception{
		CloseableHttpClient httpclient = HttpClients.createDefault();
		
		setHttpHeader("Content-Type", "text/xml; charset=UTF-8");
		setHttpHeader("User-Agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0; gzjp HttpClient4)");
		
		httppost.setEntity(entity);
		CloseableHttpResponse response = httpclient.execute(httppost);
		String retBody = parseResponse(response.getEntity());
		
		if(handle==null){
			throw new IllegalArgumentException("soap 返回需要 handle类处理.");
		}
		T t = handle.handle(retBody);
		
		httpclient.close();
		return t;
	}
	
	protected String messageFormat(List msgList){
		if(StringUtils.isBlank(getTemplate())){
			throw new IllegalArgumentException("模板不能为空!");
		}
		String xml = MessageFormat.format(getTemplate(), msgList);
		return xml;
	}
	
	protected String messageFormat(String msg){
		if(StringUtils.isBlank(getTemplate())){
			throw new IllegalArgumentException("模板不能为空!");
		}
		String xml = MessageFormat.format(getTemplate(), msg);
		return xml;
	}
	
	/**
	 * 返回 soap body 部分
	 * @param entity
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws IllegalStateException
	 * @throws IOException
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 */
	protected String parseResponse(HttpEntity entity) 
			throws UnsupportedEncodingException, IllegalStateException, IOException, SAXException, ParserConfigurationException{
		//找出xfire返回头部定义的字符
		String contentType = entity.getContentType().getValue();
		if(contentType.indexOf(';')>=0){
			String[] arr = contentType.split(";");
			for(String tmp:arr){
				if(tmp.indexOf("charset")>=0){
					this.charSet = tmp.split("=")[1].trim();
					break;
				}
			}
		}else if(contentType.indexOf("charset")>=0){
			this.charSet = contentType.split("=")[1].trim();
		}
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent(),this.charSet));
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse(new InputSource(reader));
		
		StringWriter writer = new StringWriter();
		NodeList nodeList = doc.getElementsByTagName("soap:Body");
		Node node;
		for(int i=0,k=nodeList.getLength();i<k;i++){
			node = nodeList.item(i);
			DOM2Writer.serializeAsXML(node, writer);
		}
		
		
		return writer.toString();
	}
	
	public void setHttpHeader(String name, String value) {
		httppost.setHeader(name, value);
	}
	
	public String getCharSet() {
		return charSet;
	}

	public void setCharSet(String charSet) {
		this.charSet = charSet;
	}

}
