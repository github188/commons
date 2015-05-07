package com.common.utils;

/**
 * 有待整理
 * @Description: TODO
 * @ClassName: UserAgentUtil 
 * @author huangzy@gzjp.cn
 * @date 2015年4月29日 下午1:22:51
 */
public class UserAgentUtil {
	/**微信UA包含*/
	private static final String WX_UA_INCLUDE = "MicroMessenger";
	
	/**手厅UA包含*/
	private static final String UNICOMAPP_UA_INCLUDE = "unicom";
	
	/**手厅UA包含*/
	private static final String IPHONE_UA_INCLUDE = "iPhone";
	
	public static boolean isUAUseAnd(String testUA,String ...uas){
		boolean ret = true;
		for(String ua:uas){
			if(!isUA(testUA, ua)){
				ret = false;
				break;
			}
		}
		
		return ret;
	}
	
	public static boolean isUAUseOr(String testUA,String ...uas){
		boolean ret = false;
		for(String ua:uas){
			if(isUA(testUA, ua)){
				ret = true;
				break;
			}
		}
		
		return ret;
	}
	
	private static boolean isUA(String testUA,String ua){
		if(testUA==null) throw new IllegalArgumentException("需要测试的UA不能为空");
		return testUA.contains(ua);
	}
	
	public static boolean isWxUA(String testUA){
		return isUAUseAnd(testUA, WX_UA_INCLUDE);
	}
	
	public static boolean isUnicomAppUA(String testUA){
		return isUAUseAnd(testUA, UNICOMAPP_UA_INCLUDE);
	}
	
	public static boolean isIPhoneUA(String testUA){
		return isUAUseAnd(testUA, IPHONE_UA_INCLUDE);
	}
}
