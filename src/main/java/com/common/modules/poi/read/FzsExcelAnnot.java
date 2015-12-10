/**
 * <html>
 * <body>
 *  <P>  Copyright 2016-2017 www.phone580.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created by 黄忠英</p>
 *  <p> Email:h419802957@foxmail.com
 *  </body>
 * </html>
 */
package com.common.modules.poi.read;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Package: com.fzs.samp.commons.poi.read
 * @ClassName: FzsExcelAnnot
 * @Statement: <p>excel读取注解</p>
 * @JDK version used: 
 * @Author: 黄忠英
 * @Create Date: 2016年10月13日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface FzsExcelAnnot {
	/**
	 * 包含哪些列,第一列是0。默认读取全部列
	 * @return
	 */
	public int[] cellIndex() default {};

	/**
	 * 写到哪一些属性。cellIndex数组长度等于fieldName数组长度
	 * (预留,2016/10/13注)
	 * @return
	 */
	public String[] fieldName() default {};
}
