package com.esearch.common.utils;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.synonym.SynonymFilterFactory;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.util.FilesystemResourceLoader;
import org.apache.lucene.util.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

import com.esearch.servlet.Search;

public class AnalyzerUtil {

	private static Logger log = LoggerFactory.getLogger(AnalyzerUtil.class);

	/**
	 * 
	 * analyzeChinese:对输入的中文语句进行分词操作. <br/>
	 * 
	 * @author zuokangsheng
	 * @param input
	 * @param userSmart
	 * @return
	 * @throws IOException
	 * @since JDK 1.6
	 */
	public static String analyzeChinese(String input, boolean userSmart)// 开启智能切词
			throws IOException {
		StringBuffer sb = new StringBuffer();
		StringReader reader = new StringReader(input.trim());
		IKSegmenter ikSeg = new IKSegmenter(reader, userSmart);
		for (Lexeme lexeme = ikSeg.next(); lexeme != null; lexeme = ikSeg
				.next()) {
			sb.append(lexeme.getLexemeText()).append(" ");
		}
		return sb.toString();
	}

	/**
	 * 
	 * convertSynonym:对分词后的单词进行同义词匹配，返回匹配到的同义词. <br/>
	 * 
	 * @author zuokangsheng
	 * @param input
	 * @return
	 * @throws IOException
	 * @since JDK 1.6
	 */
	public static TokenStream convertSynonym(String input) throws IOException {
		// 从servlet中取出同义词过滤器
		SynonymFilterFactory factory = Search.getFactory();
		// 如果servlet中没有初始化，再手动初始化一个
		if (factory == null) {
			factory = getSynonymFactory();
		}
		factory.inform(new FilesystemResourceLoader());
		Analyzer whitespaceAnalyzer = new WhitespaceAnalyzer();
		TokenStream ts = factory.create(whitespaceAnalyzer.tokenStream(
				"someField", input));
		return ts;
	}

	public static SynonymFilterFactory getSynonymFactory() {
		SynonymFilterFactory factory;
		Version ver = Version.LUCENE_4_10_4;
		Map<String, String> filterArgs = new HashMap<String, String>();
		filterArgs.put("luceneMatchVersion", ver.toString());
		filterArgs.put("synonyms", "config/synonyms.txt");
		filterArgs.put("expand", "true");
		log.info("加载近义词典完毕");
		factory = new SynonymFilterFactory(filterArgs);
		return factory;
	}

	/**
	 * 
	 * displayTokens:把tokenStream转换为单词串. <br/>
	 * 
	 * @author zuokangsheng
	 * @param ts
	 * @return
	 * @throws IOException
	 * @since JDK 1.6
	 */
	public static String displayTokens(TokenStream ts) throws IOException {
		StringBuffer sb = new StringBuffer();
		CharTermAttribute termAttr = (CharTermAttribute) ts
				.addAttribute(CharTermAttribute.class);
		ts.reset();
		while (ts.incrementToken()) {
			String token = termAttr.toString();
			sb.append(token).append(" ");
		}
		ts.end();
		ts.close();
		return sb.toString();
	}

	/**
	 * 
	 * getSynonums:对输入的搜索文字进行同义词处理. <br/>
	 * 
	 * @author zuokangsheng
	 * @param input
	 * @return
	 * @since JDK 1.6
	 */
	public static String getSynonums(String input) {
		try {
			String splitWords = analyzeChinese(input, true);
			return displayTokens(convertSynonym(splitWords));
		} catch (IOException e) {
		}
		return "";
	}

	public static void main(String[] args) throws IOException {
		String s = "计算机是好人历史上的伟大发明。";
		System.out.println(getSynonums(s));
	}
}