package cn.gzjp.common.modules.ssh.service;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import cn.gzjp.common.modules.ssh.entity.SSHOptionType;
import cn.gzjp.common.modules.ssh.entity.SSHServer;
import cn.gzjp.common.modules.ssh.util.FindWhyFromTheLog;
import cn.gzjp.common.modules.ssh.util.SSHInitConfig;
import cn.gzjp.common.utils.DateUtils;

@Service("sshLogService")
public class SSHLogService{
	private static final SimpleDateFormat defaultWebFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	private static final List<SSHOptionType> config = SSHInitConfig.getSSHOptionTypeList();
	
	public Map<String,Object> findLogFromSSHServer(String logtype,String keyWord,String date ) throws UnsupportedEncodingException{
		
		Map<String,Object> retMap = new HashMap<String, Object>();
		retMap.put("success", false);
		if(StringUtils.isBlank(logtype)){
			retMap.put("msg", "查询类型不能为空");
			return retMap;
		}
		if(StringUtils.isBlank(keyWord)){
			retMap.put("msg", "查询关键字不能为空");
			return retMap;
		}
		if(StringUtils.isBlank(date)){
			//retMap.put("msg", "查询日期不能为空");
			//return retMap;
		}
		
		keyWord = filter(keyWord);
		date = filter(date);
		
		SSHOptionType sshOptionType = findSSHOptionType(logtype);
		if(sshOptionType==null){
			throw new IllegalArgumentException("找不到logtype="+logtype+"定义,请检查配制文件.");
		}
		StringBuilder msg = new StringBuilder();
		
		String logMsg;
		FindWhyFromTheLog f = null;
		String logFilePath = null;
		
		StringBuilder cmdSB = new StringBuilder();
		String[] keyWordArr = keyWord.split(" ");
		
		cmdSB.append("grep "+keyWordArr[0] + " ").append("#{logFilePath}");
		if(keyWordArr.length>=2){
			for(int i=1;i<keyWordArr.length;i++){
				if(StringUtils.isBlank(keyWordArr[i])) continue;
				
				cmdSB.append("|grep "+keyWordArr[i]);
			}
		}
		
		
		for(SSHServer sshServer:sshOptionType.getSshServerList()){
			f=new FindWhyFromTheLog(sshServer.getServerIP(),sshServer.getServerUser(),sshServer.getServerPwd());
			
			logFilePath = sshServer.getServerLogDir()+parseFileName(sshServer.getServerLogName(),date);
			
			String command = cmdSB.toString().replace("#{logFilePath}", logFilePath);
			
			msg.append("\n ###from "+sshServer.getServerIP()+" return msg###:\n");
			msg.append("##################################################\n");
			
			System.out.println("command>>>"+command);
			
			logMsg = f.executeCommand(new String(command.getBytes("utf8"),"gbk"));
			
			msg.append(logMsg);
			msg.append("\n");
		}
		retMap.put("success", true);
		retMap.put("msg", msg.toString());
		
		return retMap;
	}
	
	public List<SSHOptionType> getSSHOptionTypeList(){
		return config;
	}
	
	private String parseFileName(String serverLogName,String date){
		int first = serverLogName.indexOf("${")+2;
		int second = serverLogName.indexOf("}", first);
		
		String format = serverLogName.substring(first,second);
		
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		
		String logDate = null;
		if(StringUtils.isBlank(date)){
			logDate = "*";
		}else{
			logDate = DateUtils.dateStrConverStr(date, defaultWebFormat, sdf);
		}
		
		String fileName = serverLogName.replace("${"+format+"}", logDate);
		return fileName;
	}
	
	private SSHOptionType findSSHOptionType(String logtype){
		SSHOptionType ret = null;
		for(SSHOptionType type : config){
			if(type.getLogTypeKey().equals(logtype)){
				ret = type;
				break;
			}
		}
		return ret;
	}
	
	/**
	 * 过滤前端输入的数据
	 * @param value
	 * @return
	 */
	private String filter(String value){
		if(StringUtils.isBlank(value)) return value;
		
		value = value.replaceAll("cp", "");
		value = value.replaceAll("mv", "");
		value = value.replaceAll("rm", "");
		value = value.replaceAll("add", "");
		value = value.replaceAll("user", "");
		
		value = value.replaceAll("find", "");
		value = value.replaceAll("ps", "");
		value = value.replaceAll("kill", "");
		value = value.replaceAll("top", "");
		
		value = value.replaceAll("&", "");
		value = value.replaceAll("'", "");
		value = value.replaceAll("\"", "");
		value = value.replaceAll("\\|", "");
		return value;
	}
}
