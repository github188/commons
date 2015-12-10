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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fbs.samp.sys.pub.fo.conver.ConverToObjIface;
import com.fbs.samp.sys.pub.fo.conver.impl.DefaultConverToObjImpl;
import com.fbs.samp.sys.pub.fo.rw.BigF2SResultIface;
import com.fbs.samp.sys.pub.fo.rw.F2SResultIface;
import com.fbs.samp.sys.pub.fo.rw.impl.BigFile2StrResultlImpl;

/**
 * @Package: com.fbs.samp.sys.pub.fo
 * @ClassName: ExF2OTemplate
 * @Statement: <p>file to object 执行模板(文件数据转成对象)</p>
 * @JDK version used: 
 * @Author: 黄忠英
 * @Create Date: 2016年5月17日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class ExF2OTemplate<T> {
	private F2SResultIface f2SResultIface;
	private ConverToObjIface converToObjIface;

	private Class<T> clazz;

	private String line;
	//上一行数据，预留。
	private String preLine;

	public ExF2OTemplate(String filepath, Class<T> clazz) throws IOException {
		this(new BigFile2StrResultlImpl(filepath), new DefaultConverToObjImpl(), clazz);
	}

	public ExF2OTemplate(String filepath, int maxLine, Class<T> clazz) throws IOException {
		this(new BigFile2StrResultlImpl(filepath, maxLine), new DefaultConverToObjImpl(), clazz);
	}

	public ExF2OTemplate(String filepath, String endLine, int maxLine, Class<T> clazz) throws IOException {
		this(new BigFile2StrResultlImpl(filepath, endLine, maxLine), new DefaultConverToObjImpl(), clazz);
	}

	/**
	 * @param f2sResultIface
	 * @param converToObjIface
	 */
	public ExF2OTemplate(F2SResultIface f2sResultIface, ConverToObjIface converToObjIface, Class<T> clazz) {
		this.f2SResultIface = f2sResultIface;
		this.converToObjIface = converToObjIface;
		this.clazz = clazz;
	}

	public List<T> conver() throws Exception {
		boolean hasNext = f2SResultIface.hasNext();
		if (!hasNext) {
			return null;
		}

		List<T> list = new ArrayList<T>();
		if (f2SResultIface instanceof BigF2SResultIface) {
			List<String> lineList = ( (BigF2SResultIface) f2SResultIface ).batchNext();
			for (int i = 0; i <= lineList.size() - 1; i++) {
				this.preLine = this.line;
				this.line = lineList.get(i);

				T t = conver0(this.line);
				list.add(t);
			}
		} else {
			do {
				this.line = f2SResultIface.next();
				T t = conver0(this.line);
				list.add(t);
			} while (f2SResultIface.hasNext());
		}
		return list;
	}

	protected T conver0(String str) throws Exception {
		return converToObjIface.strToObject(str, clazz);
	}

	public void close() {
		if (f2SResultIface != null)
			try {
				f2SResultIface.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

	/**
	 * @return the line
	 */
	public String getLine() {
		return line;
	}

	/**
	 * @return the preLine
	 */
	public String getPreLine() {
		return preLine;
	}

	/*public static void main(String[] args) throws Exception {
		ExF2OTemplate<XyAppVo> ex = new ExF2OTemplate<>("E:\\ftpTest\\APP_20160709.txt", XyAppVo.class);
		List<XyAppVo> list = ex.conver();
		System.out.println(list);
	}*/
}
