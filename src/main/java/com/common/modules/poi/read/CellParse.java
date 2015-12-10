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

import org.apache.poi.ss.usermodel.Cell;

/**
 * @Package: com.fzs.samp.commons.poi.read
 * @ClassName: CellParse
 * @Statement: <p>单元格解析</p>
 * @JDK version used: 
 * @Author: 黄忠英
 * @Create Date: 2016年10月14日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public interface CellParse<T> {
	public T parse(Cell cell) throws Exception;
}
