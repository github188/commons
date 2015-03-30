package cn.gzjp.common.f2s.fsEntity.annot;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标识字段出现的位置
 * @author huangzy
 * 2014年7月8日
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PositionAnnotaion {
	public int value();
}
