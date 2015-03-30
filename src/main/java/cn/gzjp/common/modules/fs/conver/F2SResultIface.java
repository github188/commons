package cn.gzjp.common.modules.fs.conver;

import java.io.Closeable;
import java.io.InputStream;

/**
 * @ClassName: F2SResultIface 
 * @Description: 文件一行行的读接口
 * @author huangzy@gzjp.cn
 * @date 2014年7月18日 下午2:57:56
 */
public interface F2SResultIface extends Closeable{
	public boolean hasNext() throws Exception;
	public String next() throws Exception;
	
	public void setInputStream(InputStream is) throws Exception;
}
