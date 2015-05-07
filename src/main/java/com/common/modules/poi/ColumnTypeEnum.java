package com.common.modules.poi;

/**
 * 
 * @Description: TODO
 * @ClassName: ColumnTypeEnum 
 * @author huangzy@gzjp.cn
 * @date 2014年10月30日 下午1:23:59
 */
public enum ColumnTypeEnum {
	TYPE_STRING(""),TYPE_INTEGER("0"),TYPE_DOUBLE("0.00"),TYPE_PRICE("#,##0.00"),
		TYPE_DATE("yyyy-m-d"),TYPE_DATETIME("yyyy-m-d HH:mm:ss");
	
	private String format;
	
	private ColumnTypeEnum(String format){
		this.format = format;
	}

	public String getFormat() {
		return format;
	}
	
	@Override
	public String toString() {
		return this.format;
	}
}
