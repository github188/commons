package com.common.modules.tip;

import java.util.Map;

/**
 * 提示类接口
 * @Description: TODO
 * @ClassName: TipTypeIface 
 * @author huangzy@gzjp.cn
 * @date 2015年3月23日 上午11:07:28
 */
public interface TipTypeIface {
	public boolean checkCondition();
	public Map<String,Object> retTipMap();

}
