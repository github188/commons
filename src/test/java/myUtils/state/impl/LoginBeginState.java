package myUtils.state.impl;

import myUtils.state.LoginState;
import myUtils.state.LoginUnicomContext;
import myUtils.state.vo.UrlVO;

import org.apache.commons.lang3.StringUtils;

import cn.gzjp.common.utils.ReDirectUtil;

public class LoginBeginState implements LoginState {

	@Override
	public void action(LoginUnicomContext context) throws Exception {
		
		//TODO此处添加联通AOP接口调用
		String phoneNum = "AOP_INVOKE_RET";
		
		//已取得手机号,不需要登录
		if(StringUtils.isNotBlank(phoneNum)){
			ReDirectUtil.getInstance(context.getRequest(), context.getResponse()).toUrl(UrlVO.ACTIVITY_IDX);
			return;
		}
		
		//转到客户端判断State
		context.setAndDoAction(new CheckUAState());
	}
	
}
