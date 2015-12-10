package com.common.modules.poi.write.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.fzs.samp.commons.poi.write.Column;
import com.fzs.samp.commons.poi.write.ExcelWriter;
import com.fzs.samp.commons.poi.write.SetCell;

/**
 * 
 * @Description: TODO
 * @ClassName: XLSExcelWriter 
 * @author huangzy
 * @date 2014年10月30日 下午4:50:20 
 */
public class XLSExcelWriterImpl implements ExcelWriter {

	private List<Column> cols = null;
	private List data;
	private HSSFWorkbook workbook;

	private SetCell setCell = new SetCell();

	public XLSExcelWriterImpl() {
		this.cols = new ArrayList<Column>();
	}

	public void addColumns(List<Column> cols) {
		this.cols.addAll(cols);
	}

	public void addColumn(Column col) {
		this.cols.add(col);
	}

	public void setData(List data) {
		this.data = data;
	}

	public void writeOut(OutputStream output) throws IOException {
		if (workbook != null) {
			workbook.write(output);
			output.flush();
			output.close();
		}
	}

	public void writeOut(String fileName, HttpServletResponse response) throws IOException {
		fileName = new String(fileName.getBytes("gb2312"), "iso-8859-1") + ".xls";

		response.setContentType("application/vnd.ms-excel;charset=gb2312");
		response.setHeader("Content-Disposition", "attachment; filename=" + fileName);

		writeOut(response.getOutputStream());
		response.getOutputStream().flush();
	}

	public void build() throws Exception {
		workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("sheet1");

		buildHead(sheet);
		buildData(sheet);
	}

	/**
	 * 创建标题
	 */
	private void buildHead(HSSFSheet sheet) {
		HSSFRow row = sheet.createRow(0);
		HSSFCell cell = null;
		HSSFCellStyle headStyle = setCell.getDefaultHeadStyle(workbook);
		Column col = null;
		for (int i = 0; i < cols.size(); i++) {
			col = cols.get(i);
			cell = row.createCell(i);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(col.getTitle());
			cell.setCellStyle(headStyle);
			sheet.setColumnWidth(i, col.getWidth() * 256);
		}

	}

	/**
	 * 创建内容行
	 * @throws Exception 
	 */
	private void buildData(HSSFSheet sheet) throws Exception {
		if (data == null)
			return;

		Object rowData = null;
		for (int j = 0; j < data.size(); j++) {
			rowData = data.get(j);
			HSSFRow row = sheet.createRow(j + 1);
			setOneRowVal(row, rowData);
		}

	}

	private void setOneRowVal(HSSFRow row, Object rowData) throws Exception {
		for (int i = 0; i < cols.size(); i++) {
			Column col = cols.get(i);

			HSSFCell cell = row.createCell(i);
			Object valObj = BeanUtils.getProperty(rowData, col.getField());

			//为col设置style
			setCell.setIfNullStyle(workbook, col);

			cell.setCellStyle(col.getStyle());

			if (valObj == null) {
				cell.setCellValue("");
				continue;
			}

			setCell.setVal(cell, col, valObj);
		}
	}

}
