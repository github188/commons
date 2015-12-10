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

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.fzs.samp.commons.poi.ExcelVerType;
import com.fzs.samp.commons.poi.read.ExcelReader;
import com.fzs.samp.commons.poi.read.RowParse;

/**
 * @Package: com.fzs.samp.commons.poi.read
 * @ClassName: SimpleExcelReaderImple
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author: 黄忠英
 * @Create Date: 2016年10月13日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class SimpleExcelReaderImple<T> implements ExcelReader<T> {
	private Workbook wb;
	private Sheet sheet;

	private int totalRow;

	private int currRow;

	private RowParse<T> rowParse;

	//从第2行开始读Excel文件(第1行一般是表头)
	private static final int DEFAULT_START_ROW = 1;

	public SimpleExcelReaderImple(String filepath, Class<T> clazz) throws IOException {
		this(filepath, DEFAULT_START_ROW, clazz);
	}

	public SimpleExcelReaderImple(String filepath, int currRow, Class<T> clazz) throws IOException {
		this(new FileInputStream(filepath), ExcelVerType.getVerType(filepath), currRow, clazz);
	}

	public SimpleExcelReaderImple(String filepath, int currRow, RowParse<T> rowParse) throws IOException {
		this(new FileInputStream(filepath), ExcelVerType.getVerType(filepath), currRow, rowParse);
	}

	public SimpleExcelReaderImple(InputStream is, int extType, int currRow, Class<T> clazz) throws IOException {
		this(is, extType, currRow, new SimpleRowParseImpl<T>(clazz));
	}

	public SimpleExcelReaderImple(InputStream is, int extType, int currRow, RowParse<T> rowParse) throws IOException {
		if (extType == ExcelVerType.EXT_XLSX) {
			this.wb = new XSSFWorkbook(is);
		} else if (extType == ExcelVerType.EXT_XLS) {
			this.wb = new HSSFWorkbook(is);
		} else {
			throw new IllegalArgumentException("文件类型不正确,extType只能取" + ExcelVerType.EXT_XLSX + "(xlsx文件),或" + ExcelVerType.EXT_XLS + "(xls文件)");
		}
		this.currRow = currRow;

		this.sheet = wb.getSheetAt(0);
		this.totalRow = sheet.getLastRowNum();

		this.rowParse = rowParse;
	}

	/* (non-Javadoc)
	 * @see com.fzs.samp.commons.poi.read.ExcelReader#next()
	 */
	@Override
	public T next() throws Exception {
		Row row = sheet.getRow(currRow);
		T t = rowParse.parse(row);

		currRow++;
		return t;
	}

	/* (non-Javadoc)
	 * @see com.fzs.samp.commons.poi.read.ExcelReader#hasNext()
	 */
	@Override
	public boolean hasNext() {
		return currRow <= totalRow;
	}

	/* (non-Javadoc)
	 * @see java.io.Closeable#close()
	 */
	@Override
	public void close() throws IOException {
		if (wb != null) {
			wb.close();
		}
		wb = null;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#finalize()
	 */
	@Override
	protected void finalize() throws Throwable {
		this.clone();
		super.finalize();
	}

}
