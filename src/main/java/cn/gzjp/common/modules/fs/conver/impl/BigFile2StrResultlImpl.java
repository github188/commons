package cn.gzjp.common.modules.fs.conver.impl;

import java.io.IOException;
import java.util.List;

import cn.gzjp.common.modules.fs.conver.BigF2SResultIface;

/**
 * 大文件分批读取 实现
 * @Description: TODO
 * @ClassName: BigFile2StrResultlImpl 
 * @author huangzy@gzjp.cn
 * @date 2014年9月29日 下午1:49:20
 */
public class BigFile2StrResultlImpl extends File2StrResultlImpl implements BigF2SResultIface{
	private int currentLine = 0;
	private int maxLine = 1000;
	
	public BigFile2StrResultlImpl(){}
	
	public BigFile2StrResultlImpl(String filepath,int maxLine) throws IOException{
		super(filepath);
		this.maxLine = maxLine;
	}
	
	@Override
	public boolean batche() {
		int k=0;
		List<Integer> flagList = super.getFlagList();
		for(int tmpCurrentLine=this.currentLine;tmpCurrentLine<flagList.size();tmpCurrentLine++){
			Integer v = flagList.get(tmpCurrentLine);
			
			if(v==1) k++;
			if(k==this.maxLine){
				this.currentLine = k+this.currentLine;
				
				return true;
			}
		}
		
		return false;
	}
	
	@Override
	public boolean hasNext() throws IOException{
		if(batche()){
			return false;
		}
		return super.hasNext();
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
	}
	
	/*public static void main(String[] args) throws IOException, InterruptedException {
		int max = 5;
		
		BigFile2StrResultlImpl impl = new 
				BigFile2StrResultlImpl("E:\\loverlymay_visit_2015-05-02.log", max);
		
		while(true){
			int k=0;
			while(impl.hasNext()){
				System.out.println(impl.next());
				k++;
			}
			System.out.println("############################################################");
			if(k!=max) break;
			Thread.currentThread().sleep(3*1000);
		}
		
	}*/
}
