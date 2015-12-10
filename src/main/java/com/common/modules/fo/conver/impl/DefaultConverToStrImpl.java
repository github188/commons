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
package com.common.modules.fo.conver.impl;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.fbs.samp.sys.pub.fo.FsConstant;
import com.fbs.samp.sys.pub.fo.annot.FzsFSAnnotaion;
import com.fbs.samp.sys.pub.fo.binder.FsDataBinder;
import com.fbs.samp.sys.pub.fo.binder.FsPropertyEdit;
import com.fbs.samp.sys.pub.fo.conver.ConverToStrIface;

/**
 * @Package: com.fbs.samp.sys.pub.fo.conver.impl
 * @ClassName: DefaultConverToStrImpl
 * @Statement: <p>将对象转为一行字符串</p>
 * @JDK version used: 
 * @Author: 黄忠英
 * @Create Date: 2016年8月17日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class DefaultConverToStrImpl implements ConverToStrIface {
	private static final Map<Class, F2SClazzCache> fieldCache = new ConcurrentHashMap<Class, F2SClazzCache>();

	@Override
	public String objToStr(Object obj) throws Exception {
		if (obj == null) {
			throw new IllegalArgumentException("对象不能为空");
		}
		return getF2SClazzCache(obj.getClass()).objToStr(obj);
	}

	private F2SClazzCache getF2SClazzCache(Class clazz) {
		if (!fieldCache.containsKey(clazz)) {
			F2SClazzCache fzsClazzCache = new F2SClazzCache(clazz);
			fieldCache.put(clazz, fzsClazzCache);
		}
		return fieldCache.get(clazz);
	}

	private class F2SClazzCache {
		//分隔符
		private String splitStr;
		private List<String> fieldNameList;
		private FsPropertyEdit fsPropertyEdit;

		private F2SClazzCache(Class clazz) {
			F2SClazzCache.this.splitStr = FsConstant.DEFAULT_SPLIT_CHAR;
			String[] fieldStrArr = null;
			FzsFSAnnotaion annot = (FzsFSAnnotaion) clazz.getAnnotation(FzsFSAnnotaion.class);
			if (annot != null) {
				F2SClazzCache.this.splitStr = annot.value();
				fieldStrArr = annot.include();
			}

			F2SClazzCache.this.fieldNameList = new ArrayList<String>();
			if (fieldStrArr != null && fieldStrArr.length > 0) {
				fieldNameList.addAll(Arrays.asList(fieldStrArr));
			} else {
				Field[] fieldArr = clazz.getDeclaredFields();
				String fieldName;
				String fieldGetMethod;
				for (Field field : fieldArr) {
					fieldName = field.getName();
					fieldGetMethod = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
					try {
						clazz.getMethod(fieldGetMethod, null);
						fieldNameList.add(fieldName);
					} catch (NoSuchMethodException e) {
					}

				}
			}

			fsPropertyEdit = FsDataBinder.getPropertyEdit(clazz);
		}

		private String objToStr(Object obj) throws Exception {
			StringBuilder sb = new StringBuilder();
			for (String fieldName : fieldNameList) {
				sb.append(fsPropertyEdit.toStr(obj, fieldName));
				sb.append(splitStr);
			}
			if (sb.length() > 0) {
				//去掉最后一个,号
				sb.setLength(sb.length() - 1);
			}
			return sb.toString();
		}
	}

	/*public static void main(String[] args) throws Exception {
		BillingSetupVo vo = new BillingSetupVo();
		vo.setFromDt("2016-08-17");
		//vo.setToDt("2018-08-16");

		ConverToStrIface converToStrIface = new DefaultConverToStrImpl();
		String xx = converToStrIface.objToStr(vo);
		System.out.println(xx);
	}*/
}
