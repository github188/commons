/**
 * <html>
 * <body>
 *  <p> Created by huangzy</p>
 *  <p> Email:h419802957@foxmail.com
 *  </body>
 * </html>
 */
package com.common.utils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanMap;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * @ClassName: BeanUtils
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author: huangzy
 * @Create Date: 2016年6月21日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class BeanUtils {
	public static Map<String, Object> toMap(Object obj) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (obj == null) {
			return map;
		}
		if (obj instanceof Map) {
			return (Map) obj;
		}

		BeanMap beanMap = new BeanMap(obj);
		Iterator<String> it = beanMap.keyIterator();
		while (it.hasNext()) {
			String name = it.next();
			Object value = beanMap.get(name);
			// 转换时会将类名也转换成属性，此处去掉
			if (value != null && !"class".equalsIgnoreCase(name)) {
				map.put(name, value);
			}
		}
		return map;
	}

	/**
	 * 复制不为空的属性
	 * @param from
	 * @param to
	 * @param isCover 如果目标对象属性不为空是否覆盖
	 */
	public static Object copyProperties(Object from, Object to, boolean isCover) throws Exception {
		Map<String, Object> map = toMap(from);

		Iterator<String> iter = map.keySet().iterator();
		while (iter.hasNext()) {
			String key = iter.next();
			Object val = map.get(key);
			if (val == null || StringUtils.isEmpty(val.toString())) {
				iter.remove();
			}
		}
		if (isCover) {
			org.apache.commons.beanutils.BeanUtils.populate(to, map);
			return to;
		}

		for (Map.Entry<String, Object> entry : map.entrySet()) {
			try {
				Object obj = PropertyUtils.getProperty(to, entry.getKey());
				if (obj == null || StringUtils.isEmpty(obj.toString())) {
					PropertyUtils.setProperty(to, entry.getKey(), entry.getValue());
				}
			} catch (NoSuchMethodException e) {//跳过无属性,或异常的赋值.
			}
		}
		return to;
	}

	public static void copyProperties(Object from, Object to) throws Exception {
		copyProperties(from, to, true);
	}

	public static <T> T copyProperties(Object from, Class<T> clz) throws Exception {
		T to = clz.newInstance();
		return (T) copyProperties(from, to, true);
	}

	public static void copyPropertiesExclude(Object from, Object to, String... excludesArray) throws Exception {
		Map<String, Object> map = toMap(from);
		List<String> excludeList = Arrays.asList(excludesArray);

		Set<String> keySet = map.keySet();
		Iterator<String> iter = keySet.iterator();
		while (iter.hasNext()) {
			if (excludeList.contains(iter.next())) {
				iter.remove();
			}
		}
		org.apache.commons.beanutils.BeanUtils.populate(to, map);
	}

	public static void copyPropertiesInclude(Object from, Object to, String[] includesArray) throws Exception {
		Map<String, Object> map = toMap(from);
		List<String> includesList = Arrays.asList(includesArray);

		Set<String> keySet = map.keySet();
		Iterator<String> iter = keySet.iterator();
		while (iter.hasNext()) {
			if (!includesList.contains(iter.next())) {
				iter.remove();
			}
		}
		org.apache.commons.beanutils.BeanUtils.populate(to, map);
	}

	/**
	 * 获取接口的泛型类型，如果不存在则返回null
	 * 
	 * @param clazz
	 * @return
	 */
	public static Class<?> getGenericClass(Class<?> clazz) {
		Type t = clazz.getGenericSuperclass();
		if (t instanceof ParameterizedType) {
			Type[] p = ( (ParameterizedType) t ).getActualTypeArguments();
			return ( (Class<?>) p[0] );
		}
		return null;
	}

	/**
	 * 获取属性泛型
	 * @param field
	 * @return
	 */
	public static Class<?> getGenericClass(Field field) {
		ParameterizedType pt = (ParameterizedType) field.getGenericType();
		Class clazz = (Class) pt.getActualTypeArguments()[0];

		return clazz;
	}

	public static void setProperty(Object bean, String name, Object value) throws NoSuchFieldException, SecurityException, IllegalArgumentException,
			IllegalAccessException {
		Field field = bean.getClass().getDeclaredField(name);
		field.setAccessible(true);
		field.set(bean, value);
	}
}
