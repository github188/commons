package cn.gzjp.common.f2s.engine.impl;

import cn.gzjp.common.f2s.conver.ConverToStrIface;
import cn.gzjp.common.f2s.engine.Obj2StrHandleIface;

public class BaseObj2StrHandleImpl implements Obj2StrHandleIface{

	@Override
	public String obj2StrConver(Object obj, ConverToStrIface annotHandle)
			throws Exception {
		return annotHandle.objToStr(obj);
	}

}
