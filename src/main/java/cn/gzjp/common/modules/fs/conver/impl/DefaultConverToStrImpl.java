package cn.gzjp.common.modules.fs.conver.impl;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import cn.gzjp.common.modules.fs.conver.ConverToStrIface;
import cn.gzjp.common.modules.fs.fsEntity.annot.PositionAnnotaion;
import cn.gzjp.common.modules.fs.fsEntity.annot.SplitAnnotaion;

public class DefaultConverToStrImpl implements ConverToStrIface {
	@Override
	public String objToStr(Object obj) throws Exception {
		SplitAnnotaion splitAnnot = obj.getClass().getAnnotation(SplitAnnotaion.class);
		String splitChar = splitAnnot.value();
		
		Field[] fields = obj.getClass().getDeclaredFields();
		
		int length = fields.length;
		List<String> valList = new ArrayList<String>(length);
		
		for(int i=0;i<length;i++){
			valList.add("");
		}
		
		int position = 0;
		String val = null;
		PositionAnnotaion positionAnnot = null;
		//有PositionAnnot注解标识字段数量
		int annotCount = 0;
		for(Field f:fields){
			f.setAccessible(true);
			
			positionAnnot = f.getAnnotation(PositionAnnotaion.class);
			if(positionAnnot!=null){
				annotCount++;
				position = positionAnnot.value();
				
				val = String.valueOf(f.get(obj)==null?"":f.get(obj));
				valList.set(position, val);
			}
		}
		
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<annotCount;i++){
			sb.append(valList.get(i));
			//添加分隔符
			if(i!=annotCount-1){
				sb.append(splitChar);
			}
		}
		
		return sb.toString();
	}
}
