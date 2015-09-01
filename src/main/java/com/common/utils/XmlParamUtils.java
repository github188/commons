/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015-11-23</p>
 *  <p> Created by 黄忠英</p>
 *  </body>
 * </html>
 */
package com.common.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

/**
 * @Package: com.yxw.framework.utils
 * @ClassName: XmlParamUtils
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author: 黄忠英
 * @Create Date: 2015-11-23
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class XmlParamUtils {

	/**
	 * 将xml字符串中的?替换为bean中的属性值
	 * 
	 * 示例:
	 * xml=<request><params><branchCode>?</branchCode><patName/><patCardType>?</patCardType><patCardNo>?</patCardNo></params></request>
	 * bean有branchCode=testCode,则将<branchCode>?</branchCode>变为<branchCode>testCode</branchCode>
	 * 
	 * @param xml
	 * @param bean
	 * @return
	 */
	public static String format(String xml, Object bean) {
		return format(xml, bean, null);
	}

	/**
	 * 
	 * @param xml
	 * @param bean
	 * @param aliasesMap 别名
	 * @return
	 */
	public static String format(String xml, Object bean, Map<String, String> aliasesMap) {
		String newXml = xml;

		List<String> list = new ArrayList<String>();

		char[] charArr = xml.toCharArray();
		StringBuilder charSb = new StringBuilder();
		for (int i = 0; i < charArr.length; i++) {
			char c = charArr[i];
			//跳过空白符
			if (c == ' ') {
				continue;
			}

			if (c == '<') {
				//清空
				charSb.setLength(0);
				continue;
			} else if (c == '>') {
				continue;
			} else if (c == '?') {
				//标签名
				String tagName = charSb.toString();
				if (aliasesMap != null && aliasesMap.get(tagName) != null) {
					tagName = aliasesMap.get(tagName);
				}
				try {
					String val = getVal(bean, tagName);
					val = val == null ? "" : val;
					list.add(val);
				} catch (Exception e) {
					e.printStackTrace();
					throw new RuntimeException(e);
				}
			}
			charSb.append(c);
		}
		if (list.size() > 0) {
			newXml = newXml.replaceAll("\\?", "%s");
			newXml = String.format(newXml, list.toArray());
		}
		return newXml;
	}

	private static String getVal(Object obj, String tagName) throws Exception {
		String val = null;
		if (obj instanceof Map) {
			val = (String) ( (Map) obj ).get(tagName);
		} else {
			val = BeanUtils.getProperty(obj, tagName);
		}
		return val;
	}
}
