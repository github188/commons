package com.common.utils;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

/**
 * 验证校验码
 * @Description: TODO
 * @ClassName: VerifyCaptchaCode 
 * @author huangzy@gzjp.cn
 * @date 2015年3月20日 下午5:05:51
 */
public class VerifyCode {
	
	public static boolean verify(String code,String sessionKey,HttpServletRequest request){
		return verify(code, sessionKey, true, request);
	}
	
	public static boolean verify(String code,String sessionKey,boolean remove,HttpServletRequest request){
		boolean ret = false;
		
		String sessionVal = (String)request.getSession().getAttribute(sessionKey);
		//使用后移除
		if(remove){
			request.getSession().removeAttribute(sessionKey);
		}
		if(StringUtils.isNotBlank(sessionVal) && StringUtils.isNotBlank(code)){
			if(sessionVal.equals(code)){
				ret = true;
			}
		}
		return ret;
	}
}
