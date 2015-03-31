package cn.gzjp.common.modules.logger;

/**
 * 格式化日志输出标志
 * @ClassName: LogFomartEnum 
 * @Description: TODO
 * @author huangzy@gzjp.cn
 * @date 2014年7月23日 上午11:03:50
 */
public enum LogFomartEnum {
	
	METHOD_IN("in:[{}]"),METHOD_OUT("out:[{}]"),ERROR_INFO("error:[{}]"),AOP_REQ("aop_req:[{}]"),AOP_RSP("aop_rsp:[{}]"),IDENTIFY("identify="),
		REQUEST_IN("req_in:[{}]"),REQUEST_OUT("req_out:[{}]"),DEBUG("debug:[{}]");
	
	private String fomart;
	private LogFomartEnum(String fomart){
		this.fomart = fomart;
	}
	
	public String getFomart(){
		return this.fomart;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.fomart;
	}
}