package com.common.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * HttpServletRequest 保持param
 * @Description: TODO
 * @ClassName: RequestKeepParam 
 * @author huangzy@gzjp.cn
 * @date 2015年4月16日 下午4:02:21
 */
public class RequestKeepParam {
	public static void keep(HttpServletRequest request,String ...paramNames){
		if(paramNames!=null&&paramNames.length>0){
			for(String param:paramNames){
				String tmp = request.getParameter(param);
				request.setAttribute(param, tmp);
			}
		}
	}
}
