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
package com.common.modules.fo.rw.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.fbs.samp.sys.pub.fo.rw.F2SResultIface;

/**
 * @Package: com.fbs.samp.sys.pub.fo.rw.impl
 * @ClassName: BaseFile2StrResultlImpl
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author: 黄忠英
 * @Create Date: 2016年5月23日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public abstract class BaseFile2StrResultlImpl implements F2SResultIface {
	protected String line;
	//用户记录某一行是否不为空，为空则此标记为0，反之则为1；(预留)
	protected List<Integer> flagList = new ArrayList<Integer>();

	protected static final String DEFAULT_CHARSET = "utf8";
	protected static final char DEFAULT_COMMENTS = '#';
	protected static final String DEFAULT_ENDLINE = null;

	protected char comments = DEFAULT_COMMENTS;
	//一行的结束标识
	protected String endLine = DEFAULT_ENDLINE;

	/**
	 * 递归查找下一行不为空的字符串
	 * @return
	 * @throws IOException
	 */
	public boolean hasNext() throws IOException {
		line = readLine();
		if (StringUtils.equals(line, endLine) || StringUtils.equals(line, DEFAULT_ENDLINE)) {
			return false;
		}
		if (StringUtils.isBlank(line) || line.charAt(0) == this.comments) {
			flagList.add(0);
			return hasNext();
		}
		flagList.add(1);
		return true;
	}

	/**
	 * 返回一行不为空的字符串(请选执行hasNext)
	 * @return
	 */
	public String next() throws Exception {
		return line;
	}

	public List<Integer> getFlagList() {
		return flagList;
	}

	public abstract String readLine() throws IOException;

	@Override
	protected void finalize() throws Throwable {
		//防止文件未关闭
		this.close();
		super.finalize();
	}

}
