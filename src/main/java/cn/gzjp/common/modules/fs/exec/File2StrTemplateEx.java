package cn.gzjp.common.f2s.exec;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Set;

import cn.gzjp.common.f2s.conver.BigF2SResultIface;
import cn.gzjp.common.f2s.conver.ConverToObjIface;
import cn.gzjp.common.f2s.conver.F2SResultIface;
import cn.gzjp.common.f2s.conver.impl.DefaultConverToObjImpl;
import cn.gzjp.common.f2s.conver.impl.File2StrResultlImpl;
import cn.gzjp.common.f2s.engine.File2ObjHandleIface;
import cn.gzjp.common.f2s.engine.impl.DefaultFile2ObjHandleImpl;
import cn.gzjp.common.f2s.fsEntity.FSEntityIface;
import cn.gzjp.common.utils.CloseUtil;

/**
 * @ClassName: File2StrTemplateEx 
 * @Description: 将文件转为对象,执行模板
 * @author huangzy@gzjp.cn
 * @date 2014年7月9日 下午3:02:12
 */
public class File2StrTemplateEx {
	private File2ObjHandleIface f2oHandle = new DefaultFile2ObjHandleImpl();
	
	private F2SResultIface f2sResult = new File2StrResultlImpl();
	private ConverToObjIface annotHandle = new DefaultConverToObjImpl();
	
	public <T extends FSEntityIface> T parse(String filepath,Class<T> c) throws Exception{
		FileInputStream fis = new FileInputStream(filepath);
		f2sResult.setInputStream(fis);
		T t = f2oHandle.parseToObj(f2sResult,annotHandle,c);
		
		close();
		
		return t;
	}
	
	public <T extends FSEntityIface> T parse(InputStream is,Class<T> c) throws Exception{
		f2sResult.setInputStream(is);
		T t = f2oHandle.parseToObj(f2sResult,annotHandle,c);
		
		close();

		return t;
	}
	
	public <T extends FSEntityIface> List<T> parseList(String filepath,Class<T> c) throws Exception{
		FileInputStream fis = new FileInputStream(filepath);
		f2sResult.setInputStream(fis);
		List<T> retList = f2oHandle.parseToObjList(f2sResult, annotHandle, c);
		
		close();
		return retList;
	}
	
	public <T extends FSEntityIface> List<T> parseList(Class<T> c) throws Exception{
		List<T> retList = f2oHandle.parseToObjList(f2sResult, annotHandle, c);
		
		close();
		return retList;
	}
	
	public <T extends FSEntityIface> List<T> parseList(InputStream is,Class<T> c) throws Exception{
		f2sResult.setInputStream(is);
		List<T> retList = f2oHandle.parseToObjList(f2sResult, annotHandle, c);
		
		close();
		return retList;
	}
	
	public <T extends FSEntityIface> Set<T> parseSet(String filepath,Class<T> c) throws Exception{
		FileInputStream fis = new FileInputStream(filepath);
		f2sResult.setInputStream(fis);
		Set<T> retSet = f2oHandle.parseToObjSet(f2sResult, annotHandle, c);
		
		close();
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
	
	private void close(){
		//大文件读取接口IO流 由调用者自行关闭
		if(!(f2sResult instanceof BigF2SResultIface)){
			CloseUtil.close(f2sResult);
		}
	}
}
