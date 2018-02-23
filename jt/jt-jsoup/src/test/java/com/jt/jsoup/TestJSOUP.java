package com.jt.jsoup;

import java.io.IOException;

import org.aspectj.lang.annotation.Before;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.jsoup.service.StudentBookService;
import com.jt.jsoup.service.StudentBookServiceImpl;

public class TestJSOUP {
	private static ObjectMapper objectMapper=new ObjectMapper();
	/**
	 * 1.爬去静态页面数据获取页面标题
	 * 2.定位目标网站的页面
	 * 3.定位页面元素内容
	 * 4.获取具体数据
	 * 5.数据处理
	 * 6.入库保存
	 * @throws IOException 
	 */
	@Test
	public  void test01() throws IOException{
		String url="http://www.it211.com.cn/web/index_new.html?tedu";
		//通过JSOUP进行数据爬取
		Document document = Jsoup.connect(url).get();
		
		//通过jsoup提供的选择器快速定位目标元素
		Element element = document.select(".b_search h2").get(0);
		
		//获取h2的具体文本内容
		
		String msg = element.text();
		System.out.println("爬取标题数据!"+msg);
	}
	@Test
	public  void test02() throws IOException{
		String url="http://www.it211.com.cn/commonData/getCommonNum";
		//通过JSOUP进行数据爬取
		Response response = Jsoup.connect(url).ignoreContentType(true).execute();
		//将获取到的结果转化为字符串
		String resultJSON = response.body();
		System.out.println(resultJSON);
		//{"result":"1","obj":{"userNum":397608,"bookNum":999,"dirNum":17}}
		
		//获取学生人数
		 JsonNode jsonNode = objectMapper.readTree(resultJSON);
		 
		 //获取obj的属性值
		 String userNum = jsonNode.get("obj").get("userNum").toString();
		 System.out.println(userNum);
	}
	
	private StudentBookService studentBookService;
	@org.junit.Before
	public void init(){
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring/*.xml");
		
		studentBookService= context.getBean("studentBookServiceImpl",StudentBookService.class);
	}
	
	
	@Test
	public  void test04() throws IOException{
		String url="http://www.it211.com.cn/book_test/getHotBook";
		//通过JSOUP进行数据爬取
		studentBookService.insertBook(url);
		/*
		String resultJSON = Jsoup.connect(url).ignoreContentType(true).execute().body();
		String listJSON = objectMapper.readTree(resultJSON).get("list").toString();
		StudentBook[] books = objectMapper.readValue(listJSON,StudentBook[].class);
		List<StudentBook> asList = Arrays.asList(books);
		System.out.println(asList);*/
		
	}
}
