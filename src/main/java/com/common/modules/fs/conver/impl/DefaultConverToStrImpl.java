package com.common.modules.fs.conver.impl;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.common.modules.fs.conver.ConverToStrIface;
import com.common.modules.fs.fsEntity.annot.PositionAnnotaion;
import com.common.modules.fs.fsEntity.annot.SplitAnnotaion;

public class DefaultConverToStrImpl implements ConverToStrIface {
	private static final ConcurrentMap<Class, F2SClazzCache> fieldCache = new ConcurrentHashMap<Class, F2SClazzCache>();
	
	@Override
	public String objToStr(Object obj) throws Exception {
		F2SClazzCache f2SClazzCache = getF2SClazzCache(obj.getClass());
		
		List<String> valList = f2SClazzCache.getValList(obj);
		
		String retStr = f2SClazzCache.valToStr(valList);
		
		return retStr;
	}
	
	private F2SClazzCache  getF2SClazzCache(Class clazz){
		F2SClazzCache f2SClazzCache = fieldCache.get(clazz);
		
		if(f2SClazzCache==null){
			SplitAnnotaion splitAnnot = (SplitAnnotaion)clazz.getAnnotation(SplitAnnotaion.class);
			String splitChar = splitAnnot.value();
			Field[] fields = clazz.getDeclaredFields();
			
			f2SClazzCache = new F2SClazzCache(splitChar,Arrays.asList(fields));
			
			
			fieldCache.put(clazz, f2SClazzCache);
		}
		
		return f2SClazzCache;
	}
	
	private class F2SClazzCache{
		private String splitChar;
		private List<Field> fields;
		
		private Map<Field,Integer> fieldPositionMap;
		public F2SClazzCache(String splitChar, List<Field> fields) {
			super();
			this.splitChar = splitChar;
			this.fields = initField(fields);
		}
		
		public List<String> getValList(Object obj) throws IllegalArgumentException, IllegalAccessException{
			List<String> valList = newList();
			
			int position = 0;
			String val = null;
			for(Field f:this.fields){
				position = this.fieldPositionMap.get(f);
				
				val = String.valueOf(f.get(obj)==null?"":f.get(obj));
				valList.set(position, val);
			}
			
			return valList;
		}
		
		public String valToStr(List<String> valList){
			StringBuilder sb = new StringBuilder();
			
			int annotCount = fields.size();
			for(int i=0;i<annotCount;i++){
				sb.append(valList.get(i));
				//添加分隔符
				if(i!=annotCount-1){
					sb.append(splitChar);
				}
			}
			return sb.toString();
		}
		
		private List<String> newList(){
			int length = fields.size();
			List<String> valList = new ArrayList<String>();
			for(int i=0;i<length;i++){
				valList.add("");
			}
			return valList;
		}
		
		private List<Field> initField(List<Field> initField){
			if(initField==null||initField.size()==0){
				throw new IllegalArgumentException("属性字段不能为空");
			}
			fieldPositionMap = new HashMap<Field,Integer>();
			List<Field> retFields = new ArrayList<Field>();
			for(Field f:initField){
				f.setAccessible(true);
				PositionAnnotaion positionAnnot = f.getAnnotation(PositionAnnotaion.class);
				if(positionAnnot!=null){
					int position = positionAnnot.value();
					
					fieldPositionMap.put(f, position);
					retFields.add(f);
				}
			}
			if(retFields.size()==0){
				throw new RuntimeException("fsEntity 没有找到position注解!");
			}
			return retFields;
		}
		
	}
	
	/*public static void main(String[] args) throws Exception {
		FSVisit fsVisit = new FSVisit("activityCode", "phone", "ip", "visiUrl",
				"userAgent", "userSession", "paramIn", "retOut", "createTime", "extend1", "extend2", "extend3");
		
		DefaultConverToStrImpl impl = new DefaultConverToStrImpl();
		String str = impl.objToStr(fsVisit);
		
		str = impl.objToStr(fsVisit);
		str = impl.objToStr(fsVisit);
		str = impl.objToStr(fsVisit);
		str = impl.objToStr(fsVisit);
		str = impl.objToStr(fsVisit);
		System.out.println(str);
	}*/
}
