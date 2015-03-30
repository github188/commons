package cn.gzjp.common.f2s.exec;

import java.io.OutputStream;
import java.io.Writer;

import cn.gzjp.common.f2s.conver.ConverToStrIface;
import cn.gzjp.common.f2s.conver.impl.DefaultConverToStrImpl;
import cn.gzjp.common.f2s.engine.Obj2StrHandleIface;
import cn.gzjp.common.f2s.engine.impl.DefaultObj2StrHandleImpl;

/**
 * @ClassName: Str2FileTemplateEx 
 * @Description: 将对象转为文件,执行模板.默认编码utf8
 * @author huangzy@gzjp.cn
 * @date 2014年7月17日 下午3:02:37
 */
public class Str2FileTemplateEx {
	private Obj2StrHandleIface obj2Strhandle = new DefaultObj2StrHandleImpl();
	private ConverToStrIface converToStr = new DefaultConverToStrImpl();
	
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
	
	public void writeOut(Object obj,OutputStream os,String charset,Obj2StrHandleIface obj2Strhandle) throws Exception{
		setObj2Strhandle(obj2Strhandle);
		writeOut(obj,os,charset);
	}

	public void setObj2Strhandle(Obj2StrHandleIface obj2Strhandle) {
		this.obj2Strhandle = obj2Strhandle;
	}

	public void setAnnotHandle(ConverToStrIface annotHandle) {
		this.converToStr = annotHandle;
	}
	
	/*public static void main(String[] args) throws Exception {
		EcReqEntity e = new EcReqEntity();
	
		Head head = e.new Head();
		head.setSuAmountCount(1);
		head.setSuCount(2);
		head.setTotalFileCount(99);
		head.setTotalReCount(1000);
	
		Body body = e.new Body();
		body.setBizTypeID("中文_bizTypeID1");
		body.setChan_type("chan_type1");
		body.setCharge_party("charge_party1");
		body.setCust_id("cust_id1");
		body.setFee(100);
		body.setOrderid("orderid1");
		body.setTime("201407171105");
	
		e.setHead(head);
		List<Body> bodyList = new ArrayList<Body>();
		bodyList.add(body);
		e.setBodyList(bodyList);
	
		Str2FileTemplateEx ex = new Str2FileTemplateEx();
		ex.writeOut(e, System.out);
	}*/
	
}
