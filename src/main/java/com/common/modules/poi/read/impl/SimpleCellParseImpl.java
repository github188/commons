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
package com.common.modules.poi.read.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.util.NumberToTextConverter;

import com.fzs.samp.commons.poi.read.CellParse;

/**
 * @Package: com.fzs.samp.commons.poi.read.impl
 * @ClassName: SimpleCellParseImpl
 * @Statement: <p>单元格数据解析</p>
 * @JDK version used: 
 * @Author: 黄忠英
 * @Create Date: 2016年10月14日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class SimpleCellParseImpl implements CellParse<Object> {

	/* (non-Javadoc)
	 * @see com.fzs.samp.commons.poi.read.CellParse#parse(org.apache.poi.ss.usermodel.Cell)
	 */
	@Override
	public Object parse(Cell cell) throws Exception {
		if (cell == null) {
			return null;
		}
		Object result = null;
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_BOOLEAN:
			result = cell.getBooleanCellValue();
			break;
		case Cell.CELL_TYPE_NUMERIC:
			//先看是否是日期格式
			if (DateUtil.isCellDateFormatted(cell)) {
				/**
					yyyy-MM-dd-----	14
					yyyy年m月d日-----	31
					yyyy年m月-------	57
					m月d日  ----------	58
					HH:mm-----------20
					h时mm分  ---------32
				 */
				short format = cell.getCellStyle().getDataFormat();
				if (format == 20 || format == 32) {
					SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
					Date date = org.apache.poi.ss.usermodel.DateUtil.getJavaDate(cell.getNumericCellValue());
					result = sdf.format(date);
				} else {
					result = cell.getDateCellValue();
				}
			} else {
				result = NumberToTextConverter.toText(cell.getNumericCellValue());
			}
			break;
		case Cell.CELL_TYPE_FORMULA:
			try {
				result = String.valueOf(cell.getNumericCellValue());
			} catch (IllegalStateException e) {
				result = String.valueOf(cell.getRichStringCellValue());
			}
			break;
		case Cell.CELL_TYPE_STRING:
			result = cell.getRichStringCellValue().toString();
			break;
		}

		return result;
	}

}
