package cn.gzjp.common.modules.wsHepler;

import org.apache.log4j.Logger;

import cn.gzjp.common.modules.wsHepler.impl.DefaultXFireClient;

public class XFireClient{
	private static final Logger log = Logger.getLogger(XFireClient.class);
	
	public static String exec(String msg,String url){
		DefaultXFireClient client = new DefaultXFireClient(url);
		
		String ret = null;
		try {
			ret = client.execute(msg);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		}
		return ret;
	}
}
