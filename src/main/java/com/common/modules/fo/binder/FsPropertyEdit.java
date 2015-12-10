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

/**
 * @Package: com.fbs.samp.sys.pub.fs.binder
 * @ClassName: FsPropertyEdit
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author: 黄忠英
 * @Create Date: 2016年5月16日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public interface FsPropertyEdit<T> {
	/**
	 * 一行字符串,转为元数据数组。
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public Object[] toArray(String str) throws Exception;

	/**
	 * 将对象转成字符串
	 * (不建议覆写此方法,仅做预留。)
	 * @param t
	 * @return
	 */
	public String toStr(T t, String fieldName) throws Exception;
}
