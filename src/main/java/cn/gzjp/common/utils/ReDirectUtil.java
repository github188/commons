package cn.gzjp.common.utils;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 服务端跳转/重定向
 * @Description: TODO
 * @ClassName: ReDirectUtil 
 * @author huangzy@gzjp.cn
 * @date 2015年4月27日 下午1:57:14
 */
public class ReDirectUtil {
	public final static String SENDREDIRECT = "redirect";
	public final static String FORWARD = "forward";
	
	private HttpServletRequest request;
	private HttpServletResponse response;
	
	private ReDirectUtil(HttpServletRequest request,HttpServletResponse response){
		this.request = request;
		this.response = response;
	}
	
	public static ReDirectUtil getInstance(HttpServletRequest request,HttpServletResponse response){
		return new ReDirectUtil(request, response);
	}
	
	public void toUrl(String url) throws ServletException, IOException{
		if(url==null) throw new IllegalArgumentException("跳转url不能为空");
		
		String[] arr = url.split(":");
		if(arr.length!=2){
			forward(url);
			return;
		}
		
		String _type = arr[0];
		String _url = arr[1];
		if(_type.equalsIgnoreCase(SENDREDIRECT)){
			sendRedirect(_url);
		}else if(_type.equalsIgnoreCase(FORWARD)){
			forward(_url);
		}
	}
	
	private void forward(String path) throws ServletException, IOException{
		request.getRequestDispatcher(path).forward(request, response);
	}
	
	private void sendRedirect(String location) throws ServletException, IOException{
		response.sendRedirect(location);
	}
}
