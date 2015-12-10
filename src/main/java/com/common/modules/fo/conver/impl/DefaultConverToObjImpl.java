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
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.annotation.AnnotationUtils;

import com.fbs.samp.sys.pub.fo.annot.FzsFSAnnotaion;
import com.fbs.samp.sys.pub.fo.binder.FsDataBinder;
import com.fbs.samp.sys.pub.fo.conver.ConverToObjIface;

/**
 * @Package: com.fbs.samp.sys.pub.fs.conver.impl
 * @ClassName: DefaultConverToObjImpl
 * @Statement: <p>将字符串转为对象 默认实现</p>
 * @JDK version used: 
 * @Author: 黄忠英
 * @Create Date: 2016年4月28日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class DefaultConverToObjImpl implements ConverToObjIface {
	private static final Map<Class, Map<String, Integer>> fieldCache = new ConcurrentHashMap<Class, Map<String, Integer>>();

	public DefaultConverToObjImpl() {
	}

	@Override
	public <T> T strToObject(String str, Class<T> c) throws Exception {
		if (StringUtils.isBlank(str)) {
			return null;
		}

		T t = getInstance(c);
		Object[] arr = FsDataBinder.getPropertyEdit(c).toArray(str);

		if (arr.length == 0) {
			throw new IllegalArgumentException("拆分字符串出错！字符串：" + str);
		}

		Map<String, Integer> fieldAndPosiMap = getFieldAndPosiMap(t);

		return setProperty(arr, fieldAndPosiMap, t);
	}

	private <T> T setProperty(Object[] arr, Map<String, Integer> fieldAndPosiMap, T t) throws IllegalAccessException, InvocationTargetException {
		String fieldName;
		Object fieldVal;
		for (Map.Entry<String, Integer> entry : fieldAndPosiMap.entrySet()) {
			fieldName = entry.getKey();
			fieldVal = arr[entry.getValue()];

			BeanUtils.setProperty(t, fieldName, fieldVal);
		}
		return t;
	}

	private Map<String, Integer> getFieldAndPosiMap(Object t) {
		Class clazz = t.getClass();
		Map<String, Integer> fieldAndPosiMap = fieldCache.get(clazz);
		if (fieldAndPosiMap == null) {
			fieldAndPosiMap = new HashMap<String, Integer>();

			Field[] fieldArr = clazz.getDeclaredFields();
			for (int i = 0; i < fieldArr.length; i++) {
				Field field = fieldArr[i];
				String property = field.getName();
				field.setAccessible(true);
				//仅处理 有相应get方法的属性
				if (PropertyUtils.isReadable(t, property)) {
					fieldAndPosiMap.put(property, i);
				}
			}

			FzsFSAnnotaion annot = AnnotationUtils.findAnnotation(clazz, FzsFSAnnotaion.class);
			if (annot != null) {
				String[] fieldStrArr = annot.include();
				if (fieldStrArr != null && fieldStrArr.length > 0) {
					//清理上面的设置,重新处理.
					fieldAndPosiMap.clear();
					for (int i = 0; i < fieldStrArr.length; i++) {
						fieldAndPosiMap.put(fieldStrArr[i], i);
					}
				}
			}
			fieldCache.put(clazz, fieldAndPosiMap);
		}

		return fieldAndPosiMap;
	}

	private <T> T getInstance(Class<T> c) throws InstantiationException, IllegalAccessException, ClassNotFoundException, IllegalArgumentException,
			SecurityException, InvocationTargetException {

		T t = null;
		String className = c.getName();
		int index = className.indexOf("$");
		//内部类
		if (index >= 0) {
			className = className.substring(0, index);
			Object parent = Class.forName(className).newInstance();

			t = (T) Class.forName(c.getName()).getConstructors()[0].newInstance(parent);
		} else {
			t = c.newInstance();
		}

		//t.getClass().newInstance();
		return t;
	}
}
