package cn.gzjp.common.modules.fs.engine.impl;

import cn.gzjp.common.modules.fs.conver.ConverToStrIface;
import cn.gzjp.common.modules.fs.engine.Obj2StrHandleIface;

public class BaseObj2StrHandleImpl implements Obj2StrHandleIface{

	@Override
	public String obj2StrConver(Object obj, ConverToStrIface annotHandle)
			throws Exception {
		return annotHandle.objToStr(obj);
	}

}
