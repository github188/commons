package myUtils.state.vo;

public class UrlVO {
	/**活动自有登录页面*/
	public static final String ACTIVITY_LOGIN_IDX = "forward:/login";
	/**活动入口页面*/
	public static final String ACTIVITY_IDX = "forward:/index";
	/**APP统一登录入口*/
	public static final String ACTIVITY_LOGIN_APP = "redirect:${aopUrl}${callBackUrl}";
}
