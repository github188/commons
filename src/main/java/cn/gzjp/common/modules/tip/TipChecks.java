package cn.gzjp.common.modules.tip;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 检查一系列的条件 (创建好对象后必需选执行check()方法)
 * @Description: TODO
 * @ClassName: TipChecks 
 * @author huangzy@gzjp.cn
 * @date 2015年3月25日 下午2:54:11
 */
public class TipChecks {
	//符合条件的
	private TipTypeIface tipType;
	private List<TipTypeIface> tipTypeList = new ArrayList<TipTypeIface>();
	
	private TipChecks(){};
	
	public static TipChecks newInstance(TipTypeIface ...tipTypes){
		TipChecks tipChecks = new TipChecks();
		tipChecks.addTipType(tipTypes);
		return tipChecks;
	}
	
	/**
	 * 检查所有的tip,若有一个符合,则返回true;
	 * @return
	 */
	public boolean check(){
		boolean ret = false;
		for(TipTypeIface tmpTipType:tipTypeList){
			if(tmpTipType.checkCondition()){
				this.tipType = tmpTipType;
				ret = true;
				break;
			}
		}
		return ret;
	}
	
	public Map<String,Object> tipMap(){
		return this.tipType.retTipMap();
	}
	
	public void addTipType(TipTypeIface tipType){
		addTipType(new TipTypeIface[]{tipType});
	}
	
	public void addTipType(TipTypeIface ...tipType){
		tipTypeList.addAll(Arrays.asList(tipType));
	}

	public TipTypeIface getTipType() {
		return tipType;
	}
	
}
