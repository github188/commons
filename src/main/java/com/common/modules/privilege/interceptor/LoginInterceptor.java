/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015年4月28日</p>
 *  <p> Created by 黄忠英</p>
 *  </body>
 * </html>
 */
package com.common.modules.privilege.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import com.common.modules.privilege.entity.User;

/**
 * @Package: com.yxw.platform.privilege.interceptor
 * @ClassName: LoginInterceptor
 * @Statement: <p>登录验证拦截</p>
 * @JDK version used: 
 * @Author: 黄忠英
 * @Create Date: 2015年8月31日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {
	private static final Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.handler.HandlerInterceptorAdapter#preHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object)
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		HttpSession session = httpRequest.getSession(true);

		User user = (User) session.getAttribute(UserConstant.LOGINED_USER);
		if (user == null) {

			if (isAjaxRequest(httpRequest)) {
				httpResponse.setCharacterEncoding("UTF-8");
				httpResponse.sendError(HttpStatus.UNAUTHORIZED.value(), "您已经太长时间没有操作,请刷新页面");
			} else {
				httpRequest.getRequestDispatcher(UserConstant.LOGIN_URL).forward(httpRequest, httpResponse);
			}

			return false;
		}
		return true;
	}

	private boolean isAjaxRequest(HttpServletRequest request) {
		String requestType = request.getHeader("X-Requested-With");
		//目前/api下的路径都是ajax请求
		if ( ( requestType != null && requestType.equals("XMLHttpRequest") ) || ( request.getRequestURI().startsWith("/api") )) {
			return true;
		} else {
			return false;
		}

	}
}
