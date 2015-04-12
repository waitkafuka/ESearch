package com.esearch.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.lucene.analysis.synonym.SynonymFilterFactory;
import org.apache.lucene.util.Version;
import org.elasticsearch.client.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.esearch.EsService;
import com.esearch.bean.SearchResultBean;
import com.esearch.common.utils.AnalyzerUtil;
import com.esearch.common.utils.ElasticUtil;

/**
 * Servlet implementation class Search
 */
public class Search extends HttpServlet {

	Logger log = LoggerFactory.getLogger(Search.class);

	private static final long serialVersionUID = 1L;

	private static SynonymFilterFactory factory;

	protected Client client = null;

	public static SynonymFilterFactory getFactory() {
		return factory;
	}

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Search() {
		super();
		client = ElasticUtil.getClient();
		log.info("建立client");
		// 创建近义词过滤工厂
		factory = AnalyzerUtil.getSynonymFactory();
	}

	public void destroy() {
		ElasticUtil.closeClient(client);
		log.info("销毁client");
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String keyWord = request.getParameter("k");
		request.setAttribute("k", keyWord);

		// 任务开始时的时间
		long start = System.currentTimeMillis();
		// 从es中搜索到结果
		List<SearchResultBean> list = EsService.search(client, keyWord, 0, 10);
		// 从es中查询结果条数
		int count = ElasticUtil.queryCount(keyWord);
		long end = System.currentTimeMillis();
		// 计算出耗费时间
		long cost = end - start;
		request.setAttribute("result", list);// 结果集
		request.setAttribute("count", count);// 命中数量
		request.setAttribute("cost", (float)cost/1000);// 用时

		// 跳转到结果页
		request.getRequestDispatcher("views/list.jsp").forward(request,
				response);
	}

}
