package jt.http.test;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class TestHttpClient {
	//@Test
	public void testGet() throws ClientProtocolException, IOException{
		//1.创建HttpClient对象用来发出uhttp请求
		CloseableHttpClient httpClient = HttpClients.createDefault();
		//2.创建url访问网站的地址
		String url="http://item.jd.com/1221882.html";
		//3定义请求方式
		HttpGet httpGet = new HttpGet(url);
		//4.通过httpClient发出get请求
		CloseableHttpResponse response = httpClient.execute(httpGet);
		//5.判断请求是否有效,获取响应页面的状态
		if(response.getStatusLine().getStatusCode()==200){
			//6.将实体Entity转化为String
			String result = EntityUtils.toString(response.getEntity());
			//7输出结果
			System.out.println(result);
		}
	}
}
