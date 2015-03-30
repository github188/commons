package cn.gzjp.common.modules.poi;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.CellStyle;


public class Column {
	private String title;
	private String field;  	//数据库字段名
	private ColumnTypeEnum type; 		// 单元格类型
	private int width;		//宽度为字符个数
	
	private Map<Object,Object> renderer;
	private String format;
	
	private CellStyle style;
	
	public Column(String title, String field, ColumnTypeEnum type, int width) {
		this.title = title;
		this.field = field;
		this.type = type;
		this.width = width;
	}
	
	public Column(String title, String field,ColumnTypeEnum type, int width,CellStyle style,String format) {
		this.title = title;
		this.field = field;
		this.type = type;
		this.width = width;
		this.style = style;
		
		this.format = format;
	}
	
	public Column() {
		super();
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public CellStyle getStyle() {
		return style;
	}
	public void setStyle(CellStyle style) {
		this.style = style;
	}
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}

	public ColumnTypeEnum getType() {
		return type;
	}

	public void setType(ColumnTypeEnum type) {
		this.type = type;
	}

	public Map<Object, Object> getRenderer() {
		return renderer;
	}

	public <T> T renderer(Object key){
		if(renderer==null||!renderer.containsKey(key)) return (T)key;
		return (T)renderer.get(key);
	}

	public void setRenderer(Map<Object, Object> renderer) {
		this.renderer = renderer;
	}

	public String format(String val){
		String _val = val;
		if(!StringUtils.isBlank(this.format)){
			_val = this.format.replaceAll("\\$\\{value\\}", val);
		}
		return _val;
	}

	public void setFormat(String format) {
		this.format = format;
	}
}
