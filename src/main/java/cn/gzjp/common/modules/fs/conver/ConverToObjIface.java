package cn.gzjp.common.modules.fs.conver;

import cn.gzjp.common.modules.fs.fsEntity.FSEntityIface;

/**
 * @ClassName: AnnotToObjHandleIface 
 * @Description: 将字符串转为对象接口
 * @author huangzy@gzjp.cn
 * @date 2014年7月7日 下午2:58:49
 */
public interface ConverToObjIface {
	public <T extends FSEntityIface> T strToObject(String str,Class<T> c) throws Exception;
}
