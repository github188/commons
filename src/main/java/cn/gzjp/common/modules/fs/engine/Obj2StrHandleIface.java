package cn.gzjp.common.f2s.engine;

import cn.gzjp.common.f2s.conver.ConverToStrIface;

/**
 * @ClassName: Obj2StrHandleIface 
 * @Description: 将对象转为一行行数据接口
 * @author huangzy@gzjp.cn
 * @date 2014年7月16日 下午3:01:18
 */
public interface Obj2StrHandleIface {
	public String obj2StrConver(Object obj,ConverToStrIface annotHandle) throws Exception;
}
