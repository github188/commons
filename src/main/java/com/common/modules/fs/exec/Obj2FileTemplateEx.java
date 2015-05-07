package com.common.modules.fs.exec;

import java.io.OutputStream;
import java.io.Writer;

import com.common.modules.fs.conver.ConverToStrIface;
import com.common.modules.fs.conver.impl.DefaultConverToStrImpl;
import com.common.modules.fs.engine.Obj2StrHandleIface;
import com.common.modules.fs.engine.impl.BaseObj2StrHandleImpl;

/**
 * @ClassName: Str2FileTemplateEx 
 * @Description: 将对象转为文件,执行模板.默认编码utf8
 * @author huangzy@gzjp.cn
 * @date 2014年7月17日 下午3:02:37
 */
public class Obj2FileTemplateEx {
	private Obj2StrHandleIface obj2Strhandle = new BaseObj2StrHandleImpl();
	private ConverToStrIface converToStr = new DefaultConverToStrImpl();
	
	public Obj2FileTemplateEx(){}
	
	public Obj2FileTemplateEx(Obj2StrHandleIface obj2Strhandle,ConverToStrIface converToStr){
		this.obj2Strhandle = obj2Strhandle;
		this.converToStr = converToStr;
	}
	
	public String writeOut(Object obj) throws Exception{
		String str = obj2Strhandle.obj2StrConver(obj, converToStr);
		return str;
	}
	
	public void writeOut(Object obj, OutputStream os) throws Exception {
		writeOut(obj, os, "utf8");
	}
	
	public void writeOut(Object obj, OutputStream os,String charset) throws Exception {
		String str = writeOut(obj);
		os.write(str.getBytes(charset));
	}
	
	public void writeOut(Object obj, Writer writer) throws Exception {
		String str = writeOut(obj);
		writer.write(str);
	}
	
	public void setObj2Strhandle(Obj2StrHandleIface obj2Strhandle) {
		this.obj2Strhandle = obj2Strhandle;
	}

	public void setAnnotHandle(ConverToStrIface annotHandle) {
		this.converToStr = annotHandle;
	}
	
}
