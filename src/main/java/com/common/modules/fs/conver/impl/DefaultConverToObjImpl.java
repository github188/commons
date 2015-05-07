package com.common.modules.fs.conver.impl;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;

import com.common.modules.fs.conver.ConverToObjIface;
import com.common.modules.fs.fsEntity.FSEntityIface;
import com.common.modules.fs.fsEntity.annot.PositionAnnotaion;

/**
 * 将字符串转为对象 实现
 * @Description: TODO
 * @ClassName: DefaultConverToObjImpl 
 * @author huangzy@gzjp.cn
 * @date 2015年5月5日 下午2:14:06
 */
public class DefaultConverToObjImpl implements ConverToObjIface{
	
	private static final ConcurrentMap<Class, HashMap<String,Integer>> fieldCache = new ConcurrentHashMap<Class, HashMap<String,Integer>>();
	
	public DefaultConverToObjImpl(){}
	
	public <T extends FSEntityIface> T strToObject(String str,Class<T> c) throws Exception{
		if(StringUtils.isBlank(str)){
			return null;
		}
		
		T t = getInstance(c);
		
		Object[] arr = t.toArray(str);
		
		if(arr.length==0){
			throw new IllegalArgumentException("拆分字符串出错！字符串："+str);
		}
		
		String fieldName;
		Object fieldVal;
		HashMap<String, Integer> fieldAndPosiMap = getFieldAndPosiMap(c);
		for(Map.Entry<String, Integer> entry: fieldAndPosiMap.entrySet()){
			fieldName = entry.getKey();
			fieldVal = arr[entry.getValue()];
			
			BeanUtils.setProperty(t, fieldName, fieldVal);
		}
		
		return t;
	}
	
	private HashMap<String, Integer>  getFieldAndPosiMap(Class clazz){
		HashMap<String, Integer> fieldAndPosiMap = fieldCache.get(clazz);
		
		if(fieldAndPosiMap==null){
			fieldAndPosiMap = new HashMap<String, Integer>();
			
			Field[] fields = clazz.getDeclaredFields();
			for(Field f:fields){
				PositionAnnotaion positionAnnot = f.getAnnotation(PositionAnnotaion.class);
				if(positionAnnot!=null){
					int position = positionAnnot.value();
					if(fieldAndPosiMap.containsValue(position)){
						throw new RuntimeException("fsEntity 有重复的position位置!");
					}
					String fieldName = f.getName();
					
					fieldAndPosiMap.put(fieldName, position);
				}
			}
			if(fieldAndPosiMap.size()==0) {
				throw new RuntimeException("fsEntity 没有找到position注解!");
			}
			fieldCache.put(clazz, fieldAndPosiMap);
		}
		
		return fieldAndPosiMap;
	}
	
	private <T> T getInstance(Class<T> c) throws InstantiationException, IllegalAccessException,
		ClassNotFoundException, IllegalArgumentException, SecurityException, InvocationTargetException{
		
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
		
		//t.getClass().newInstance();
		return t;
	}
}
