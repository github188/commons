package cn.gzjp.common.modules.fs.fsEntity.impl;

import org.apache.commons.lang3.StringUtils;

import cn.gzjp.common.modules.fs.fsEntity.FSEntityIface;
import cn.gzjp.common.modules.fs.fsEntity.annot.SplitAnnotaion;

/**
 * 基本文件实体
 * @Description: TODO
 * @ClassName: BaseFSEntity 
 * @author huangzy@gzjp.cn
 * @date 2014年9月28日 下午5:05:42
 */
public class BaseFSEntity implements FSEntityIface{
	
	protected String splitChar = null;
	
	public BaseFSEntity(){
		Class clazz = this.getClass();
		
		SplitAnnotaion splitAnnot = (SplitAnnotaion) clazz.getAnnotation(SplitAnnotaion.class);
		
		if(splitAnnot==null)
			throw new IllegalArgumentException("必需在类名上添加 SplitAnnotaion");
		
		splitChar = splitAnnot.value();
		
	}
	@Override
	public Object[] toArray(String str) {
		return StringUtils.splitPreserveAllTokens(str, splitChar);
	}
	
}
