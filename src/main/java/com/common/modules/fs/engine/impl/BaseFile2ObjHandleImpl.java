package com.common.modules.fs.engine.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.common.modules.fs.conver.ConverToObjIface;
import com.common.modules.fs.conver.F2SResultIface;
import com.common.modules.fs.engine.File2ObjHandleIface;
import com.common.modules.fs.fsEntity.FSEntityIface;

/**
 * @ClassName: BaseFile2ObjHandleImpl 
 * @Description: 文件转为对象基本处理类 
 * @author huangzy@gzjp.cn
 * @date 2014年7月18日 下午3:01:41
 */
public class BaseFile2ObjHandleImpl implements File2ObjHandleIface{
	
	private final int cpuCount = (int)(Runtime.getRuntime().availableProcessors()*1.5);
	private final ExecutorService threadPool = Executors.newFixedThreadPool(cpuCount);
	
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
		
		//多线程读
		AnnotThread annotThread = new AnnotThread(result, annotHandle, t);
		
		for(int i=0;i<cpuCount;i++){
			threadPool.execute(annotThread);
		}
		annotThread.getLatch().await();
		
		return annotThread.getRetList();
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
	
	private class AnnotThread<T extends FSEntityIface> implements Runnable{
		
		private F2SResultIface result;
		
		private Class<T> t;
		private ConverToObjIface annotHandle;
		
		private List<T> retList = new Vector<T>();
		
		private final CountDownLatch latch = new CountDownLatch(cpuCount);
		
		private volatile boolean hasNext = true;
		
		private AnnotThread(){}
		
		private AnnotThread(F2SResultIface result
				,ConverToObjIface annotHandle,Class<T> t){
			
			this.result = result;
			this.t = t;
			this.annotHandle = annotHandle;
		}
		
		@Override
		public void run() {
			try {
				//System.out.println(Thread.currentThread().getName()+" start");
				
				String str = null;
				while((str=readLine())!=null){
					//System.out.println(Thread.currentThread().getName()+","+str);
					
					T obj2 = annotHandle.strToObject(str, t);
					retList.add(obj2);
				}
				//System.out.println(Thread.currentThread().getName()+" end");
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}finally{
				//线程执行完成 减1
				latch.countDown();
			}
			
		}
		
		private String readLine() throws Exception{
			synchronized(result){
				String str = null;
				if(!hasNext) return str;
				
				hasNext = result.hasNext();
				if(hasNext){
					str = result.next();
				}
				return str;
			}
		}

		private List<T> getRetList() {
			return retList;
		}

		private CountDownLatch getLatch() {
			return latch;
		}

	}
	
}
