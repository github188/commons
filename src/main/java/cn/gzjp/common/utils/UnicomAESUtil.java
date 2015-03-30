package cn.gzjp.common.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class UnicomAESUtil {
	private static final String key = "8mfxt9siysjm84zr";
	private static final String iv = "chb84py3hjonw84p";
	
	public static String encrypt(String data) throws Exception {
        byte[] dataBytes = data.getBytes();
        
        SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");//"算法/模式/补码方式"
        IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());//使用CBC模式，需要一个向量iv，可增加加密算法的强度
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, ivspec);
        byte[] encrypted = cipher.doFinal(dataBytes);

        return new BASE64Encoder().encode(encrypted);
    }
	
	public static String desEncrypt(String data) throws Exception {

		byte[] encrypted1 = new BASE64Decoder().decodeBuffer(data);

		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
		IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());

		cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);

		byte[] original = cipher.doFinal(encrypted1);
		String originalString = new String(original);

		return originalString;
	}

	public static void main(String[] args) throws Exception {
		String data = "6oEvFdd97xjNZoBSGv2gtSwdIpcs5i2FXHQ5QZWvZiryQdSkkc7/pxbe2M1eqeqT";
		System.out.println(desEncrypt(data));
		
		data = "{\"amount\":\"1.00\",\"mobile\":\"18664720000\"}";
		System.out.println(encrypt(data));
	}
}
