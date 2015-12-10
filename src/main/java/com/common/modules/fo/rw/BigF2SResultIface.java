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
package com.common.modules.fo.rw;

import java.util.List;

/**
 * @Package: com.fbs.samp.sys.pub.fs.conver
 * @ClassName: BigF2SResultIface
 * @Statement: <p>大文件分批读取</p>
 * @JDK version used: 
 * @Author: 黄忠英
 * @Create Date: 2016年4月28日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public interface BigF2SResultIface extends F2SResultIface {
	/**分批*/
	public List<String> batchNext() throws Exception;
}
