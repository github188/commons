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
package com.common.modules.fo.rw;

import java.io.Closeable;

/**
 * @Package: com.fbs.samp.sys.pub.fs.conver
 * @ClassName: F2SResultIface
 * @Statement: <p>一行行的读文件</p>
 * @JDK version used: 
 * @Author: 黄忠英
 * @Create Date: 2016年4月28日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public interface F2SResultIface extends Closeable {

	public boolean hasNext() throws Exception;

	public String next() throws Exception;
}
