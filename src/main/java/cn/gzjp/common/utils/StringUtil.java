package cn.gzjp.common.utils;

public class StringUtil {

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