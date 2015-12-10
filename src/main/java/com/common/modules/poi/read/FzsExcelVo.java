package com.common.modules.poi.read;


/**
 * @Package: com.fzs.samp.commons.poi.read
 * @ClassName: FzsExcelVo
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author: 黄忠英
 * @Create Date: 2016年10月13日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class FzsExcelVo {
	private int[] cellIndex;
	private String[] fieldName;

	/**
	 * @param cellIndex
	 * @param fieldName
	 * @param dateFormat
	 */
	public FzsExcelVo(int[] cellIndex, String[] fieldName) {
		super();
		this.cellIndex = cellIndex;
		this.fieldName = fieldName;
	}

	/**
	 * @return the cellIndex
	 */
	public int[] getCellIndex() {
		return cellIndex;
	}

	/**
	 * @return the fieldName
	 */
	public String[] getFieldName() {
		return fieldName;
	}

}