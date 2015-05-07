package myUtils.state.config;

import org.apache.commons.configuration.PropertiesConfiguration;

import com.common.utils.PropertiesCacheUtil;

public class UrlConfig {
	private final static PropertiesConfiguration configuration = 
			PropertiesCacheUtil.getInstance().getConfInstance("activityConfig.properties");
	
	/**活动自有登录页面*/
	public static final String ACTIVITY_LOGIN_IDX;
	/**活动入口页面*/
	public static final String ACTIVITY_IDX;
	/**APP统一登录入口*/
	public static final String ACTIVITY_LOGIN_APP;
	
	static{
		ACTIVITY_LOGIN_IDX = configuration.getString("activity.url.login");
		ACTIVITY_IDX = configuration.getString("activity.url.idx");
		
		ACTIVITY_LOGIN_APP = configuration.getString("activity.unicomUrl");
	}
}
