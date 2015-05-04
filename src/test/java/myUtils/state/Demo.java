package myUtils.state;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import myUtils.state.impl.LoginBeginState;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public class Demo {
	/**
	 * 公共登录入口 (spring mvc)
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public void unicomBegin(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// return "/front/login";
		LoginUnicomContext context = new LoginUnicomContext(request, response);

		// 覆写LoginBeginState reDirect默认 方法
		LoginState beginState = new LoginBeginState() {
			@Override
			public void reDirect(LoginUnicomContext context) throws Exception {
				LoginBeginState state = (LoginBeginState) context.getState();
				//String fromChannel = (String) context.getRequest().getSession()	.getAttribute(ConstantVo.FROM_CHANNEL);
				//String phoneNum = state.getPhoneNum();
				//xxxService.setUserActive(phoneNum, fromChannel);

				//context.getRequest().getSession().setAttribute(ConstantVo.USER_SESSION_KEY, phoneNum);
				super.reDirect(context);
			}
		};
		
		context.setAndDoAction(beginState);

	}
}
