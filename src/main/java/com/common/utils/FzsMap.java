/**
 * <html>
 * <body>
 *  <p>  All rights reserved.</p>
 *  <p> Created by 黄忠英</p>
 *  <p> Email:h419802957@foxmail.com
 *  </body>
 * </html>
 */
package com.common.utils;

import java.io.Reader;
import java.sql.Blob;
import java.sql.Clob;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.Assert;

/**
 * @Package: com.fzs.samp.commons
 * @ClassName: FzsMap
 * @Statement: 
 * <p>
 * 供mybatis的resultMap、resultType使用
 * 如:
 * <resultMap type="com.fzs.samp.commons.FzsMap" id="upResultMap" />
 * <select id="query" parameterType="xxxx" resultType="com.fzs.samp.commons.FzsMap">
 * 	select id,colunm from table
 * <\/select>
 * </p>
 * @JDK version used: 
 * @Author: 黄忠英
 * @Create Date: 2016年6月30日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class FzsMap extends HashMap<String, Object> {
	private static final ThreadLocal<Map<String, String>> aliaseThreadLocal = new ThreadLocal<Map<String, String>>();

	private static final ThreadLocal<SimpleDateFormat> dataFormatThreadLocal = new ThreadLocal<SimpleDateFormat>();

	private static final ThreadLocal<Conver> converThreadLocal = new ThreadLocal<Conver>();

	public FzsMap() {
		if (dataFormatThreadLocal.get() == null) {
			dataFormatThreadLocal.set(new SimpleDateFormat("yyyy-MM-dd"));
		}
		if (converThreadLocal.get() == null) {
			converThreadLocal.set(new Conver());
		}
	}

	@Override
	public Object put(String key, Object value) {
		//TODO 跳过ROW_ID
		if (StringUtils.equals("ROW_ID", key)) {
			return null;
		}
		String _key = converKey0(key);
		Object _value = converValue0(_key, value);
		return super.put(_key, _value);
	}

	public Object putNormal(String key, Object value) {
		return super.put(key, value);
	}

	private String converKey0(String key) {
		if (aliaseThreadLocal.get() != null && aliaseThreadLocal.get().containsKey(key.toLowerCase())) {
			return aliaseThreadLocal.get().get(key.toLowerCase());
		}

		return converThreadLocal.get().converKey(key);
	}

	private Object converValue0(String key, Object value) {
		if (value instanceof Date && dataFormatThreadLocal.get() != null) {
			return dataFormatThreadLocal.get().format(value);
		} else if (value instanceof Clob) {
			return getClobString((Clob) value);
		} else if (value instanceof Blob) {
			return getBlobString((Blob) value);
		}
		return converThreadLocal.get().converValue(key, value);
	}

	public String getClobString(Clob clob) {
		try {
			Reader reader = clob.getCharacterStream();
			if (reader == null) {
				return null;
			}
			StringBuffer sb = new StringBuffer();
			char[] charbuf = new char[4096];
			for (int i = reader.read(charbuf); i > 0; i = reader.read(charbuf)) {
				sb.append(charbuf, 0, i);
			}
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public String getBlobString(Blob blob) {
		try {
			if (blob.length() == 0) {
				return null;
			}
			byte[] bdata = blob.getBytes(1, (int) blob.length());
			return new String(bdata, "utf8");
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	//-------------------------------------------------------------------------
	public static void setDateFormat(SimpleDateFormat sdf) {
		dataFormatThreadLocal.set(sdf);
	}

	public static void setAliaseKey(String lowerCol, String newName) {
		Assert.notNull(lowerCol);
		Assert.notNull(newName);

		if (aliaseThreadLocal.get() == null) {
			aliaseThreadLocal.set(new HashMap<String, String>());
		}
		aliaseThreadLocal.get().put(lowerCol.toLowerCase(), newName);
	}

	public static void setConver(Conver conver) {
		converThreadLocal.set(conver);
	}

	//--使用线程池需要调用此方法清理上次调用留下的设置
	public static void cleanThreadLocal() {
		dataFormatThreadLocal.set(null);
		aliaseThreadLocal.set(new HashMap<String, String>());
		converThreadLocal.set(null);
	}

	//----------------------------------------------------------------------------
	public static class Conver {
		protected String converKey(String key) {
			StringBuilder _keyBuilder = new StringBuilder();
			String[] arr = key.toLowerCase().split("_");

			String tmp = arr[0];
			_keyBuilder.append(tmp.substring(0, 1).toLowerCase() + tmp.substring(1));
			if (arr.length > 1) {
				for (int i = 1; i < arr.length; i++) {
					tmp = arr[i];
					_keyBuilder.append(tmp.substring(0, 1).toUpperCase() + tmp.substring(1));
				}
			}
			return _keyBuilder.toString();
		}

		protected Object converValue(String key, Object value) {
			return value;
		}
	}
}