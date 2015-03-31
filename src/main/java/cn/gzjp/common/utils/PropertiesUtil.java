package cn.gzjp.common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 优先加载 -test 文件
 * @Description: TODO
 * @ClassName: PropertiesUtil 
 * modify by huangzy@gzjp.cn
 * @date 2014年8月8日 下午1:36:29
 */
public final class PropertiesUtil {
	
	private PropertiesUtil() {}

	private Properties p;

	public Properties getP() {
		return p;
	}

	public void setP(Properties p) {
		this.p = p;
	}

	private static Map<String, PropertiesUtil> propertiesCache = new ConcurrentHashMap<String, PropertiesUtil>();

	/**
	 *若存在propath-test.properties 则优先加载.不存在,则加载propath.properties
	 * @param propath
	 * @return
	 */
	public static PropertiesUtil getProperties(String propath) {
		return getProperties0(propath);
	}
	
	private static PropertiesUtil getProperties0(String propath){
		if (propertiesCache.containsKey(propath)) {
			return propertiesCache.get(propath);
		} else {
			return cacheProperties(propath);
		}
	}
	
	
	public static void reloadProperties(String propath){
		cacheProperties(propath);
	}
	
	private static PropertiesUtil cacheProperties(String propath){
		PropertiesUtil pu = new PropertiesUtil();
		Properties p = new Properties();
		InputStreamReader isr = null;
		InputStream is = null;
		try {
			is = PropertiesUtil.class.getResourceAsStream(propath);
			isr = new InputStreamReader(is,"utf8");
			p.load(isr);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}finally{
			CloseUtil.close(is);
			CloseUtil.close(isr);
		}
		pu.setP(p);
		propertiesCache.put(propath, pu);
		
		return pu;
	}
	
	public String getString(String key ,String deValue){
		if (null != key && !key.trim().equals(""))
			return p.getProperty(key, deValue);
		return "";
	}
	public String getString(String key){
		if (null != key && !key.trim().equals(""))
			return p.getProperty(key);
		return "";
	}

	public Object getValue(String key, Object deObject) {
		if (null != key && !key.trim().equals(""))
			return null == p.get(key) ? deObject : p.get(key);
		return deObject;

	}

	public Object getValue(String key) {
		if (key == null) {
			return null;
		}

		return p.get(key);
	}
}
