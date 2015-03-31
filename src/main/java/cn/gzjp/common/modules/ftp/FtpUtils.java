package cn.gzjp.common.modules.ftp;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPFileFilter;
import org.apache.commons.net.ftp.FTPReply;

import cn.gzjp.common.utils.CloseUtil;

/**
 * FTP工具类
 * @author huangzy
 * 2014年7月10日
 */
public class FtpUtils implements Closeable{
	private FTPClient ftpClient = null;
	private boolean isLogin = false;
	public FtpUtils(String url,int port) throws SocketException, IOException{
		ftpClient = new FTPClient();
		ftpClient.connect(url);
		ftpClient.setDefaultPort(port);
		
		ftpClient.setControlEncoding("UTF8");
	}
	public FtpUtils(String url,int port,String user, String password) throws SocketException, IOException{
		this(url,port);
		login(user,password);
	}
	
	public boolean login(String user, String password) throws IOException{
		isLogin = ftpClient.login(user, password);
		int reply = ftpClient.getReplyCode();
		if (!FTPReply.isPositiveCompletion(reply)) {
			ftpClient.disconnect();
			isLogin = false;
        }
		return isLogin;
	}
	
	/**
	 * 改变当前目录
	 * @param dir
	 * @return
	 * @throws IOException
	 */
	public boolean changeWorkingDirectory(String dir) throws IOException{
		return ftpClient.changeWorkingDirectory(dir);
	}
	
	/**
	 * 获取当前的工作目录
	 * @return
	 * @throws IOException
	 */
	public String getCurrWorkingDirectory() throws IOException{
		return ftpClient.printWorkingDirectory();
	}
	
	/**
	 * 
	 * @param localFilePath 文件路径，如：F:\\Dev\\中国联通电子渠道认证能力共享平台对外接入手册v2.1.1.docx
	 * @return
	 * @throws IOException 
	 */
	public boolean upload(String localFilePath) throws IOException{
		File file = new File(localFilePath);
		String fileName = file.getName();
		FileInputStream fis = new FileInputStream(file);
		
		boolean isSuccess = upload(fis,fileName);
		
		CloseUtil.close(fis);
		
		return isSuccess;
	}
	
	/**
	 * 上传文件
	 * @param in
	 * @param newname
	 * @return
	 * @throws IOException 
	 */
	public boolean upload(InputStream in,String newname) throws IOException{
		ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE); 
		boolean ret = ftpClient.storeFile(newname, in);
		return ret;
	}
	
	public boolean uploadAppointedDir(String filepath,String remoteDir) throws IOException{
		if(StringUtils.isBlank(remoteDir)){
			throw new IllegalArgumentException("FTP远程文件不能为空!");
		}
		String ftpCurrDir = null;
		boolean result = false;
		try {
			ftpCurrDir = getCurrWorkingDirectory();
			if(!changeWorkingDirectory(remoteDir)){
				throw new RuntimeException("切换到远程目录失败!远程目录:"+remoteDir+"可能不存在!");
			}
			result = upload(filepath);
		}finally{
			try {
				changeWorkingDirectory(ftpCurrDir);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	/**
	 * 下载文件
	 * @param remoteFileName 远程文件名，如：中国联通电子渠道认证能力共享平台对外接入手册v2.1.1.docx
	 * @param localFilePath 保存到本地的完整路径，如：D:\\中国联通电子渠道认证能力共享平台对外接入手册v2.1.1.docx
	 * @return
	 * @throws IOException
	 */
	public boolean download(String remoteFileName,String localFilePath) throws IOException{
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(localFilePath);
			return download(remoteFileName, fos);
		}finally{
			CloseUtil.close(fos);
		}
		
	}
	
	public boolean download(String remoteFileName,File localFile) throws IOException{
		return this.download(remoteFileName, localFile.getAbsolutePath());
	}
	
	/**
	 * 下载文件
	 * @param remote
	 * @param out
	 * @return
	 * @throws IOException 
	 */
	public boolean download(String remote, OutputStream out) throws IOException 
	{ 
		ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
		boolean ret = ftpClient.retrieveFile(remote, out);  
		return ret;
	}
	
	public List<String> listFTPFile(String remoteDir) throws IOException{
		List<FTPFile> ftpFileList = listFTPFile(remoteDir,null);
		
		if(ftpFileList==null) return null;
		
		List<String> list = new ArrayList<String>();
		
		for(FTPFile ftpFile:ftpFileList){
			list.add(ftpFile.getName());
		}
		
		return list;
	}
	
	public List<FTPFile> listFTPFile(String remoteDir,FTPFileFilter filter) throws IOException{
		FTPFile[] ftpFileArr = null;
		if(filter!=null){
			ftpFileArr = ftpClient.listFiles(remoteDir, filter);
		}else{
			ftpFileArr = ftpClient.listFiles(remoteDir);
		}
		
		if(ftpFileArr==null) return null;
		List<FTPFile> list = new ArrayList<FTPFile>();
		Collections.addAll(list, ftpFileArr);
		
		return list;
	}
	
	public List<String> listFTPFile() throws IOException{
		return listFTPFile(getCurrWorkingDirectory());
	}
	
	public boolean delete(String remote) throws IOException{
		return ftpClient.deleteFile(remote);
	}
	
	public void deleteAll(String ...remotes) throws IOException{
		if(remotes!=null&&remotes.length>0){
			for(String remote:remotes){
				delete(remote);
			}
		}
	}
	
	public void deleteAll(List<String> remotes) throws IOException{
		deleteAll(remotes.toArray(new String[remotes.size()]));
	}
	
	/**
	 * 测试ftp是否还连接
	 * @return
	 */
	public boolean ping(){
		int reply = ftpClient.getReplyCode();
		if(FTPReply.SERVICE_CLOSING_CONTROL_CONNECTION == reply||!FTPReply.isPositiveCompletion(reply)) {  
			 try {
				ftpClient.disconnect();
			} catch (IOException e) {}
			 return false;
		}
		return true;
	}
	
	@Override
	public void close(){		
		try{
            if (ftpClient != null) {
            	ftpClient.logout();
            	ftpClient.disconnect();
            }
		}catch(Exception ex){}    
	}
	
	
	
	@Override
	protected void finalize() throws Throwable {
		this.close();
		super.finalize();
	}
	
	
	
}
