package cn.gzjp.common.f2s.conver.impl;

import java.lang.reflect.Field;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;

import cn.gzjp.common.f2s.conver.ConverToObjIface;
import cn.gzjp.common.f2s.fsEntity.FSEntityIface;
import cn.gzjp.common.f2s.fsEntity.annot.PositionAnnotaion;

public class DefaultConverToObjImpl implements ConverToObjIface{
	
	public DefaultConverToObjImpl(){}
	
	public <T extends FSEntityIface> T strToObject(String str,Class<T> c) throws Exception{
		if(StringUtils.isBlank(str)){
			return null;
		}
		
		T t = null;
		String className = c.getName();
		int index = className.indexOf("$");
		//内部类
		if(index>=0){
			className = className.substring(0,index);
			Object parent = Class.forName(className).newInstance();
			 
			t = (T)Class.forName(c.getName()).getConstructors()[0].newInstance(parent);
		}else{
			t = c.newInstance();
		}
		
		Object[] arr = t.toArray(str);

		if(arr.length==0){
			throw new IllegalArgumentException("拆分字符串出错！字符串："+str);
		}

		int position = 0;
		String fieldName;
		Object fieldVal;
		Field[] fields = c.getDeclaredFields();
		for(Field f:fields){
			PositionAnnotaion positionAnnot = f.getAnnotation(PositionAnnotaion.class);
			if(positionAnnot!=null){
				position = positionAnnot.value();
				
				fieldName = f.getName();
				
				fieldVal = arr[position];
				BeanUtils.setProperty(t, fieldName, fieldVal);
			}
		}
		return t;
	}
}
