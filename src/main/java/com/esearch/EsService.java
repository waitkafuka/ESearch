/**
 * Project Name:ESearch
 * File Name:Seacher.java
 * Package Name:com.esearch
 * Date:2015年4月2日上午11:40:41
 * Copyright s(c) 2015, www.landfalcon.com All Rights Reserved.
 *
 */

package com.esearch;

import static com.esearch.common.Constants.P_CONTENT;
import static com.esearch.common.Constants.P_DATE;
import static com.esearch.common.Constants.P_SOURCE;
import static com.esearch.common.Constants.P_TITLE;
import static com.esearch.common.Constants.P_URL;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.Requests;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.esearch.bean.SearchResultBean;
import com.esearch.common.Constants;
import com.esearch.common.utils.AnalyzerUtil;
import com.esearch.common.utils.ElasticUtil;

/**
 * ClassName:Seacher <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2015年4月2日 上午11:40:41 <br/>
 * 
 * @author zuokangsheng
 * @version
 * @since JDK 1.6
 * @see
 */
public class EsService {

	private static Logger log = LoggerFactory.getLogger(EsService.class);

	public synchronized static List<SearchResultBean> search(Client client,
			String words, int from, int size) {
		words = AnalyzerUtil.getSynonums(words);
		log.debug("最终搜索单词：{}", words);
		System.out.println("最终单词：" + words);
		// 从es中查询到map类型的结果
		List<Map<String, Object>> result = ElasticUtil.queryByWord(client,
				words, from, size);
		// 把map类型的结果转换为结果bean
		List<SearchResultBean> list = convertList(result);
		return list;
	}

	/*
	 * 把map类型的结果转换为结果bean
	 */
	private static List<SearchResultBean> convertList(
			List<Map<String, Object>> result) {
		// 遍历map集合，转化为SearchResultBean
		List<SearchResultBean> list = new ArrayList<SearchResultBean>();
		SearchResultBean bean = null;
		if (result != null && !result.isEmpty()) {
			for (Map<String, Object> att : result) {
				bean = new SearchResultBean();
				bean.setDate((String) att.get(P_DATE));
				bean.setUrl((String) att.get(P_URL));
				bean.setSource((String) att.get(P_SOURCE));
				bean.setTitle((String) att.get(P_TITLE));
				list.add(bean);
			}
		}
		return list;
	}

	/**
	 * 
	 * setMapping:给索引设置mapping. <br/>
	 * 
	 * @author zuokangsheng
	 * @throws IOException
	 * @since JDK 1.6
	 */
	public static void setMapping() throws IOException {

		Client client = ElasticUtil.getClient();
		String indices = Constants._INDEX;

		// 先删除旧的索引
		DeleteIndexRequest delete = new DeleteIndexRequest(Constants._INDEX);
		client.admin().indices().delete(delete);

		new XContentFactory();

		XContentBuilder builder = XContentFactory.jsonBuilder().startObject()
				.startObject(indices).startObject("properties")
				.startObject(P_URL).field("type", "string").endObject()
				.startObject(P_TITLE).field("type", "string")
				.field("store", "yes").field("indexAnalyzer", "ik")
				.field("searchAnalyzer", "ik").endObject().startObject(P_DATE)
				.field("type", "date").endObject().startObject(P_CONTENT)
				.field("type", "string").field("store", "yes")
				.field("indexAnalyzer", "ik").field("searchAnalyzer", "ik")
				.endObject().startObject(P_SOURCE).field("type", "string")
				.field("store", "yes").endObject().endObject().endObject()
				.endObject();
		PutMappingRequest mapping = Requests.putMappingRequest(indices)
				.type(indices).source(builder);
		client.admin().indices().prepareCreate(indices).execute().actionGet();
		client.admin().indices().putMapping(mapping).actionGet();
		client.close();
		log.info("新建索引和mapping完毕,mapping为：{}", builder.string());
	}

	public static void main(String[] args) {
		try {
			setMapping();
		} catch (IOException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();

		}
	}

}
