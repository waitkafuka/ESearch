package com.esearch.common.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.elasticsearch.action.count.CountResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.get.GetField;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.esearch.common.Constants.*;

import com.google.gson.Gson;

/**
 * 使用java api 操作elasticsearch索引，包含了基本的增删改查
 * 
 **/
public class ElasticUtil {

	private static Logger log = LoggerFactory.getLogger(ElasticUtil.class);

	public static Client getClient() {
		@SuppressWarnings("resource")
		Client client = new TransportClient()
				.addTransportAddress(new InetSocketTransportAddress(
						"127.0.0.1", 9300));
		return client;
	}

	public static void closeClient(Client client) {
		if (client != null) {
			client.close();
		}
	}

	public static void main(String[] args) throws Exception {
		System.out.println(queryTotalCount());
		System.out.println(queryCount("12"));
		DeleteIndexByQuery(_INDEX, _TYPE);
		System.out.println(queryTotalCount());
		System.out.println(queryCount("12"));
		// Map<String, String> map = new HashMap<String, String>();
		// map.put("p_content", "文章 搜索");
		// index(_INDEX, _TYPE, map);
		// queryById("dsa");
		// System.out.println(queryCount("文章搜索"));
		// System.out.println(queryByWord("12"));
		// ElasticUtil es = new ElasticUtil();
		// // es.updatedoc();
		// // es.getone();
		// es.deleteOne();
		// es.indexOne();
	}

	/**
	 * 根据ID删除一个文档
	 * 
	 * **/
	public void delete(String index, String type, String id) {
		try {
			Client client = getClient();
			DeleteResponse de = client.prepareDelete(index, type, id).execute()
					.actionGet();
			if (!de.isFound()) {
				log.error("词条数据不存在");
			}
			closeClient(client);
			log.info("删除成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据ID查询数据
	 * **/
	public static void queryById(String id) throws Exception {
		Client client = getClient();
		GetResponse response = client.prepareGet(_INDEX, _TYPE, id).execute()
				.actionGet();
		if (!response.isExists()) {
			log.info("数据不存在");
			return;
		}
		Map<String, Object> source = response.getSource();
		for (Entry<String, Object> eo : source.entrySet()) {
			System.out.println(eo.getKey() + "  " + eo.getValue());
		}
		Map<String, GetField> fields = response.getFields();
		if (fields != null) {
			for (Entry<String, GetField> s : fields.entrySet()) {
				System.out.println(s.getKey());
			}
		} else {
			System.out.println("fields is null;");
		}
		client.close();
	}

	/**
	 * 储存一个数据
	 * 
	 * **/
	public static void index(String index, String type, Map<String, String> data) {
		Client client = getClient();
		index(client, index, type, data);
		closeClient(client);
	}

	/**
	 * 用指定的客户端索引一个数据
	 * 
	 * @param client
	 * @param index
	 * @param type
	 * @param data
	 */
	public static void index(Client client, String index, String type,
			Map<String, String> data) {
		String uuid = UUID.randomUUID().toString();
		System.out.println(uuid);
		Gson g = new Gson();
		String json = g.toJson(data);
		client.prepareIndex(index, type, uuid).setSource(json).execute()
				.actionGet();
		log.info("index success");
	}

	/**
	 * 删除所有索引（慎用）
	 */
	public static void DeleteIndexByQuery(String index, String type) {
		MatchAllQueryBuilder allQueryBuilder = QueryBuilders.matchAllQuery();// 查询所有的documents
		Client client = getClient();
		client.prepareDeleteByQuery(index).setQuery(allQueryBuilder)
				.setTypes(type).execute().actionGet();
		closeClient(client);
		log.info("删除成功");
	}

	/**
	 * 查询一个索引下的总数量
	 * 
	 * @return
	 */
	public static long queryTotalCount() {
		MatchAllQueryBuilder allQueryBuilder = QueryBuilders.matchAllQuery();// 查询所有的documents
		Client client = getClient();
		CountResponse rs = client.prepareCount(_INDEX).setTypes(_TYPE)
				.setQuery(allQueryBuilder).execute().actionGet();
		closeClient(client);
		return rs.getCount();

	}

	/**
	 * 根据关键词搜索文章
	 * 
	 * @param client2
	 * 
	 * @param keyWord
	 * @return
	 */
	public static List<Map<String, Object>> queryByWord(Client client,
			String keyWord, int from, int size) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = null;
		QueryBuilder query = QueryBuilders.multiMatchQuery(keyWord, P_CONTENT,
				P_TITLE);
		SearchResponse response = client.prepareSearch(_INDEX).setQuery(query)
				.setFrom(from).setSize(size).setExplain(true).execute()
				.actionGet();
		SearchHits hits = response.getHits();
		hits.getTotalHits();
		for (SearchHit hit : hits) {
			map = hit.getSource();
			list.add(map);
		}
		return list;
	}

	/**
	 * 查询一个关键词命中的所有文章
	 * 
	 * @param keyWord
	 * @return
	 */
	public static int queryCount(String keyWord) {
		QueryBuilder query = QueryBuilders.matchQuery(P_TITLE, keyWord);
		Client client = getClient();
		CountResponse response = client.prepareCount(_INDEX).setTypes(_TYPE)
				.setQuery(query).execute().actionGet();
		closeClient(client);
		return (int) response.getCount();
	}
}
