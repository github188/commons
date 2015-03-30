package cn.gzjp.common.utils;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 简单权限过滤器 辅助类
 * @Description: TODO
 * @ClassName: FilterHelper 
 * @author huangzy@gzjp.cn
 * @date 2015年3月30日 上午11:17:54
 */
public class FilterHelper {
	public static boolean filter(HttpServletRequest request,HttpServletResponse response,Vo vo) throws IOException{
		boolean ret = false;
		
		String ctxPath = request.getContextPath();
		
		String request_uri  = request.getRequestURI();
		String uri = request_uri.substring(ctxPath.length());
		
		if(vo.checkUrl(uri)&&vo.checkVal==null){
			
			if ((request.getHeader("accept").indexOf("application/json") > -1 
				  || 
					(request.getHeader("X-Requested-With") != null 
					&&request.getHeader("X-Requested-With").indexOf("XMLHttpRequest") > -1))
			) {
				response.getWriter().print(vo.jsonStr);
			} else {
				response.sendRedirect(ctxPath + vo.sendRedirectUrl);
			}
			
			ret = true;
		}
		
		return ret;
	}
	
	public static class Vo{
		//要拦截的url
		private List<String> filterUrls;
		//检查的值(一般从session取)
		private String checkVal;
		
		private String jsonStr;
		private String sendRedirectUrl;
		public Vo(List<String> filterUrls, String checkVal, String jsonStr,
				String sendRedirectUrl) {
			super();
			this.filterUrls = filterUrls;
			this.checkVal = checkVal;
			this.jsonStr = jsonStr;
			this.sendRedirectUrl = sendRedirectUrl;
		}
		
		public boolean checkUrl(String currUrl){
			if(filterUrls==null) return false;
			for(String url:filterUrls){
				if(currUrl.indexOf(url)>-1){
					return true;
				}
			}
			return false;
		}
	}
}
