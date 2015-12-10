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

import org.apache.poi.ss.usermodel.Row;

/**
 * @Package: com.fzs.samp.commons.poi.read
 * @ClassName: RowParse
 * @Statement: <p>将一行excel数据转为对象</p>
 * @JDK version used: 
 * @Author: 黄忠英
 * @Create Date: 2016年10月13日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public interface RowParse<T> {
	public T parse(Row row) throws Exception;

	public <P> void registerCellParse(int cellIndex, CellParse<P> cellParse) throws Exception;
}
