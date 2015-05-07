package com.common.modules.fs.engine;

import java.util.List;
import java.util.Set;

import com.common.modules.fs.conver.ConverToObjIface;
import com.common.modules.fs.conver.F2SResultIface;
import com.common.modules.fs.fsEntity.FSEntityIface;

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
