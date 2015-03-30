package cn.gzjp.common.utils;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;

/**
 * properties 文件读取工具
 * @Description: TODO
 * @ClassName: PropertiesCacheUtil 
 * @author huangzy@gzjp.cn
 * @date 2015年3月23日 上午10:21:21
 */
public class PropertiesCacheUtil{
	
	//默认刷新时间10s
	private static final Integer DEFAULT_REFRESH_DELAY = 10*1000;
	
	private static final String DEFAULT_ENCODING = "UTF8";
	
	private static final PropertiesCacheUtil propCacheUtil = new PropertiesCacheUtil();
	
	private ConcurrentMap<String, PropertiesConfiguration> propConfCacheMap = new ConcurrentHashMap<String, PropertiesConfiguration>();
	
	private Object lock = new Object();
	
	private PropertiesCacheUtil(){}
	
	public static PropertiesCacheUtil getInstance(){
		return propCacheUtil;
	}
	
	public PropertiesConfiguration getConfInstance(String filepath){
		return getConfInstance(filepath,DEFAULT_ENCODING, initReloadStrategy(DEFAULT_REFRESH_DELAY));
	}
	
	public PropertiesConfiguration getConfInstance(String filepath,String encoding,FileChangedReloadingStrategy reloadingStrategy){
		
		PropertiesConfiguration propConf = propConfCacheMap.get(filepath);
		
		if(propConf!=null){
			return propConf;
		}else{
			
			synchronized (lock) {
				propConf = propConfCacheMap.get(filepath);
				if(propConf==null){
					propConf = new PropertiesConfiguration();
					propConf.setEncoding(encoding);
					try {
						propConf.load(filepath);
					} catch (ConfigurationException e) {
						throw new RuntimeException(e);
					}
					
					propConf.setReloadingStrategy(reloadingStrategy);
					
					propConfCacheMap.put(filepath, propConf);
				}
			}
			
		}
		return propConf;
	}
	
	private FileChangedReloadingStrategy initReloadStrategy(Integer ms){
		FileChangedReloadingStrategy propsReload = new FileChangedReloadingStrategy();
		propsReload.setRefreshDelay(ms);
		
		return propsReload;
	}
}
