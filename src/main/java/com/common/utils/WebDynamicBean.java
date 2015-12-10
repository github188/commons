/**
 * <html>
 * <body>
 *  <p>  All rights reserved.</p>
 *  <p> Created by 黄忠英</p>
 *  <p> Email:h419802957@foxmail.com
 *  </body>
 * </html>
 */
package com.common.utils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.cglib.beans.BeanGenerator;

/**
 * @ClassName: DynamicBeanTest
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author: 黄忠英
 * @Create Date: 2016年3月30日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class WebDynamicBean {

	private Object object;
	private BeanMap beanMap;

	private Map<String, Object> valueMap;
	private Map<String, Class> props;

	public WebDynamicBean(Object bean) {
		props = new TreeMap<String, Class>();
		valueMap = new HashMap<String, Object>();

		init(bean);
	}

	/**
	 * 添加类属性、属性值
	 * @param property 类属性
	 * @param value 类属性值
	 * @param clazz 类属性类型
	 * @return
	 */
	public WebDynamicBean addProperty(String property, Object value, Class clazz) {
		props.put(property, clazz);
		valueMap.put(property, value);

		return this;
	}

	/**
	 * 替换某属性
	 * @param property
	 * @param value
	 * @param clazz
	 * @return
	 */
	public WebDynamicBean replaceProperty(String property, Object value, Class clazz) {
		return this.addProperty(property, value, clazz);
	}

	public WebDynamicBean replaceProperty(String property, Object value) {
		return this.addProperty(property, value, value.getClass());
	}

	/**
	 * 生成最终类
	 * @return
	 */
	public Object generateBean() {
		BeanGenerator generator = new BeanGenerator();
		for (Map.Entry<String, Class> entry : props.entrySet()) {
			generator.addProperty(entry.getKey(), entry.getValue());
		}
		this.object = generator.create();

		//设置值
		this.beanMap = BeanMap.create(this.object);
		beanMap.putAll(valueMap);

		return this.object;
	}

	/**
	 * 替换原bean的property属性为新的replacePropertyBean属性值
	 * @param bean
	 * @param property
	 * @param replacePropertyBean
	 * @return
	 */
	public static Object generateBean(Object bean, String property, Object replacePropertyBean) {
		WebDynamicBean dynamicBean = new WebDynamicBean(bean);
		dynamicBean.replaceProperty(property, replacePropertyBean);
		return dynamicBean.generateBean();
	}

	public void setValue(String property, Object value) {
		beanMap.put(property, value);
	}

	public Object getValue(String property) {
		return beanMap.get(property);
	}

	public Object getObject() {
		return this.object;
	}

	/**
	 * 1、创建并添加与原bean一样的动态类属性
	 * 2、保存原bean属性值
	 * @param bean
	 */
	private void init(Object bean) {
		Class clazz = bean.getClass();
		if (bean.getClass().isPrimitive()) {
			throw new IllegalArgumentException("不支持基本类型或String类型：" + clazz.getName());
		}

		Field[] fieldArr = clazz.getDeclaredFields();
		for (Field field : fieldArr) {
			String property = field.getName();
			field.setAccessible(true);
			//仅处理 有相应get方法的属性
			if (PropertyUtils.isReadable(bean, property)) {
				props.put(property, field.getType());

				try {
					valueMap.put(property, field.get(bean));
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}
	}
}