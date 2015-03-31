
package cn.gzjp.common.modules.logger;

import java.io.PrintStream;

import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;

/**
 * 日志基类
 * @ClassName: BaseLogger 
 * @Description: TODO
 * @author huangzy@gzjp.cn
 * @date 2014年7月23日 下午3:58:40
 */
public class BaseLogger {
	protected final org.slf4j.Logger log;
	
	public BaseLogger(){
		log = org.slf4j.LoggerFactory.getLogger("seedfee");
	}
	
	protected final void logInfo(LogFomartEnum l,Object arg0){
		String identify = LoggerIdentify.generateAndSetIdentify();
		
		String fomart = l.toString();
		log.info(fomart,LogFomartEnum.IDENTIFY+identify+";"+arg0.toString());
	}
	
	protected final void logInfo(LogFomartEnum l,Object ...arg0){
		String identify = LoggerIdentify.generateAndSetIdentify();
		
		StringBuilder sbLog = new StringBuilder();
		for(int i=0,k=arg0.length;i<k;i++){
			sbLog.append(arg0[i]);
			splitMark(sbLog, k, i);
		}
		String fomart = l.toString();
		log.info(fomart,LogFomartEnum.IDENTIFY+identify+";"+sbLog.toString());
	}
	
	protected void splitMark(StringBuilder sb,int totalSize,int currSize){
		if(currSize<totalSize-1){
			sb.append(";");
		}
	}
	
	public void logError(String arg0, Throwable arg1){
		String identify = LoggerIdentify.generateAndSetIdentify();
		String fomart = LogFomartEnum.ERROR_INFO.toString();
		
		ByteOutputStream out = new ByteOutputStream();
		PrintStream ps = new PrintStream(out);
		arg1.printStackTrace(ps);
		
		String errTracer = arg1.toString();
		try {
			errTracer = new String(out.getBytes(),"utf8");
		} catch (Exception e) {}
		log.error(fomart, LogFomartEnum.IDENTIFY+identify+";"+arg0.toString()+";",errTracer);
	}
	public void logError(String arg0, String arg1){
		String identify = LoggerIdentify.generateAndSetIdentify();
		String fomart = LogFomartEnum.ERROR_INFO.toString();
		
		log.error(fomart, LogFomartEnum.IDENTIFY+identify+";"+arg0.toString()+";"+arg1);
	}
	
	public void logDebug(String arg0, String arg1){
		String identify = LoggerIdentify.generateAndSetIdentify();
		String fomart = LogFomartEnum.DEBUG.toString();
		
		log.error(fomart, LogFomartEnum.IDENTIFY+identify+";"+arg0.toString()+";"+arg1);
	}
	
	protected String getStr(String remark,Object ...arg0){
		StringBuilder sbTmp = new StringBuilder();
		sbTmp.append("remark="+remark);
		// 1,0仅 触发 添加一个分隔符 无其它意义；
		splitMark(sbTmp, 2, 0);
		String arg02Str = getStrArr(arg0);
		sbTmp.append(arg02Str);
		
		return sbTmp.toString();
	}
	
	private String getStrArr(Object ...arg0){
		StringBuilder sbRet = new StringBuilder();
		
		if(arg0!=null&&arg0.length>0){
			for(int i=0,k=arg0.length;i<k;i++){
				sbRet.append(arg0[i]);
				splitMark(sbRet, k, i);
			}
		}
		
		return sbRet.toString();
	}
}
