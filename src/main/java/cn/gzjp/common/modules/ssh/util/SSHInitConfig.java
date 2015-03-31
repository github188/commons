package cn.gzjp.common.modules.ssh.util;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.gzjp.base.logger.ssh.entity.SSHOptionType;
import cn.gzjp.base.logger.ssh.entity.SSHServer;
import cn.gzjp.base.util.CloseUtil;
import cn.gzjp.base.util.File2StrResultlImpl;
import cn.gzjp.base.util.JPBeanUtil;

public class SSHInitConfig {
	//private static final SSHInitConfig config = new SSHInitConfig();
	private SSHInitConfig(){}
	private static final List<SSHOptionType> list;
	private static final String filename = "/sshFindLog.properties";
	static{
		list = new ArrayList<SSHOptionType>();
		InputStream in = null;
		try {
			String line;
			String[] arr;
			
			SSHServer sshServer = null;
			SSHOptionType sshOptionType = null;
			
			Map<String,SSHServer> sshServerMap = new HashMap<String,SSHServer>();
			
			in = SSHInitConfig.class.getResourceAsStream(filename);
			File2StrResultlImpl f2sResult = new File2StrResultlImpl(in);
			while(f2sResult.hasNext()){
				line = f2sResult.next();
				if(line.indexOf("#")==0)continue;
				arr = line.split("=");
				if(arr[0].indexOf("server")==0){
					if("serverName".equals(arr[0])){
						sshServer = new SSHServer();
					}
					JPBeanUtil.setVal(sshServer, arr[0], arr[1]);
					if("serverLogName".equals(arr[0])){
						sshServerMap.put(sshServer.getServerName(), sshServer);
					}
				}else if(arr[0].indexOf("logType")==0){
					if("logTypeKey".equals(arr[0])){
						sshOptionType = new SSHOptionType();
					}
					
					if("logTypeSearchServerName".equals(arr[0])){
						String[] arrServer = arr[1].split(",");
						List<SSHServer> sshServerList = new ArrayList<SSHServer>();
						
						for(String serv:arrServer){
							SSHServer tmpServer = sshServerMap.get(serv);
							if(tmpServer!=null){
								sshServerList.add(tmpServer);
							}
						}
						sshOptionType.setSshServerList(sshServerList);
						list.add(sshOptionType);
					}else{
						JPBeanUtil.setVal(sshOptionType, arr[0], arr[1]);
					}
					
				}
				
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			CloseUtil.close(in);
		}
	}
	
	public static List<SSHOptionType> getSSHOptionTypeList(){
		return list;
	}
	
	public static void main(String[] args) {
		System.out.println(list);
	}
}
