package cn.gzjp.common.f2s.engine;

import java.util.List;
import java.util.Set;

import cn.gzjp.common.f2s.conver.ConverToObjIface;
import cn.gzjp.common.f2s.conver.F2SResultIface;
import cn.gzjp.common.f2s.fsEntity.FSEntityIface;

/**
 * 将文件解析为对象接口
 * @author huangzy
 * 2014年7月7日
 */
public interface File2ObjHandleIface {
	public <T extends FSEntityIface> T parseToObj(F2SResultIface result,
			ConverToObjIface annotHandle,Class<T> t) throws Exception;
	
	public <T extends FSEntityIface> List<T> parseToObjList(F2SResultIface result,
			ConverToObjIface annotHandle,Class<T> t) throws Exception;
	
	public <T extends FSEntityIface> Set<T> parseToObjSet(F2SResultIface result,
			ConverToObjIface annotHandle,Class<T> t) throws Exception;
}
