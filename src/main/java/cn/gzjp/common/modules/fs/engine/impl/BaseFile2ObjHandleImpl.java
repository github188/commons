package cn.gzjp.common.modules.fs.engine.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cn.gzjp.common.modules.fs.conver.ConverToObjIface;
import cn.gzjp.common.modules.fs.conver.F2SResultIface;
import cn.gzjp.common.modules.fs.engine.File2ObjHandleIface;
import cn.gzjp.common.modules.fs.fsEntity.FSEntityIface;

/**
 * @ClassName: BaseFile2ObjHandleImpl 
 * @Description: 文件转为对象基本处理类 
 * @author huangzy@gzjp.cn
 * @date 2014年7月18日 下午3:01:41
 */
public class BaseFile2ObjHandleImpl implements File2ObjHandleIface{

	@Override
	public <T extends FSEntityIface> T parseToObj(F2SResultIface result,
			ConverToObjIface annotHandle, Class<T> t)
			throws Exception {
		throw new UnsupportedOperationException("方法未实现");
	}

	@Override
	public <T extends FSEntityIface> List<T> parseToObjList(
			F2SResultIface result, ConverToObjIface annotHandle,
			Class<T> t) throws Exception {
		T obj2;
		String str = null;
		List<T> retList = new ArrayList<T>();
		while(result.hasNext()){
			str = result.next();
			obj2 = annotHandle.strToObject(str, t);
			
			retList.add(obj2);
		}
		
		return retList;
	}

	@Override
	public <T extends FSEntityIface> Set<T> parseToObjSet(
			F2SResultIface result, ConverToObjIface annotHandle, Class<T> t)
			throws Exception {
		List<T> retList = this.parseToObjList(result, annotHandle, t);
		
		Set<T> retSet = new HashSet<T>();
		retSet.addAll(retList);
		
		return retSet;
	}
}
