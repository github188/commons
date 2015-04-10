package cn.gzjp.common.utils;

/**
 * 为字符串添加到指定长度
 * @Description: TODO
 * @ClassName: StringUtil 
 * @author huangzy@gzjp.cn
 * @date 2015年4月10日 下午5:17:29
 */
public class StringAppendUtil {

	public static String appendToStringLeft(String num,int i,String a){
		if(num.length()>=i){
			return num;
		}
		return appendToStringLeft(a+num,i,a);
	}
	public static String appendToStringRigth(String num,int i,String a){
		if(num.length()>=i){
			return num;
		}
		return appendToStringRigth(num+a,i,a);
	}
	
	/*public static void main(String[] args) {
		System.out.println(appendToStringRigth("20", 4, "0"));
		System.out.println(appendToStringLeft("020", 4, "0"));
		
		String randomCode = StringUtil.appendToStringRigth(RandomUtils.nextInt(99999)+"", 5, "0");
		System.out.println(randomCode);
	}*/
}