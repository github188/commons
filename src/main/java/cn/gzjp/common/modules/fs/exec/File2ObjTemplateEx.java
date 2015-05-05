package cn.gzjp.common.modules.fs.exec;

import java.util.List;
import java.util.Set;

import cn.gzjp.common.modules.fs.conver.ConverToObjIface;
import cn.gzjp.common.modules.fs.conver.F2SResultIface;
import cn.gzjp.common.modules.fs.conver.impl.DefaultConverToObjImpl;
import cn.gzjp.common.modules.fs.engine.File2ObjHandleIface;
import cn.gzjp.common.modules.fs.engine.impl.BaseFile2ObjHandleImpl;
import cn.gzjp.common.modules.fs.fsEntity.FSEntityIface;

/**
 * @ClassName: File2StrTemplateEx 
 * @Description: 将文件转为对象,执行模板
 * @author huangzy@gzjp.cn
 * @date 2014年7月9日 下午3:02:12
 */
public class File2ObjTemplateEx {
	private F2SResultIface f2sResult;
	private ConverToObjIface annotHandle;
	
	private File2ObjHandleIface f2oHandle;
	
	private static final ConverToObjIface DEFAULT_ANNOTHANDLE = new DefaultConverToObjImpl();
	private static final File2ObjHandleIface DEFAULT_F2OHANDLE = new BaseFile2ObjHandleImpl();
	
	public File2ObjTemplateEx(F2SResultIface f2sResult){
		this(f2sResult, DEFAULT_ANNOTHANDLE, DEFAULT_F2OHANDLE);
	}
	
	public File2ObjTemplateEx(F2SResultIface f2sResult,ConverToObjIface annothandle,File2ObjHandleIface f2ohandle){
		this.f2sResult = f2sResult;
		this.annotHandle = annothandle;
		this.f2oHandle = f2ohandle;
	}
	
	
	public <T extends FSEntityIface> T parse(Class<T> c) throws Exception{
		T t = f2oHandle.parseToObj(f2sResult,annotHandle,c);
		
		return t;
	}
	
	public <T extends FSEntityIface> List<T> parseList(Class<T> c) throws Exception{
		List<T> retList = f2oHandle.parseToObjList(f2sResult, annotHandle, c);
		
		return retList;
	}
	
	public <T extends FSEntityIface> Set<T> parseSet(Class<T> c) throws Exception{
		Set<T> retSet = f2oHandle.parseToObjSet(f2sResult, annotHandle, c);
		
		return retSet;
	}

	public void setAnnotHandle(ConverToObjIface annotHandle) {
		this.annotHandle = annotHandle;
	}

	public void setF2sResult(F2SResultIface f2sResult) {
		this.f2sResult = f2sResult;
	}

	public void setF2sHandle(File2ObjHandleIface f2sHandle) {
		this.f2oHandle = f2sHandle;
	}
	
}
