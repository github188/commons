package cn.gzjp.common.modules.ftp;

import java.io.File;
import java.io.IOException;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPFileFilter;

import cn.gzjp.common.utils.CloseUtil;

public class FtpDownloadUtil{
	private FtpUtils ftpUtils = null;
	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());
	
	public FtpDownloadUtil(String url,int port) throws SocketException, IOException{
		ftpUtils = new FtpUtils(url, port);
	}
	
	public FtpDownloadUtil(String url,int port,String user, String password) throws SocketException, IOException{
		ftpUtils = new FtpUtils(url, port, user, password);
	}
	
	public boolean login(String user, String password) throws IOException{
		return ftpUtils.login(user, password);
	}
	
	
	public List<String> downloadFile(String ftp_dir,String local_dir,final String includeName) throws Exception{
		FTPFileFilter filter = new FTPFileFilter() {
			
			@Override
			public boolean accept(FTPFile file) {
				return file.getName().indexOf(includeName)!=-1;
			}
		};
		
		return downloadFile(ftp_dir, local_dir, filter);
	}
	
	public List<String> downloadFile(String ftp_dir,String local_dir,FTPFileFilter filter) throws Exception{
		List<String> retList = new ArrayList<String>();
		
		if(!StringUtils.isBlank(ftp_dir)){
			boolean isChange = ftpUtils.changeWorkingDirectory(ftp_dir);
			if (!isChange) {
				throw new IOException("FTP切换 " + ftp_dir + " 目录失败！");
			}
		}
		
		List<FTPFile> ftpFileList = ftpUtils.listFTPFile(ftp_dir, filter);
		if (ftpFileList == null || ftpFileList.size() == 0) {
			return null;
		}
		
		File file = null;
		
		boolean isSuccess = false;
		String ftpFileName = null;
		for (FTPFile ftpFile : ftpFileList) {
			ftpFileName = ftpFile.getName();
			
			file = new File(local_dir, ftpFileName.substring(ftpFileName.lastIndexOf("/")+1));
			
			isSuccess = ftpUtils.download(ftpFileName, file);
			if (!isSuccess) {
				log.error(ftpFileName + " 文件下载失败!");
				continue;
			}
			//ftpUtils.delete(ftpFileName);
			retList.add(file.getAbsolutePath());
		}
		return retList;
	}
	
	public void close(){
		CloseUtil.close(ftpUtils);
	}
}
