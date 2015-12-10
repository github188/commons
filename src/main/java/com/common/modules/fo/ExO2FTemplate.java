/**
 * <html>
 * <body>
 *  <P>  Copyright 2016-2017 www.phone580.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created by 黄忠英</p>
 *  <p> Email:h419802957@foxmail.com
 *  </body>
 * </html>
 */
package com.common.modules.fo;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

import com.fbs.samp.sys.pub.fo.conver.ConverToStrIface;
import com.fbs.samp.sys.pub.fo.conver.impl.DefaultConverToStrImpl;

/**
 * @Package: com.fbs.samp.sys.pub.fo
 * @ClassName: ExO2FTemplate
 * @Statement: <p>object to file 执行模板(对象转成字符串并写入文件)</p>
 * @JDK version used: 
 * @Author: 黄忠英
 * @Create Date: 2016年8月17日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class ExO2FTemplate<T> {
	private PrintWriter pw;
	private ConverToStrIface converToStrIface;

	public ExO2FTemplate(String filepath) throws FileNotFoundException, UnsupportedEncodingException {
		this(new PrintWriter(filepath, "utf8"), new DefaultConverToStrImpl());
	}

	/**
	 * @param filepath
	 * @param pw
	 * @param converToStrIface
	 */
	public ExO2FTemplate(PrintWriter pw, ConverToStrIface converToStrIface) {
		super();
		this.pw = pw;
		this.converToStrIface = converToStrIface;
	}

	public void writeOut(T obj) throws Exception {
		writeOut(Arrays.asList(obj));
	}

	public void writeOut(List<T> list) throws Exception {
		if (list == null) {
			throw new IllegalArgumentException("入参不能为空");
		}
		for (Object obj : list) {
			String line = converToStrIface.objToStr(obj);
			pw.println(line);
		}
	}

	public void close() {
		if (pw != null) {
			pw.close();
			pw = null;
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#finalize()
	 */
	@Override
	protected void finalize() throws Throwable {
		this.close();
		super.finalize();
	}

}
