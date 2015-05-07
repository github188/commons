package com.common.utils;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileUtil {
	private static final Logger log = LoggerFactory.getLogger(FileUtil.class);
	private static FileUtil instance;
	final static public String LS = System.getProperty("line.separator", "\n");
	final static public String FS = System.getProperty("file.separator", "\\");
	private FileUtil() {
	}

	public static synchronized FileUtil getSingletonInstance() {
		if (instance == null) {
			instance = new FileUtil();
		}
		return instance;
	}
	
	/**
	 * 
	* 方法用途和描述: 移动文件(支持跨系统)
	* @param fold
	* @param fnew
	* @return
	* @author zhangjh 新增日期：2011-1-4
	* @author 你的姓名 修改日期：2011-1-4
	* @since zte_crbt_bi
	 */
	public static boolean moveFile(File fold,File fnew){
		boolean isSuccess = false;
		try{
		isSuccess = fold.renameTo(fnew);
		if(!isSuccess){
			isSuccess = mvFile(fold.getPath(),fnew.getPath());
		}
		}catch(Exception e){
			log.warn("移动文件失败",e);
		}
		return isSuccess;
	}
	
	/**
	 * 
	* 方法用途和描述: linux下移动文件方法(java移动文件失败后备用)
	* @param srcFile
	* @param descDir
	* @return
	* @author zhangjh 新增日期：2010-8-27
	* @author 你的姓名 修改日期：2010-8-27
	* @since zte_crbt_bi
	 */
	public static boolean mvFile(String srcFile, String descDir) {
		InputStreamReader ir = null;
		LineNumberReader input = null;
		String command = "";
		try {
			if("/".equals(File.separator)){
				command = "mv ";
			}else{
				return false;
			}
			Process process = Runtime.getRuntime().exec(
					command+srcFile+" "+descDir);
			
			ir = new InputStreamReader(process.getInputStream());
			input = new LineNumberReader(ir);
			String line;
			while ((line = input.readLine()) != null) {
				log.info("Runtime:" + line);
			}
		} catch (IOException ioe) {
			log.error("",ioe);
			return false;
		} finally {
			try {
				if (ir != null) {
					ir.close();
				}
				if (input != null) {
					input.close();
				}
			} catch (IOException e) {
				log.error(command+srcFile+" "+descDir,e);
			}
		}
		return true;
	}

	/**
	 * 
	* 方法用途和描述: 检查文件夹是否以字符结束和创建该文件夹
	* @param path
	* @return
	* @author zhangjh 新增日期：2011-3-1
	* @author 你的姓名 修改日期：2011-3-1
	* @since zte_crbt_bi
	 */
	@SuppressWarnings("static-access")
	public static String checkDirectory(String path){
		File file = new File(path);
		if(!path.endsWith(file.separator)){
			path+=file.separator;
		}
		if(!file.isDirectory()){
			file.delete();
			file.mkdir();
		}
		return path;
	}

	public static final boolean copyFile(final String sSource,
			final String sDest, final String srcEncoding,
			final String destEncoding) {
		try {
			File src = new File(sSource);
			if (!src.exists()) {
				return false;
			}
			File dest = new File(sDest);
			String reuslt = readFile(src, srcEncoding);
			write(dest, reuslt, destEncoding);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
    public static boolean changeFileEncode(final String sSource,
			final String sDest, final String srcEncoding,
			final String destEncoding)
    {  	
    	boolean result = false;
    	try
    	{
			InputStream in = new BufferedInputStream(new FileInputStream(sSource));
			InputStreamReader isr = new InputStreamReader(in,srcEncoding);
			BufferedReader buf = new BufferedReader(isr);	    
	    	String str = null;
	    	FileOutputStream fos = new FileOutputStream(sDest);
	    	Writer out = new OutputStreamWriter(fos,destEncoding);
	    	
	    	while((str = buf.readLine()) != null)
	    	{    
	    		if(str.length()>0)
	    		{
	    			out.write(str+LS);
	    		}
	    		
	    	}
	    	buf.close();
	    	out.close();
	    	result = true;
    	}
    	catch(IOException e)
    	{
    		log.error("",e);
    	}
    	return result;
    }
    public static boolean changeBillLogFile(final String sSource,
			final String sDest, final String srcEncoding,
			final String destEncoding)
    {  	
    	boolean result = false;
    	try
    	{
			InputStream in = new BufferedInputStream(new FileInputStream(sSource));
			InputStreamReader isr = new InputStreamReader(in,srcEncoding);
			BufferedReader buf = new BufferedReader(isr);	    
	    	String str = null;
	    	FileOutputStream fos = new FileOutputStream(sDest);
	    	Writer out = new OutputStreamWriter(fos,destEncoding);
	    	int i=1;
	    	while((str = buf.readLine()) != null)
	    	{    
	    		if(str.length()>0&&i!=1)
	    		{	    	
	    			String spcode = "0" ;   		
	    			//System.out.println(str.substring(46, 51).trim());
	    			if(!str.substring(46, 51).trim().equals(""))
	    			{
	    				spcode =str.substring(46, 51).trim();
	    			}
	    			if(spcode.matches("[0-9]+")){
	    			String write_str = str.substring(0,30).trim()+"|"//
	    			+str.substring(30, 32).trim()+"|"//
	    			+str.substring(32, 33).trim()+"|"//
	    			+str.substring(33, 46).trim()+"|"//
	    			+Long.parseLong(spcode)+"|"//
	    			+str.substring(51, 56).trim()+"|"//	    			
	    			+str.substring(56, 69).trim()+"|"//
	    			+str.substring(69, 89).trim()+"|"//
	    			+str.substring(89, 99).trim()+"|"//
	    			+str.substring(100, 101).trim()+"|"//
	    			+str.substring(101, 107).trim()+"|"//
	    			+str.substring(107, 113).trim()+"|"//
	    			+str.substring(113, 118).trim()+"|"//
	    			+str.substring(119, 120).trim()+"|"//
	    			+str.substring(120, 121).trim()+"|"//
	    			+str.substring(121, 124).trim()+"|"//
	    			+str.substring(124, 125).trim()+"|"//
	    			+str.substring(125, 126).trim()+"|"//
	    			+str.substring(127, 131).trim()+"|"//
	    			+str.substring(131, 136).trim()+"|"//
	    			+str.substring(136, 141).trim()+"|"//
	    			+str.substring(141, 154).trim()+"|"//
	    			+str.substring(154, 168).trim()+"|"//
	    			+str.substring(168, 182).trim()
	    			;	    			
	    			out.write(write_str+LS);
	    			
	    			}else{
	    				log.info("this file "+sSource+" has error pattern");
	    			}
	    		}
	    		i++;
	    	}
	    	buf.close();
	    	out.close();
	    	result = true;
    	}
    	catch(IOException e)
    	{
    		log.error("",e);
    	}
    	return result;
    }
	public static void write(File file, String context, String coding)
			throws IOException {
		write(file, context.getBytes(coding), false);
	}

	public static void write(File file, byte[] bytes, boolean append)
			throws IOException {
		write(file, new ByteArrayInputStream(bytes), append);
	}

	public static void checkFile(File file) throws IOException {
		boolean exists = file.exists();
		if (exists && !file.isFile()) {
			throw new IOException("File " + file.getPath()
					+ " is actually not a file.");
		}
	}
    
	public static void makedirs(File file) throws IOException {
		checkFile(file);
		File parentFile = file.getParentFile();
		if (parentFile != null) {
			if (!parentFile.exists() && !parentFile.mkdirs()) {
				throw new IOException("Creating directories "
						+ parentFile.getPath() + " failed.");
			}
		}
	}

	public static void write(File file, InputStream input, boolean append)
			throws IOException {
		makedirs(file);
		BufferedOutputStream output = null;
		try {
			int contentLength = input.available();
			output = new BufferedOutputStream(
					new FileOutputStream(file, append));
			while (contentLength-- > 0) {
				output.write(input.read());
			}
		} finally {
			close(input, file);
			close(output, file);
		}
	}

	public static void close(InputStream input, File file) {
		if (input != null) {
			try {
				input.close();
				input = null;
			} catch (IOException e) {

			}
		}
	}

	public static void close(OutputStream output, File file) {
		if (output != null) {
			try {
				output.close();
				output = null;
			} catch (IOException e) {
			}
		}
	}

	public static void close(Reader reader, File file) {
		if (reader != null) {
			try {
				reader.close();
			} catch (IOException e) {
			}
		}
	}

	public static String readFile(File file, String encode) throws IOException {
		BufferedReader reader = null;
		InputStream in = null;
		try {
			in = new BufferedInputStream(new FileInputStream(file));
			InputStreamReader isr = new InputStreamReader(in, encode);
			reader = new BufferedReader(isr);
			StringBuffer sbr = new StringBuffer();
			for (String line = ""; (line = reader.readLine()) != null;) {
				sbr.append(line + LS);
			}
			return sbr.toString();
		} finally {
			close(in, file);
			close(reader, file);
		}
	}

	public static ArrayList getAllFiles(String path, String ext)
			throws IOException {
		File file = new File(path);
		ArrayList ret = new ArrayList(20);
		String[] exts = ext.split(",");
		String[] listFile = file.list();
		if (listFile != null) {
			for (int i = 0; i < listFile.length; i++) {
				File tempfile = new File(path + FS + listFile[i]);
				if (tempfile.isDirectory()) {
					ArrayList arr = getAllFiles(tempfile.getPath(), ext);
					ret.addAll(arr);
					arr.clear();
					arr = null;
				} else {
					for (int j = 0; j < exts.length; j++) {
						if (getExtension(tempfile.getAbsolutePath())
								.equalsIgnoreCase(exts[j])) {
							ret.add(tempfile.getAbsolutePath());
						}
					}
				}
			}
		}
		return ret;
	}

	public static String getExtension(String name) {
		if (name == null) {
			return "";
		}
		int index = name.lastIndexOf(".");
		if (index == -1) {
			return "";
		} else {
			return name.substring(index + 1);
		}
	}

	

	public static String getSpecialString(String context, String encoding) {
		try {
			ByteArrayInputStream in = new ByteArrayInputStream(context
					.getBytes());
			InputStreamReader isr = new InputStreamReader(in, encoding);
			BufferedReader reader = new BufferedReader(isr);
			StringBuffer buffer = new StringBuffer();
			String result;
			while ((result = reader.readLine()) != null) {
				buffer.append(result);
			}
			return buffer.toString();
		} catch (Exception ex) {
			return context;
		}
	}

	public static Properties loadProperties(File file) {
		if (file == null || !file.exists()) {
			return null;
		}
		Properties properties = new Properties();
		try {
			InputStream in = new BufferedInputStream(new FileInputStream(file));
			properties.load(in);
			close(in, file);
		} catch (IOException e) {
		}
		return properties;
	}

	public static boolean isChinaLanguage(String str) {
		char[] chars = str.toCharArray();
		int[] ints = new int[2];
		boolean isChinese = false;
		int length = chars.length;
		byte[] bytes = null;
		for (int i = 0; i < length; i++) {
			bytes = ("" + chars[i]).getBytes();
			if (bytes.length == 2) {
				ints[0] = bytes[0] & 0xff;
				ints[1] = bytes[1] & 0xff;
				if (ints[0] >= 0x81 && ints[0] <= 0xFE && ints[1] >= 0x40
						&& ints[1] <= 0xFE) {
					isChinese = true;
				}
			} else {
				return false;
			}
		}
		return isChinese;
	}

	
	/**
	 * 方法用途和描述: 测试移动文件的方法
	 * @param args
	 * @author zhangjh 新增日期：2011-1-4
	 * @author 你的姓名 修改日期：2011-1-4
	 * @throws IOException 
	 * @since zte_crbt_bi
	 */
	public static void main(String[] args) throws IOException {
		String ss="ddsd";
		if(ss.matches("[0-9]+")){
			System.out.println("1");
		}else{
			System.out.println("0");
		}
		//FileUtil ss = new FileUtil();
//		File fold = new File("D:\\sa.txt");
//		System.out.println(fold.getPath());
		//String sss ="123456789";
		//System.out.println(sss.substring(3, 4));
		//ss.changeBillLogFile("c:\\1\\CDR20121229087.020", "c:\\2\\CDR20121229087.020", "GBK", "UTF-8");
//		InputStream in = new BufferedInputStream(new FileInputStream("c:\\1\\CDR20121229087.020"));
//		InputStreamReader isr = new InputStreamReader(in,"GBK");
//		BufferedReader buf = new BufferedReader(isr);	    
//    	String str = null;
//    	//FileOutputStream fos = new FileOutputStream(sDest);
//    	//Writer out = new OutputStreamWriter(fos,destEncoding);
//    	int i = 1;
//    	while((str = buf.readLine()) != null)
//    	{    
//    		if(str.length()>0&&i>1&&i<4)
//    		{
//    			
//    			
////    			System.out.println("1:命令序列号:"+str.substring(0,30).replace(" ", ""));
////    			System.out.println("2:短消息话单类型:"+str.substring(30, 32).replace(" ", ""));
////    			System.out.println("3:用户类型:"+str.substring(32, 33).replace(" ", ""));
////    			System.out.println("4:计费用户号码:"+str.substring(33, 46).replace(" ", ""));
////    			System.out.println("5:SP代码:"+str.substring(46, 51).replace(" ", ""));
////    			System.out.println("6:SP所属SMG代码:"+str.substring(51, 56).replace(" ", ""));
////    			System.out.println("7:被叫号码:"+str.substring(56, 69).replace(" ", ""));
////    			System.out.println("8:接入代码:"+str.substring(69, 89).replace(" ", ""));
////    			System.out.println("9:业务代码:"+str.substring(89, 99).replace(" ", ""));
////    			System.out.println("10:用户计费类别:"+str.substring(100, 101).replace(" ", ""));
////    			System.out.println("11:信息费:"+str.substring(101, 107).replace(" ", ""));
////    			System.out.println("12:包月费:"+str.substring(107, 113).replace(" ", ""));
////    			System.out.println("13:赠送话费:"+str.substring(113, 119).replace(" ", ""));
////    			System.out.println("14:代收费标志:"+str.substring(119, 120).replace(" ", ""));
////    			System.out.println("15:MO-MT对应标志:"+str.substring(120, 121).replace(" ", ""));
////    			System.out.println("16:短消息发送状态:"+str.substring(121, 124).replace(" ", ""));
////    			System.out.println("17:短消息发送优先级:"+str.substring(124, 125).replace(" ", ""));
////    			System.out.println("18:信息条数:"+str.substring(125, 126).replace(" ", ""));
////    			System.out.println("19:计费用户号码归属地:"+str.substring(127, 131).replace(" ", ""));    			
////    			System.out.println("20:网关代码:"+str.substring(131, 136).replace(" ", ""));
////    			System.out.println("21:关联网关代码:"+str.substring(136, 141).replace(" ", ""));
////    			System.out.println("22:短消息中心代码:"+str.substring(141, 154).replace(" ", ""));
////    			System.out.println("23:申请时间:"+str.substring(154, 168).replace(" ", ""));
////    			System.out.println("24:处理结束时间:"+str.substring(168, 182).replace(" ", ""));   				
//    			//System.out.println(billog.length);
////    			for(String stra:billog)
////    			{
////    				System.out.println(stra);
////    			}
//    			//break;
//    		}
//    		i++;
//    		
//    	}
//    	buf.close();
    	
	}

}

