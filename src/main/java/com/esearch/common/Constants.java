/**
 * Project Name:ESearch
 * File Name:Constants.java
 * Package Name:com.esearch.common
 * Date:2015年4月2日下午1:15:07
 * Copyright (c) 2015, www.landfalcon.com All Rights Reserved.
 *
 */

package com.esearch.common;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * ClassName:Constants <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2015年4月2日 下午1:15:07 <br/>
 * 
 * @author zuokangsheng
 * @version
 * @since JDK 1.6
 * @see
 */
public interface Constants {

	public static final DateFormat SF = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	/**
	 * 要搜索的网站
	 */
	public static final String[] WEB_SITES = new String[] {
			"http://finance.sina.com.cn/stock/", "http://stock.sohu.com/",
			"http://finance.qq.com/stock/", "http://money.163.com/stock/",
			"http://finance.ifeng.com/stock/", "http://stock.eastmoney.com/",
			"http://www.yicai.com/", "http://www.cnstock.com/",
			"http://stock.hexun.com/", "http://stock.cnfol.com/",
			"http://stock.cfi.cn/", "http://www.enoya.com/" };

	/**
	 * elasticSearch中存储的索引名
	 */
	public static final String _INDEX = "spider";

	/**
	 * elasticSearch中存储的类型
	 */
	public static final String _TYPE = "finance";

	/**
	 * 索引mapping类型
	 */
	public static final String _MAPPING_TYPE = "spider";

	/**
	 * elasticSearch节点IP
	 */
	public static final String _HOST = "127.0.0.1";

	/**
	 * elasticSearch通信端口
	 */
	public static final int _PORT = 9300;

	/**
	 * 链接
	 */
	public static final String P_URL = "p_url";

	/**
	 * 标题
	 */
	public static final String P_TITLE = "p_title";

	/**
	 * 日期
	 */
	public static final String P_DATE = "p_date";

	/**
	 * 内容
	 */
	public static final String P_CONTENT = "p_content";

	/**
	 * 来源
	 */
	public static final String P_SOURCE = "p_source";
}
