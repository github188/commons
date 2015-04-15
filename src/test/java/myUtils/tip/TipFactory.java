package myUtils.tip;

import myUtils.tip.impl.NotIsTheUserTip;
import cn.gzjp.common.modules.tip.TipTypeIface;

/**
 * 提示工厂类
 * @Description: TODO
 * @ClassName: TipFactory 
 * @author huangzy@gzjp.cn
 * @date 2015年3月23日 上午11:06:10
 */
public class TipFactory {
	
	public static TipTypeIface createNotIsTheUser(int count) {
		return new NotIsTheUserTip(count);
	}
	
}
