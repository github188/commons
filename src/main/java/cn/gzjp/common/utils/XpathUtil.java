/**
 *  Copyright 2014 广州金鹏
 *     all right reserved
 * @author:Mr.Yellow
 *     2014年5月19日
 *     
 * modify by huangzy@gzjp.cn
 */
package cn.gzjp.common.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * XpathUtil 2014年5月19日
 */
public final class XpathUtil {

	// private static XpathUtil xpathUtil = new XpathUtil();

	private XpathUtil() {
	}

	public static DocumentBuilder builder;

	public static XPath xPath;
	static {
		DocumentBuilderFactory domfactory = DocumentBuilderFactory
				.newInstance();
		try {
			builder = domfactory.newDocumentBuilder();
			XPathFactory xpfactory = XPathFactory.newInstance();
			xPath = xpfactory.newXPath();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static Node getNodeForExpress(String express, Node node) {

		try {
			return (Node) xPath.compile(express).evaluate(node,
					XPathConstants.NODE);
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		return null;

	}

	public static NodeList getNodeListForExpress(String express, Node node) {

		try {
			return (NodeList) xPath.compile(express).evaluate(node,
					XPathConstants.NODESET);
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	public static String getNodeStringForExpress(String express, Node node) {

		try {
			return (String) xPath.compile(express).evaluate(node,
					XPathConstants.STRING);
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	public static Document parseDocument(String path) throws IOException,
			SAXException {
		return builder.parse(path);
	}

	public static Document parseDocument(File path) throws IOException,
			SAXException {
		return builder.parse(path);
	}

	public static Document parseDocument(InputStream path) throws IOException,
			SAXException {
		return builder.parse(path);
	}
	
	public static void outputXml(Document doc, OutputStream out)
			throws TransformerException {
		// 将Document输出到流
		TransformerFactory transFactory = TransformerFactory.newInstance();
		Transformer transformer = transFactory.newTransformer();
		//IDENT设定XSLT引擎在输出XML文档时,是否自动添加额外的空格,它可选的值为"yes"、"no"。
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");

		DOMSource source = new DOMSource();
		source.setNode(doc);
		StreamResult result = new StreamResult();
		result.setOutputStream(out);

		transformer.transform(source, result);
	}
}
