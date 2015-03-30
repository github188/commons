package cn.gzjp.common.utils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 * 浏览器文件上传工具类
 * (必需与springMvc结合:
 * 	spring-mvc 文件中定义 <bean id="multipartResolver"
 * 			class="org.springframework.web.multipart.commons.CommonsMultipartResolver" />
 *  注意bean必需是multipartResolver,class可为任何MultipartResolver子类;
 *  详情可参考org.springframework.web.servlet.DispatcherServlet.doDispatch(HttpServletRequest request, HttpServletResponse response);
 * )
 * @Description: TODO
 * @ClassName: FileUploadUtil 
 * @author huangzy@gzjp.cn
 * @date 2015年2月26日 下午5:12:00
 * 
 * modify @date 2015年3月3日 上午10:12:00 by huangzy@gzjp.cn
 */
public class WebFileSpringUpload {
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
	
	/**
	 * 上传单个文件
	 * @param request
	 * @param fieldName
	 * @param savaDir
	 * @param mark
	 * @return
	 * @throws IOException
	 */
	public static File upload(HttpServletRequest request,String fieldName,String savaDir,String mark) throws IOException{
		Map<String,File> result = upload(request, savaDir, mark);
		
		return result.get(fieldName);
	}
	
	/**
	 * 获取一个文件的相对路径
	 * @param filepath
	 * @param savaDir
	 * @return
	 */
	public static String getWebRelativePath(String filepath,String savaDir){
		return filepath.substring(filepath.indexOf(savaDir));
	}
	
	/**
	 * 上传批量文件
	 * @param request
	 * @param savaDir
	 * @param mark
	 * @return key为前端input name,value为保存到本地的文件相对路径.
	 * @throws IOException
	 */
	public static Map<String,File> upload(HttpServletRequest request,String savaDir,String mark) throws IOException{
		Map<String,File> result = null;
		
		// 应用目录路径
		String tempUploadName = sdf.format(new Date());
					
		String curWebAppRoot = request.getSession().getServletContext().getRealPath(savaDir);
		String outPutFilePath = curWebAppRoot + File.separator + mark + "_"	+ tempUploadName;
		
		if (request instanceof MultipartHttpServletRequest) {
			result = new HashMap<String, File>();
			
			MultipartHttpServletRequest multRequest = (MultipartHttpServletRequest) request;
			Map<String, MultipartFile> fileMap = multRequest.getFileMap();
			
			for(Map.Entry<String,MultipartFile> entity: fileMap.entrySet()){
				// 上传文件名  
	            //System.out.println("key: " + entity.getKey());  
	            MultipartFile mf = entity.getValue();  
	            
	            if (!mf.isEmpty()) {
	    			String extension = FilenameUtils.getExtension(mf.getOriginalFilename());
	    			
	    			File outPutFile = new File(outPutFilePath + "." + extension);
	    			try {
	    				FileUtils.writeByteArrayToFile(outPutFile, mf.getBytes());
	    				
	    				result.put(mf.getName(), outPutFile);
	    			} catch (IOException e) {
	    				delAllUploadedFile(result.values());
	    				throw e;
	    			}
	    			
	    		}
	            
			}
		}
		
		return result;
	}
	
	//删除之前上传成功的文件
	private static void delAllUploadedFile(Collection<File> fileList){
		if(fileList==null) return;
		for(File file:fileList){
			FileUtils.deleteQuietly(file);
		}
		
	}
}
