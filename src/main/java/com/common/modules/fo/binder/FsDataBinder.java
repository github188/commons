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

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Package: com.fbs.samp.sys.pub.fs.binder
 * @ClassName: FsDataBinder
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author: 黄忠英
 * @Create Date: 2016年5月13日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class FsDataBinder {
	private static final Map<Class, FsPropertyEdit> propertyEditMap = new ConcurrentHashMap<Class, FsPropertyEdit>();

	public static <T> void registerCustomEdit(Class<T> clzz, FsPropertyEdit<T> fsPropertyEdit) {
		propertyEditMap.put(clzz, fsPropertyEdit);
	}

	public static <T> FsPropertyEdit<T> getPropertyEdit(Class<T> clazz) {
		FsPropertyEdit<T> fsPropertyEdit = propertyEditMap.get(clazz);

		fsPropertyEdit = ( fsPropertyEdit == null ) ? new DefaultPropertyEdit<T>(clazz) : fsPropertyEdit;

		return fsPropertyEdit;
	}

}
