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

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import com.fbs.samp.sys.pub.fo.UnicodeReader;

/**
 * @Package: com.fbs.samp.sys.pub.fs.conver.impl
 * @ClassName: File2StrResultlImpl
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author: 黄忠英
 * @Create Date: 2016年4月28日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */

public class File2StrResultlImpl extends BaseFile2StrResultlImpl {
	private BufferedReader reader;

	public File2StrResultlImpl() {
	}

	public File2StrResultlImpl(String filepath) throws IOException {
		this(new FileInputStream(filepath), DEFAULT_CHARSET, DEFAULT_COMMENTS, DEFAULT_ENDLINE);
	}

	public File2StrResultlImpl(String filepath, String endLine) throws IOException {
		this(new FileInputStream(filepath), DEFAULT_CHARSET, DEFAULT_COMMENTS, endLine);
	}

	public File2StrResultlImpl(InputStream in, String charset, char comments, String endLine) throws IOException {
		super.comments = comments;
		super.endLine = endLine;
		this.reader = initReader(in, charset);
	}

	private BufferedReader initReader(InputStream in, String charset) throws UnsupportedEncodingException {
		return new BufferedReader(new UnicodeReader(in, charset));
	}

	public String readLine() throws IOException {
		return reader.readLine();
	}

	@Override
	public void close() throws IOException {
		if (reader != null) {
			reader.close();
		}
	}

	public void setReader(BufferedReader reader) throws Exception {
		this.reader = reader;
	}

}