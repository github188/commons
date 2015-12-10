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

import com.fbs.samp.sys.pub.fo.rw.BigF2SResultIface;
import com.fbs.samp.sys.pub.fo.rw.F2SResultIface;

/**
 * @Package: com.fbs.samp.sys.pub.fs.conver
 * @ClassName: BigFile2StrResultlImpl
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author: 黄忠英
 * @Create Date: 2016年4月29日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class BigFile2StrResultlImpl implements BigF2SResultIface {
	private static final int DEFAULT_MAX_LINE = 1000;

	private int maxLine;;

	private F2SResultIface f2SResultIface;

	public BigFile2StrResultlImpl(String filepath) throws IOException {
		this(filepath, DEFAULT_MAX_LINE);
	}

	public BigFile2StrResultlImpl(String filepath, int maxLine) throws IOException {
		this(maxLine, new File2StrResultlImpl(filepath));
	}

	/**
	 * @param maxLine
	 * @param f2sResultIface
	 */
	public BigFile2StrResultlImpl(int maxLine, F2SResultIface f2sResultIface) {
		this.maxLine = maxLine;
		f2SResultIface = f2sResultIface;
	}

	/**
	 * @param filepath
	 * @param endLine
	 * @throws IOException
	 */
	public BigFile2StrResultlImpl(String filepath, String endLine, int maxLine) throws IOException {
		this.f2SResultIface = new File2StrResultlImpl(filepath, endLine);
		this.maxLine = maxLine;
	}

	public List<String> batchNext() throws Exception {
		String next = null;
		List<String> retList = new ArrayList<String>(maxLine);

		boolean hasNext = true;
		for (int i = 0; ( i < maxLine ) && hasNext; i++) {
			next = f2SResultIface.next();
			if (StringUtils.isBlank(next)) {
				break;
			}
			retList.add(next);

			//避免每次循环丢失一条数据
			if (i != maxLine - 1) {
				hasNext = f2SResultIface.hasNext();
			}
		}
		return retList;
	}

	@Override
	public boolean hasNext() throws Exception {
		return f2SResultIface.hasNext();
	}

	/* (non-Javadoc)
	 * @see com.fbs.samp.sys.pub.fs.conver.F2SResultIface#next()
	 */
	@Override
	public String next() throws Exception {
		//throw new UnsupportedOperationException("请调用 batcheNext方法");
		return f2SResultIface.next();
	}

	/* (non-Javadoc)
	 * @see java.io.Closeable#close()
	 */
	@Override
	public void close() throws IOException {
		if (f2SResultIface != null)
			f2SResultIface.close();
	}

}