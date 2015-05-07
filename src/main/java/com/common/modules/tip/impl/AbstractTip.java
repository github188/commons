package com.common.modules.tip.impl;

import java.util.HashMap;

import org.apache.commons.configuration.PropertiesConfiguration;

import com.common.modules.tip.TipTypeIface;
import com.common.utils.PropertiesCacheUtil;

/**
 * 信息提示 抽象类
 * @Description: TODO
 * @ClassName: AbstractTip 
 * @author huangzy@gzjp.cn
 * @date 2015年3月23日 下午4:31:21
 */
public abstract class AbstractTip implements TipTypeIface{
	
	protected PropertiesConfiguration propConf;
	
	private static final String DEFAULT_TIP_TEMPLATE = "/tipTemplate.properties";
	
	protected AbstractTip(){
		this.setTipTemplate();
	}
	protected AbstractTip(String filepath){
		this.setTipTemplate(filepath);
	}
	
	private void setTipTemplate(){
		this.setTipTemplate(DEFAULT_TIP_TEMPLATE);
	}
	protected void setTipTemplate(String filepath){
		this.propConf = PropertiesCacheUtil.getInstance().getConfInstance(filepath);
	}
	
	protected HashMap<String,Object> createMap(){
		return new HashMap<String, Object>();
	}

}
