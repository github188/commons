package myUtils.state.impl;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import myUtils.state.LoginState;
import myUtils.state.LoginUnicomContext;
import myUtils.state.vo.UrlVO;
import cn.gzjp.common.utils.ReDirectUtil;
import cn.gzjp.common.utils.TempletaReplaceUtil;

public class IPhoneUAState implements LoginState {

	@Override
	public void action(LoginUnicomContext context) throws Exception {
		if(isIPhoneAndUnicomUA(context.getRequest())){
			ReDirectUtil.getInstance(context.getRequest(), 
					context.getResponse()).toUrl(UrlVO.ACTIVITY_LOGIN_IDX);
			return;
		}
		
		String location = UrlVO.ACTIVITY_LOGIN_IDX;
		
		Map<String,String> param = new HashMap<String,String>();
		location = TempletaReplaceUtil.templetaReplace(location, param);
		
		ReDirectUtil.getInstance(context.getRequest(), 
				context.getResponse()).toUrl(location);
		
		//流程结束

	}
	
	private boolean isIPhoneAndUnicomUA(HttpServletRequest request){
		String ua = request.getHeader("user-agent");
		//用户使用手厅并且手机是IPhone
		return (ua.indexOf("unicom")>0)&&(ua.indexOf("iPhone")>0);
	}
}
