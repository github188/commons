/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015-11-6</p>
 *  <p> Created by 黄忠英</p>
 *  </body>
 * </html>
 */
package com.common.utils;

import java.math.BigDecimal;

/**
 * @Package: com.yxw.framework.utils
 * @ClassName: DecimalUtils
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author: 黄忠英
 * @Create Date: 2015-11-6
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class DecimalUtils {
	/**
	 * 返回两个数相乘
	 * @param amount
	 * @param multiplicand
	 * @return
	 */
	public static BigDecimal multiply(String amount, String multiplicand) {
		BigDecimal amountDec = new BigDecimal(amount);
		BigDecimal multiplicandDec = new BigDecimal(multiplicand);

		return amountDec.multiply(multiplicandDec);
	}

	public static String multiplyForStr(String amount, String multiplicand) {
		String ret = multiply(amount, multiplicand).toString();

		return subZero(ret);
	}

	/**
	 * 返回两个数相除
	 * @param amount
	 * @param divisor
	 * @return
	 */
	public static BigDecimal divide(String amount, String divisor) {
		BigDecimal amountDec = new BigDecimal(amount);
		BigDecimal divisorDec = new BigDecimal(divisor);

		return amountDec.divide(divisorDec);
	}

	public static String divideForStr(String amount, String divisor) {
		String ret = divide(amount, divisor).toString();
		return subZero(ret);
	}

	//如 str为1890.0,经过此函数处理后将返回 1890。(去掉了 .0)
	private static String subZero(String str) {
		String ret = str;

		int idx = ret.indexOf(".");
		if (idx > -1) {
			//.后面如果全是0,则删除.后面的所有0;
			String tmp = ret.substring(idx + 1);
			boolean allZero = true;
			for (int i = tmp.length() - 1; i >= 0; i--) {
				if (tmp.charAt(i) != '0') {
					allZero = false;
					break;
				}
			}
			if (allZero) {
				ret = ret.substring(0, idx);
			}
		}
		return ret;
	}
}
