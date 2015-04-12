package com.esearch.spider;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.elasticsearch.client.Client;
import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.esearch.common.Constants.*;

import com.esearch.common.utils.ElasticUtil;

public class QQSpider {

	Logger log = LoggerFactory.getLogger(QQSpider.class);

	public int getSiteData() throws IOException {
		String source = "腾讯财经";
		Document doc = Jsoup.connect("http://finance.qq.com/stock/").get();
		Elements links = doc.select("a[href]");
		String title = null;
		String url = null;
		String date = null;
		String content = null;
		Map<String, String> map = null;
		int count = 0;
		Client client = ElasticUtil.getClient();
		for (Element link : links) {
			title = link.text();
			if (!StringUtil.isBlank(title) && title.length() > 10) {
				map = new HashMap<String, String>();
				url = link.attr("href");
				try {
					doc = Jsoup.connect(url).get();
				} catch (Exception e) {
					continue;
				}
				date = doc.select(".pubTime").text();
				// 日期处理
				if (StringUtil.isBlank(date)) {
					// continue;
					date = "";
				}
				content = doc.text();
				content = Jsoup.clean(content, Whitelist.none());
				map.put(P_TITLE, title);
				map.put(P_DATE, date);
				map.put(P_URL, url);
				map.put(P_CONTENT, content);
				map.put(P_SOURCE, source);
				// 把抓取到的数据放入到elasticSearch中
				System.out.println("索引了" + ++count + "条");
				ElasticUtil.index(client, _INDEX, _TYPE, map);
			}
		}
		ElasticUtil.closeClient(client);
		return count;
	}

}
