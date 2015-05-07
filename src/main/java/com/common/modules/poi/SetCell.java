package com.common.modules.poi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

/**
 * 
 * @Description: TODO
 * @ClassName: SetCell 
 * @author huangzy@gzjp.cn
 * @date 2014年10月30日 下午4:50:37
 */
public class SetCell {
	
	private final Map<HSSFWorkbook,Map<ColumnTypeEnum, HSSFCellStyle>> cacheCellStyle 
									= new ConcurrentHashMap<HSSFWorkbook,Map<ColumnTypeEnum, HSSFCellStyle>>();
	private final Map<HSSFWorkbook,Map<ColumnTypeEnum,Short>> cacheDataFormat 
									= new ConcurrentHashMap<HSSFWorkbook,Map<ColumnTypeEnum, Short>>();
	
	public void setVal(HSSFCell cell,Column col,Object valObj){
		ColumnTypeEnum type = col.getType();
		switch(type){
			case TYPE_INTEGER:
				String v = valObj.toString();
				//如0.00,只取0
				if(v.indexOf(".")>0){
					v = v.substring(0, v.indexOf("."));				
				}
				
				Integer valInt = Integer.parseInt(v);
    			cell.setCellValue(valInt);
				break;
			case TYPE_PRICE:
			case TYPE_DOUBLE:
				Double valDouble = Double.parseDouble(valObj.toString());
    			cell.setCellValue(valDouble);
				break;
			case TYPE_DATE:
    		case TYPE_DATETIME:
    			Date date = null;
    			if(valObj instanceof String){
    				try {
    					SimpleDateFormat sdf = new SimpleDateFormat(type.getFormat());
    					date = sdf.parse(valObj.toString());
					} catch (ParseException e) {
						e.printStackTrace();
					}
    			}else{
    				date = (Date)valObj;
    			}
    			
				cell.setCellValue(date);
			default:
				String valStr = col.renderer(valObj.toString());
				//格式化数据显示
				valStr = col.format(valStr);
				
				cell.setCellValue(valStr);
		}
	}
	
	public void setIfNullStyle(HSSFWorkbook workbook,Column col){
		if(col.getStyle() == null){
			HSSFCellStyle defaultCellStyle = getDefaultCellStyle(workbook, col);
			short fmt = getDataFormat(workbook, col);
			defaultCellStyle.setDataFormat(fmt);
			col.setStyle(defaultCellStyle);
		}
	}
	
	public short getDataFormat(HSSFWorkbook workbook,Column col){
		Map<ColumnTypeEnum, Short> cache = cacheDataFormat.get(workbook);
		if(cache!=null&&cache.containsKey(col.getType())){
			//System.out.println("SetCell getDataFormat use cache");
			return cache.get(col.getType());
		}
		
		//System.out.println("SetCell getDataFormat use new!!!");
		
		HSSFDataFormat format= workbook.createDataFormat();
		Short retShort = format.getFormat(col.getType().getFormat());

		Map<ColumnTypeEnum,Short> cacheMap = new HashMap<ColumnTypeEnum,Short>();
		cacheMap.put(col.getType(),retShort);
		cacheDataFormat.put(workbook,cacheMap);
		
		return retShort;
	}
	
	public HSSFCellStyle getDefaultCellStyle(HSSFWorkbook workbook,Column col){
		 Map<ColumnTypeEnum, HSSFCellStyle> cache = cacheCellStyle.get(workbook);
		if(cache!=null&&cache.containsKey(col.getType())){
			//System.out.println("SetCell getDefaultCellStyle use cache");
			return cache.get(col.getType());
		}
		
		System.out.println("SetCell getDefaultCellStyle use new!!!");
		
		HSSFFont font = workbook.createFont();
	    font.setFontName("宋体");
	    font.setColor(HSSFColor.BLACK.index);
	    
		HSSFCellStyle style = workbook.createCellStyle();
	    style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	    style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
	    //style.setFillForegroundColor(HSSFColor.WHITE.index);
	    //style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

	    // 设置边框
	    style.setBottomBorderColor(HSSFColor.BLACK.index);
	    style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
	    style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
	    style.setBorderRight(HSSFCellStyle.BORDER_THIN);
	    style.setBorderTop(HSSFCellStyle.BORDER_THIN);
	    style.setFont(font);
	    
	    Map<ColumnTypeEnum,HSSFCellStyle> cacheMap = new HashMap<ColumnTypeEnum, HSSFCellStyle>();
	    cacheMap.put(col.getType(), style);
	    
	    cacheCellStyle.put(workbook,cacheMap);
	    
		return style;
	}
	
	public HSSFCellStyle getDefaultHeadStyle(HSSFWorkbook workbook){
		HSSFFont font = workbook.createFont();
	    font.setFontName("宋体");
	    font.setBoldweight((short) 200);
	    font.setFontHeight((short) 250);
	    font.setColor(HSSFColor.WHITE.index);
	    
		HSSFCellStyle style = workbook.createCellStyle();
	    style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	    style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
	    style.setFillForegroundColor(HSSFColor.LIGHT_BLUE.index);
	    style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

	    // 设置边框
	    style.setBottomBorderColor(HSSFColor.BLACK.index);
	    style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
	    style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
	    style.setBorderRight(HSSFCellStyle.BORDER_THIN);
	    style.setBorderTop(HSSFCellStyle.BORDER_THIN);
	    style.setFont(font);
	    
	    return style;
	}
}
