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
package com.common.modules.poi.read;

import java.io.Closeable;

/**
 * @Package: com.fzs.samp.commons.poi
 * @ClassName: ExcelReader
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author: 黄忠英
 * @Create Date: 2016年10月12日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public interface ExcelReader<T> extends Closeable {
	public T next() throws Exception;

	public boolean hasNext();
}
