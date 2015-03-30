package cn.gzjp.common.f2s.engine.impl;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import cn.gzjp.common.f2s.conver.ConverToObjIface;
import cn.gzjp.common.f2s.conver.F2SResultIface;
import cn.gzjp.common.f2s.fsEntity.FSEntityIface;

/**
 * 仅处理文件中第一行可封装成一个对象 后面的N行封装成一个List<Object> 的情况
 * @author huangzy
 * 2014年7月9日
 */
public class DefaultFile2ObjHandleImpl extends BaseFile2ObjHandleImpl{
	
	@Override
	public <T extends FSEntityIface> T parseToObj(F2SResultIface result,
			ConverToObjIface annotHandle, Class<T> t)
			throws Exception {
		
		T t0 = t.newInstance();
		
		Field[] fields = t.getDeclaredFields();
		//取第一个对象
		Field f1 = fields[0];
		f1.setAccessible(true);
		//第一个对象(内部类,下同)
		Class c1 = f1.getType();
		
		result.hasNext();
		String str = result.next();
		Object obj1 = annotHandle.strToObject(str, c1);
		
		Field f2 = fields[1];
		f2.setAccessible(true);
		//取得泛型类型
		ParameterizedType pt = (ParameterizedType)f2.getGenericType();
		Class c2 = (Class)pt.getActualTypeArguments()[0];
		
		List list = new ArrayList();
		list = super.parseToObjList(result, annotHandle, c2);
		
		//设置值
		f1.set(t0, obj1);
		f2.set(t0, list);
		
		return t0;
	}



}
