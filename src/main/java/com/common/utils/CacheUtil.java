package com.common.utils;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;


/**
 * 缓存工具
 * @Description: TODO
 * @ClassName: CacheUtil 
 * @author huangzy@gzjp.cn
 * @date 2014年9月4日 上午11:39:31
 */
public class CacheUtil {
	private static final String defaultConfig = "/ehcache/ehcache_portal.xml";
	
	private Cache cache;
	private CacheManager manager;
	
	private CacheUtil(){
		this(defaultConfig);
	}
	private CacheUtil(String encacheConfig){
		this(encacheConfig,"_defaultCacheName");
	}
	
	private CacheUtil(String encacheConfig,String cacheName){
		manager = CacheManager.newInstance(CacheUtil.class.getResourceAsStream(encacheConfig));
		
		this.cache = manager.getCache(cacheName);
		if(this.cache==null){
			this.cache = new Cache(cacheName, 150, false, false, 120,60);
			manager.addCache(cache);
		}
	}
	
	private CacheUtil(String encacheConfig,Cache cache){
		this.cache = cache;
	}
	
	public static CacheUtil getInstance(){
		return new CacheUtil();
	}

	public static CacheUtil getInstance(String cacheName){
		return new CacheUtil(defaultConfig,cacheName);
	}
	
	public static CacheUtil getInstance(String encacheConfig,String cacheName){
		return new CacheUtil(encacheConfig,cacheName);
	}
	
	public void put(Object key,Object val){
		Element element = new Element(key,val);
		cache.put(element);
	}
	
	public void putIfAbsent(Object key,Object val){
		Element element = new Element(key,val);
		cache.putIfAbsent(element);
	}
	
	public <T> T get(Object key,Object defaultVal){
		T t = (T)defaultVal;
		Element element = cache.get(key);
		if(element!=null){
			t = (T)element.getObjectValue();
		}
		return t;
	}
	
	public <T> T get(Object key){
		return get(key,null);
	}
	
	public boolean remove(Object key){
		return cache.remove(key);
	}
	
	public synchronized void shutdown(){
		if(manager!=null)
			manager.shutdown();
		manager = null;
	}
	
	public void setCache(Cache cache) {
		this.cache = cache;
	}
	
}
