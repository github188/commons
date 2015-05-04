package myUtils.state.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import myUtils.state.LoginReDirect;
import myUtils.state.LoginUnicomContext;
import myUtils.state.config.UrlConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.gzjp.common.utils.ReDirectUtil;
import cn.gzjp.common.utils.UserAgentUtil;

/**
 * 检查是否是客户端访问
 * @Description: TODO
 * @ClassName: CheckUAState 
 * @author huangzy@gzjp.cn
 * @date 2015年4月27日 下午3:50:02
 */
public class CheckUAState implements LoginReDirect{
	private static Logger logger = LoggerFactory.getLogger(CheckUAState.class);
	
	@Override
	public void action(LoginUnicomContext context) throws Exception {
		//非客户端访问
		if(!isUnicomAppUA(context.getRequest())){
			reDirect(context);
			return;
		}
		context.setAndDoAction(new IPhoneUAState());
	}
	
	private boolean isUnicomAppUA(HttpServletRequest request){
		String ua = request.getHeader("user-agent");
		
		return UserAgentUtil.isUnicomAppUA(ua);
	}

	@Override
	public void reDirect(LoginUnicomContext context) throws ServletException, IOException {
		logger.info(context.getRequest().getRemoteAddr()+" "
				+context.getRequest().getRequestedSessionId()
				+"is not UnicomAppUA  redirect  "+UrlConfig.ACTIVITY_LOGIN_IDX+",login");
		
		//活动自身登录页面activity.url.login=forward:/loverly/login
		ReDirectUtil.getInstance(context.getRequest(), 
				context.getResponse()).toUrl(UrlConfig.ACTIVITY_LOGIN_IDX);
		
	}
}
