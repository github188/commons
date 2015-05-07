package com.common.utils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class HtmlUtil {
	// 通过递归删除html文件中的配对的html标签
	public static String removeHtmlTag(String htmlStr) {
		Document doc=Jsoup.parse(htmlStr);
		
		return doc.text();
	}

	public static String subString(String htmlStr, int length, String endWith) {
		String result = removeHtmlTag(htmlStr);
		if(result.length()>length){
			result = result.substring(0,length);
			result = result+endWith;
		}
		return result;
	}

	/*public static void main(String[] args) {
		String str0 = "<p><font size=\"2\"><span class=\" mce_class=\"font-size: 10.5pt\">"
				+ "依据绩abc效管理体系的规定，公司&nbsp;决定于</span><span class=\" mce_class=\"font-size: 10.5pt\">"
				+ "2008</span><span class=\" mce_class=\"font-size: 10.5pt\">年</span><span class=\" "
				+ "mce_class=\"font-size: 10.5pt\">12</span><span class=\" mce_class=\"font-size: 10.5pt\">"
				+ "月</span><span class=\" mce_class=\"font-size: 10.5pt\">22</span><span class=\" "
				+ "mce_class=\"font-size: 10.5pt\">日</span><span class=\" mce_class=\"font-size: 10.5pt\">"
				+ "\"-2009</span><span class=\" mce_class=\"font-size: 10.5pt\">年</span><span class=\" "
				+ "mce_class=\"font-size: 10.5pt\">1</span><span class=\" mce_class=\"font-size: 10.5pt\">"
				+ "月</span><span class=\" mce_class=\"font-size: 10.5pt\"> 23& </span><span class=\" "
				+ "mce_class=\"font-size: 10.5pt\">日期间进行</span><span class=\" mce_class=\"font-size: "
				+ "10.5pt\">2008</span><span class=\" mce_class=\"font-size: 10.5pt\">年年度绩效考评工作，"
				+ "具体事项如下：</span></font></p>";
		
		String str1 = "<p>我是单选</p><p>guohang</p><p><img src=\"/gd-exp/ueditor/jsp/upload/image/20141202/1417499873628055185.jpg\" title=\"1417499873628055185.jpg\" alt=\"test.jpg\"/></p>";
		String str2 = "<p>我是单选题&nbsp;<a href=\"http://www.baidu.com\" target=\"_self\">baidu</a></p><p><img src=\"/gd-exp/ueditor/jsp/upload/image/20141202/1417506915713077405.png\" title=\"1417506915713077405.png\" alt=\"发光.png\"/></p>";
		
		//Document doc=Jsoup.parse(str0);
		
		System.out.println(subStringHTML(str0, 60, "..."));
	}*/
}
