package cn.gzjp.common.modules.fs.engine.impl;

import java.lang.reflect.Field;
import java.util.List;

import cn.gzjp.common.modules.fs.conver.ConverToStrIface;

public class DefaultObj2StrHandleImpl extends BaseObj2StrHandleImpl{

	@Override
	public String obj2StrConver(Object obj, ConverToStrIface annotHandle)
			throws Exception {
		StringBuilder sb = new StringBuilder();

		Class clazz = obj.getClass();

		Field[] fields = clazz.getDeclaredFields();
		// 取第一个对象
		Field f1 = fields[0];
		f1.setAccessible(true);
		// 第一个对象(内部类,下同)
		//Class c1 = f1.getType();
		Object obj1 = f1.get(obj);
		//第一个类的内容
		sb.append(super.obj2StrConver(obj1, annotHandle));
		sb.append("\n\n");
		
		//取第二个对象的信息
		Field f2 = fields[1];
		f2.setAccessible(true);
		List list = (List)f2.get(obj);
		for(Object obj2:list){
			sb.append(super.obj2StrConver(obj2, annotHandle));
		}
		return sb.toString();
	}
}
