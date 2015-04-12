package com.esearch.spider;

public class Spider {
	public static void main(String[] args) {
		int total = 0;
		try {
			System.out.println("东方证券开始抓取：");
			int count1 = new EastMoneySpider().getSiteData();
			System.out.println("抓取完毕，抓取了" + count1 + "条数据");
			total += count1;

			System.out.println("凤凰网开始抓取：");
			int count2 = new IFengSpider().getSiteData();
			System.out.println("抓取完毕，抓取了" + count2 + "条数据");
			total += count2;

			System.out.println("腾讯财经开始抓取：");
			int count3 = new QQSpider().getSiteData();
			System.out.println("抓取完毕，抓取了" + count3 + "条数据");
			total += count3;

			System.out.println("新浪股票开始抓取：");
			int count4 = new SinaSpider().getSiteData();
			System.out.println("抓取完毕，抓取了" + count4 + "条数据");
			total += count4;

			System.out.println("搜狐网开始抓取：");
			int count5 = new SohuSpider().getSiteData();
			System.out.println("抓取完毕，抓取了" + count5 + "条数据");
			total += count5;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println();
			System.out.println("全部抓取完毕，一共" + total + "条。");
		}

	}
}
