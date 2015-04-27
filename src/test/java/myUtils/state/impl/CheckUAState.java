package myUtils.state.impl;

import javax.servlet.http.HttpServletRequest;

import cn.gzjp.common.utils.ReDirectUtil;
import myUtils.state.LoginState;
import myUtils.state.LoginUnicomContext;
import myUtils.state.vo.UrlVO;

/**
 * 检查是否是客户端访问
 * @Description: TODO
 * @ClassName: CheckUAState 
 * @author huangzy@gzjp.cn
 * @date 2015年4月27日 下午3:50:02
 */
public class CheckUAState implements LoginState{

	@Override
	public void action(LoginUnicomContext context) throws Exception {
		//非客户端访问
		if(!isUnicomAppUA(context.getRequest())){
			ReDirectUtil.getInstance(context.getRequest(), 
					context.getResponse()).forward(UrlVO.ACTIVITY_LOGIN_IDX);
			return;
		}
		context.setAndDoAction(new IPhoneUAState());
	}
	
	private boolean isUnicomAppUA(HttpServletRequest request){
		String ua = request.getHeader("user-agent");
		return (ua.indexOf("unicom")>0);
	}
}
