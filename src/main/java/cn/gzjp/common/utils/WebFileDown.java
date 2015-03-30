package cn.gzjp.common.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

/**
 * 浏览器文件下载
 * @Description: TODO
 * @ClassName: FileDown 
 * @author huangzy@gzjp.cn
 * @date 2015年2月27日 下午4:45:49
 */
public class WebFileDown {
	
	public static void download(String filepath,String filename,HttpServletResponse response) throws IOException{
		File file = new File(filepath);
		if(!file.isFile() || !file.exists()){
			throw new IOException("文件不存在或是个目录:"+filepath);
		}
		
		String _filename = filename;
		if(StringUtils.isBlank(_filename)){
			_filename = file.getName();
		}
		
		response.setContentType("APPLICATION/DOWNLOAD");
		response.setHeader("Content-Disposition", "attachment; filename="
				+ java.net.URLEncoder.encode(_filename, "UTF-8"));
		BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
		
        byte[] bytes = new byte[1024];
        BufferedInputStream bis = null;
        try {
        	bis = new BufferedInputStream(new FileInputStream(file));
            
            int length = bis.read(bytes);
            while (length != -1) {
            	out.write(bytes, 0, length);
                length = bis.read(bytes);
            }
           
            out.flush();
		}finally{
			if(bis!=null) bis.close();
		}
        
	}
	
}
