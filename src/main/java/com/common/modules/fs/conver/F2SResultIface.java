package com.common.modules.fs.conver;

import java.io.Closeable;

/**
 * @ClassName: F2SResultIface 
 * @Description: 一行行的读接口
 * @author huangzy@gzjp.cn
 * @date 2014年7月18日 下午2:57:56
 */
public interface F2SResultIface extends Closeable {
	public boolean hasNext() throws Exception;

	public String next() throws Exception;
}
