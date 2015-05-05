package myUtils.state.impl;

import javax.servlet.http.HttpServletRequest;

import myUtils.state.LoginReDirect;
import myUtils.state.LoginUnicomContext;
import myUtils.state.config.UrlConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.gzjp.common.utils.ReDirectUtil;
import cn.gzjp.common.utils.TempletaReplaceUtil;
import cn.gzjp.common.utils.UserAgentUtil;

public class IPhoneUAState implements LoginReDirect {
	
	private static Logger logger = LoggerFactory.getLogger(IPhoneUAState.class);

	@Override
	public void action(LoginUnicomContext context) throws Exception {
		
		//根据流程,如果是IPhone进入手厅,并且未取到手机号,则转到活动自身登录页面.
		if(isIPhoneAndUnicomUA(context.getRequest())){
			reDirect(context);
			return;
		}
		
		String location = getUnicomUrl(context);
		
		ReDirectUtil.getInstance(context.getRequest(),context.getResponse()).toUrl(location);
		
		//流程结束
	}
	
	@Override
	public void reDirect(LoginUnicomContext context) throws Exception {
		logger.info(context.getRequest().getRemoteAddr()+" "
				+context.getRequest().getRequestedSessionId()
		+" iphone user　redirect "+UrlConfig.ACTIVITY_LOGIN_IDX);
		
		ReDirectUtil.getInstance(context.getRequest(), 
				context.getResponse()).toUrl(UrlConfig.ACTIVITY_LOGIN_IDX);
		
	}
	
	//获取替换本机服务器IP后的 统一登录url
	private String getUnicomUrl(LoginUnicomContext context){
			
		String location = UrlConfig.ACTIVITY_LOGIN_APP;
		HttpServletRequest request = context.getRequest();
			
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
		location = TempletaReplaceUtil.templetaReplace(location, "basePath", basePath);
			
		logger.info(context.getRequest().getRemoteAddr()+" "
					+context.getRequest().getRequestedSessionId() +" not iphone user　redirect "+location);
			
		return location;
	}
	
	private boolean isIPhoneAndUnicomUA(HttpServletRequest request){
		String ua = request.getHeader("user-agent");
		//用户使用手厅并且手机是IPhone
		return UserAgentUtil.isUnicomAppUA(ua)&&UserAgentUtil.isIPhoneUA(ua);
	}
	
}
