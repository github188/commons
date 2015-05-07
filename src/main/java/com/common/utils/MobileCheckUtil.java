package com.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public class MobileCheckUtil {
	private static final Pattern isMobilePat = Pattern.compile("^((13[0-9])|(15[0-9])|(18[0-9]))\\d{8}$");
	
	private static final Pattern isUnicomMobilePat = Pattern.compile("^(1(3[0-2]|5[56]|8[56])\\d{8},?)");
	
	public static boolean isMobileNO(String mobiles) {
		if(StringUtils.isBlank(mobiles)) return false;
		
		Matcher m = isMobilePat.matcher(mobiles);
		return m.matches();
	}
	
	public static boolean isUnicomMobileNo(String mobiles){
		if(StringUtils.isBlank(mobiles)) return false;
		
		Matcher m = isUnicomMobilePat.matcher(mobiles);
		return m.matches();
		
	}
	
	/*public static void main(String[] args) {
		System.out.println(isUnicomMobileNo("132503058588"));
	}*/
}
