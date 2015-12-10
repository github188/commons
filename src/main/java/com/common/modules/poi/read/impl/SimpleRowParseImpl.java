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

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.core.annotation.AnnotationUtils;

import com.fzs.samp.commons.poi.read.CellParse;
import com.fzs.samp.commons.poi.read.FzsExcelAnnot;
import com.fzs.samp.commons.poi.read.FzsExcelVo;
import com.fzs.samp.commons.poi.read.RowParse;

/**
 * @Package: com.fzs.samp.commons.poi.read.impl
 * @ClassName: SimpleRowParseImpl
 * @Statement: <p>将一行excel 数据转为对象</p>
 * @JDK version used: 
 * @Author: 黄忠英
 * @Create Date: 2016年10月13日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class SimpleRowParseImpl<T> implements RowParse<T> {
	private Class<T> clazz;

	private Map<Integer, CellParse> cellParseMap = new HashMap<Integer, CellParse>();

	private static final Map<Class, FzsExcelVo> fzsExcelVoCache = new ConcurrentHashMap<Class, FzsExcelVo>();

	public SimpleRowParseImpl(Class<T> clazz) {
		this.clazz = clazz;

		this.init(clazz);
	}

	private void init(Class<T> clazz) {
		//不加锁
		if (fzsExcelVoCache.get(clazz) == null) {
			//获取可写属性(有set方法)
			List<String> fieldNameList = new ArrayList<String>();
			Field[] fieldArr = clazz.getDeclaredFields();
			for (int i = 0; i < fieldArr.length; i++) {
				Field field = fieldArr[i];
				String property = field.getName();
				try {
					//存在set方法
					clazz.getMethod("set" + property.substring(0, 1).toUpperCase() + property.substring(1), field.getType());
					fieldNameList.add(property);
				} catch (Exception e) {
				}
			}

			//初始化FzsExcelAnnot注解包含的数据
			int[] cellIndex;
			String[] fieldNameArr;
			FzsExcelAnnot fzsExcelAnnot = AnnotationUtils.findAnnotation(clazz, FzsExcelAnnot.class);
			if (fzsExcelAnnot == null) {
				cellIndex = new int[fieldNameList.size()];
				for (int i = 0; i < fieldNameList.size(); i++) {
					cellIndex[i] = i;
				}
				fieldNameArr = fieldNameList.toArray(new String[fieldNameList.size()]);

			} else {
				cellIndex = fzsExcelAnnot.cellIndex();
				if (cellIndex.length == 0) {
					for (int i = 0; i < fieldNameList.size(); i++) {
						cellIndex[i] = i;
					}
				}

				fieldNameArr = fzsExcelAnnot.fieldName();
				if (fieldNameArr.length == 0) {
					fieldNameArr = fieldNameList.toArray(new String[fieldNameList.size()]);
				}
			}

			fzsExcelVoCache.put(clazz, new FzsExcelVo(cellIndex, fieldNameArr));
		}
	}

	@Override
	public T parse(Row row) throws Exception {
		T t = clazz.newInstance();

		FzsExcelVo fzsExcelVo = fzsExcelVoCache.get(clazz);
		int[] cellIndex = fzsExcelVo.getCellIndex();
		String[] fieldNameArr = fzsExcelVo.getFieldName();
		for (int i = 0; i < cellIndex.length; i++) {
			int index = cellIndex[i];

			Object value = getCellParse(index).parse(row.getCell(index));
			String fieldName = fieldNameArr[i];

			BeanUtils.setProperty(t, fieldName, value);
		}
		return t;
	}

	public <P> void registerCellParse(int cellIndex, CellParse<P> cellParse) {
		cellParseMap.put(cellIndex, cellParse);
	}

	private CellParse getCellParse(int cellIndex) {
		CellParse cellParse = cellParseMap.get(cellIndex);
		if (cellParse == null) {
			cellParse = new SimpleCellParseImpl();
		}
		return cellParse;
	}
}
