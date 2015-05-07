package myUtils.tip.impl;

import java.util.Map;

import com.common.modules.tip.impl.AbstractTip;

/**
 * 非定向用户提示
 * @Description: TODO
 * @ClassName: NotIsTheUserTip 
 * @author huangzy@gzjp.cn
 * @date 2015年3月23日 上午11:13:55
 */
public class NotIsTheUserTip extends AbstractTip {
	private int count;
	
	public NotIsTheUserTip(int count){
		this.count = count;
	}
	
	@Override
	public boolean checkCondition() {
		return count==0;
	}

	@Override
	public Map<String, Object> retTipMap() {
		Map<String, Object> retMap = createMap();
		
		String msg = super.propConf.getString("gift.notIsUser","非定向用户不能参加此活动");
		
		retMap.put("msg", msg);
		
		return retMap;
	}

}
