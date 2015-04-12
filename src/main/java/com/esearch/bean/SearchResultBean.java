/**
 * Project Name:ESearch
 * File Name:SearchResult.java
 * Package Name:com.esearch
 * Date:2015年4月2日下午12:44:57
 * Copyright (c) 2015, www.landfalcon.com All Rights Reserved.
 *
 */

package com.esearch.bean;

import java.io.Serializable;

/**
 * ClassName:SearchResult <br/>
 * Function: TODO ADD s FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2015年4月2日 下午12:44:57 <br/>
 * 
 * @author zuokangsheng
 * @version 1.0
 * @since JDK 1.6
 * @see
 */
public class SearchResultBean implements Serializable {
	private static final long serialVersionUID = -7355483799447074802L;

	private String title;// 标题
	private String date;// 日期
	private String source;// 来源
	private String url;// 链接

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

}
