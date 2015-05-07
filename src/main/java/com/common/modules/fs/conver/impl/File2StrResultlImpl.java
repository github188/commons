package com.common.modules.fs.conver.impl;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.common.modules.fs.conver.F2SResultIface;
import com.common.utils.CloseUtil;
import com.common.utils.UnicodeReader;

/**
 * 对账文件解析工具类
 * @author huangzy
 * 2014年7月8日
 */
public class File2StrResultlImpl implements F2SResultIface{
	private String line;
	private BufferedReader reader;
	//用户记录某一行是否不为空，为空则此标记为0，反之则为1；(预留)
	private List<Integer> flagList = new ArrayList<Integer>();
	
	private static final String DEFAULT_CHARSET = "utf8";
	private static final char DEFAULT_COMMENTS = '#';
	
	private char comments = DEFAULT_COMMENTS;
	
	public File2StrResultlImpl(){}
	
	public File2StrResultlImpl(String filepath) throws IOException{
		this(new FileInputStream(filepath),DEFAULT_CHARSET,DEFAULT_COMMENTS);
	}
	
	public File2StrResultlImpl(InputStream in,char comments) throws IOException{
		this.comments = comments;
		this.reader = initReader(in,DEFAULT_CHARSET);
	}
	
	public File2StrResultlImpl(InputStream in,String charset,char comments) throws IOException{
		this.comments = comments;
		this.reader = initReader(in,charset);
	}
	
	private BufferedReader initReader(InputStream in,String charset) throws UnsupportedEncodingException{
		return new BufferedReader(new UnicodeReader(in,charset));
	}
	
	/**
	 * 递归查找下一行不为空的字符串
	 * @return
	 * @throws IOException
	 */
	public boolean hasNext() throws IOException{
		line=reader.readLine();
		if(line==null){
			return false;
		}
		if(StringUtils.isBlank(line)||line.charAt(0)==this.comments){
			flagList.add(0);
			hasNext();
		}
		flagList.add(1);
		return true;
	}
	
	/**
	 * 返回一行不为空的字符串(请选执行hasNext)
	 * @return
	 */
	public String next(){
		return line;
	}
	
	public List<Integer> getFlagList() {
		return flagList;
	}
	
	@Override
	public void close() throws IOException {
		CloseUtil.close(reader);
	}
	
	public void setReader(BufferedReader reader) throws Exception {
		this.reader = reader;
	}
	
	@Override
	protected void finalize() throws Throwable {
		//防止文件未关闭
		this.close();
		super.finalize();
	}
}
