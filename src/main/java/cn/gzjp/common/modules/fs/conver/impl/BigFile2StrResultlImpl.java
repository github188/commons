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
		List<Integer> flagList = super.getFlagList();
		flagList = flagList.subList(currentLine, flagList.size());
		
		int k=0;
		for(int i:flagList){
			if(i==1) k++;
			
			if(k==maxLine){
				currentLine = k+currentLine;
				
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
	
	/*public static void main(String[] args) throws IOException, InterruptedException {
		int max = 2;
		
		BigFile2StrResultlImpl impl = new 
				BigFile2StrResultlImpl("E:\\ftp_server_file\\filecenter_out\\user\\a.txt", max);
		
		while(true){
			int k=0;
			while(impl.hasNext()){
				System.out.println(impl.next());
				k++;
			}
			System.out.println("################################");
			if(k!=max) break;
			Thread.currentThread().sleep(3*1000);
		}
		
	}*/
}
