package myUtils.state.impl;

import myUtils.state.LoginReDirect;
import myUtils.state.LoginUnicomContext;
import myUtils.state.config.UrlConfig;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.gzjp.common.utils.ReDirectUtil;

public class LoginBeginState implements LoginReDirect {
	
	private static Logger logger = LoggerFactory.getLogger(LoginBeginState.class);
	private String phoneNum;
	
	@Override
	public void action(LoginUnicomContext context) throws Exception {
		
		String ticket = context.getRequest().getParameter("ticket");
		
		if(StringUtils.isNotBlank(ticket)){
			//联通AOP接口调用
			phoneNum = aopPhoneNum(ticket);
		}
		
		//已取得手机号,不需要登录
		if(StringUtils.isNotBlank(phoneNum)){
			reDirect(context);
			return;
		}
		logger.info(context.getRequest().getRemoteAddr()+" "
				+context.getRequest().getRequestedSessionId()
		+" can't get phone from aop ticket="+ticket);
		
		//判断客户端访问的用户UA。根据UA指定用户访问页面
		context.setAndDoAction(new CheckUAState());
	}
	
	private String aopPhoneNum(String ticket){
		String phoneNum = null;
		
		/*LoginUserInfoService loginUserInfoService = new LoginUserInfoService();

		AopUserLoginInfoRequest_REQ body = new AopUserLoginInfoRequest_REQ();
		body.setType("02");
		body.setQueryValue(ticket);
		
		AopUserLoginInfoRequest aopUserLoginInfoRequest = new AopUserLoginInfoRequest();
		aopUserLoginInfoRequest.setUserLoginInfoReq(body);

		try {
			AopUserLoginInfoResponse aopUserLoginInfoResponse = loginUserInfoService.invoke(aopUserLoginInfoRequest);
			
			phoneNum = aopUserLoginInfoResponse.getUserLoginInfoRes().getUserid();
			
			logger.info("aopUserLoginInfoRequest "+ticket+","+phoneNum);
			
			boolean isMobileNO = MobileCheckUtil.isMobileNO(phoneNum);
			if(!isMobileNO){
				phoneNum = null;
			}
		} catch (Exception e) {
			logger.info(ticket+","+phoneNum+","+e.toString());
			e.printStackTrace();
		}*/
		
		return phoneNum;
	}
	
	@Override
	public void reDirect(LoginUnicomContext context) throws Exception {
		String ticket = context.getRequest().getParameter("ticket");
		
		logger.info(context.getRequest().getRemoteAddr()+" "
				+context.getRequest().getRequestedSessionId()
		+" get phone from aop "+ticket+","+phoneNum
		+" redirect "+UrlConfig.ACTIVITY_IDX);
		
		ReDirectUtil.getInstance(context.getRequest(), context.getResponse()).toUrl(UrlConfig.ACTIVITY_IDX);
		
	}

	public String getPhoneNum() {
		return phoneNum;
	}
	
}
