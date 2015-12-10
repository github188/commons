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
package com.common.modules.fo.binder;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.annotation.AnnotationUtils;

import com.fbs.samp.sys.pub.fo.FsConstant;
import com.fbs.samp.sys.pub.fo.annot.FzsFSAnnotaion;

/**
 * @Package: com.fbs.samp.sys.pub.fs.binder
 * @ClassName: DefaultPropertyEdit
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author: 黄忠英
 * @Create Date: 2016年5月16日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class DefaultPropertyEdit<T> implements FsPropertyEdit<T> {
	private Class<T> clazz;

	/**
	 * @param clazz
	 */
	public DefaultPropertyEdit(Class<T> clazz) {
		this.clazz = clazz;
	}

	/* (non-Javadoc)
	 * @see com.fbs.samp.sys.pub.fs.binder.FsPropertyEdit#toArray(java.lang.String)
	 */
	@Override
	public String[] toArray(String str) {
		String splitChar = FsConstant.DEFAULT_SPLIT_CHAR;
		FzsFSAnnotaion annot = AnnotationUtils.findAnnotation(clazz, FzsFSAnnotaion.class);
		if (annot != null) {
			splitChar = annot.value();
		}
		return StringUtils.splitPreserveAllTokens(str, splitChar);
	}

	/* (non-Javadoc)
	 * @see com.fbs.samp.sys.pub.fs.binder.FsPropertyEdit#toStr(java.lang.Object)
	 */
	@Override
	public String toStr(T t, String fieldName) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		String val = BeanUtils.getProperty(t, fieldName);
		return val == null ? "" : val;
		//throw new UnsupportedOperationException("方法未实现");
	}

}
