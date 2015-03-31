package cn.gzjp.common.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 字符串替换工具
 * 如：我的名字是${name},性别${sex}....
 * 传入一个map,key-->name value-->huangzy,key-->sex value=男
 * 本工具替换后的字符串为：我的名字是huangzy,性别男...
 * @Description: TODO
 * @ClassName: StringReplaceUtil 
 * @author huangzy@gzjp.cn
 * @date 2014年11月19日 下午3:09:25
 */
public class TempletaReplaceUtil {
	public static String templetaReplace(String templeta,Map<String,String> param){
		if(templeta==null) return templeta;
		for(Map.Entry<String, String> entity:param.entrySet()){
			if(entity.getValue()!=null){
				templeta = templeta.replaceAll("\\$\\{"+entity.getKey()+"\\}", entity.getValue());
			}
		}
		
		return templeta;
	}
	
	public static void main(String[] args) {
		String msg = "${nick}，您好：<br/>您在广东联通用户研究小组网站所注册的账号已完成密码重置。<br/>请在此页面登录，http://localhost:8080/gd-share/home，登录名是：${email}，默认登录密码是：${passwd}。<br/>建议您登录后尽快修改默认密码，提高账户安全度。<br/><br/>广东联通用户研究小组网站请访问：http://localhost:8080/gd-share";
		
		Map<String,String> param = new HashMap<String,String>();
		param.put("nick", "忠英");
		param.put("email", "hzying19@xxx.com");
		param.put("passwd", "aaaaa");
		
		System.out.println(templetaReplace(msg, param));
	}
}
