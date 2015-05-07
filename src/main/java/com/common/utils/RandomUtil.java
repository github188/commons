package com.common.utils;

import java.util.Random;

public class RandomUtil {
	private static final String allChar = "23456789abcdefghjkmnpqrstuvwxyABCDEFGHJKLMNPQRSTUVWXY";
	private static final String numberChar = "0123456789";
	
	private static final String chineseChar = "人是为生存而活着而生存的意义绝不单单是吃饱肚子延续生命的长度在纷繁的尘世间生活赋予人以很多的职责为自己为他人为社会这是作为一个自然人的义务面对繁芜的生活我们的生命也许做不到精彩但一定要活的充实快乐既然选择了生命就要设法活出生命的质感";
	
	private static final Random random = new Random();
	
	public static String RandomString(String charStr,int length) {  
	    StringBuffer buf = new StringBuffer();  
	    for (int i = 0; i < length; i++) {  
	        int num = random.nextInt(charStr.length());  
	        buf.append(charStr.charAt(num));  
	    }  
	    return buf.toString();  
	}
	
	public static String RandomString(int length) {  
	    return RandomString(allChar, length);
	}
	
	public static String RandomNum(int length) {  
	    return RandomString(numberChar, length);
	}
	
	public static String RandomChinese(int length){
		 return RandomString(chineseChar, length);
	}

	public static void main(String[] args) {
		for(int i=0;i<15;i++){
			System.out.println(RandomChinese(4));
		}
	}
}