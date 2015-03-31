package cn.gzjp.common.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class UnicomAESUtil {
	private static final String key = "8mfxt9siysjm84zr";
	private static final String iv = "chb84py3hjonw84p";
	
	private static final String algorithm = "AES";
	private static final String transformation = "AES/CBC/PKCS5Padding";
	
	public static String encrypt(String data) throws Exception {
        byte[] dataBytes = data.getBytes();
        
        byte[] encrypted = initCipher(Cipher.ENCRYPT_MODE).doFinal(dataBytes);

        return new BASE64Encoder().encode(encrypted);
    }
	
	public static String desEncrypt(String data) throws Exception {
		byte[] encrypted = new BASE64Decoder().decodeBuffer(data);

		
		byte[] original = initCipher(Cipher.DECRYPT_MODE).doFinal(encrypted);
		String originalString = new String(original);

		return originalString;
	}
	
	private static Cipher initCipher(int mode) throws Exception{
		SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), algorithm);
		IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());
		
		Cipher cipher = Cipher.getInstance(transformation);
		cipher.init(mode, keyspec, ivspec);
		
		return cipher;
	}

	public static void main(String[] args) throws Exception {
		String data = "6oEvFdd97xjNZoBSGv2gtSwdIpcs5i2FXHQ5QZWvZiryQdSkkc7/pxbe2M1eqeqT";
		System.out.println(desEncrypt(data));
		
		data = "{\"amount\":\"1.00\",\"mobile\":\"18664720000\"}";
		System.out.println(encrypt(data));
	}
}
