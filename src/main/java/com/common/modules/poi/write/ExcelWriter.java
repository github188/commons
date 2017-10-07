package com.common.modules.poi.write;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public interface ExcelWriter {
	/**
	 * 设置每列标题及该列类型
	 */
	public void addColumns(List<Column> cols);
	
	public void addColumn(Column cols);
	
	/**
	 * 设置数据内容
	 * @param data
	 */
	public void setData(List data);

	/**
	 * 创建工作薄
	 */
	public void build() throws Exception;
	
	/**
	 * 把工作薄输出到流
	 * @param output
	 */
	public void writeOut(OutputStream output)  throws IOException;

	/**
	 * Excel文件下载
	 * @param fileName
	 * @param response
	 * @throws IOException
	 */
	public void writeOut(String fileName, HttpServletResponse response) throws IOException;
}
