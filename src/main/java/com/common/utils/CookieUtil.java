package com.common.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {
	/**
	 * 检查cookie中是否存在某值
	 * @param cookies
	 * @param name
	 * @return
	 */
	public static boolean isExits(Cookie[] cookies,String name){
		boolean isExits = false;
		
		if(cookies==null){
			return isExits;
		}
		for(Cookie cookie:cookies){
			if(cookie.getName().equals(name)){
				isExits = true;
				break;
			}
		}
		return isExits;
	}
	
	public static String getValue(Cookie[] cookies,String name){
		String value = null;
		for(Cookie cookie:cookies){
			if(cookie.getName().equals(name)){
				value = cookie.getValue();
				break;
			}
		}
		return value;
	}
	
	
	public static void setValue(HttpServletResponse response,String key,String val){
		setValue(response, key, val, null,null);
	}
	
	public static void setValue(HttpServletResponse response,String key,String val,String path){
		setValue(response, key, val, path,null);
	}
	
	public static void setValue(HttpServletResponse response,String key,String val,Integer expiry){
		setValue(response, key, val, null,expiry);
	}
	
	public static void setValue(HttpServletResponse response,String key,String val,String path,Integer expiry){
		Cookie cookie = new Cookie(key,val);
		if(path!=null){
			cookie.setPath(path);
		}
		if(expiry!=null){
			cookie.setMaxAge(expiry);
		}
		response.addCookie(cookie);
	}
}
