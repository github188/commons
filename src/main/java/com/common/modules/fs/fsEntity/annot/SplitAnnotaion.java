package com.common.modules.fs.fsEntity.annot;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标识分隔符
 * @author huangzy
 * 2014年7月8日
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface SplitAnnotaion {
	/**分隔符*/
	public String value() default ",";
}
