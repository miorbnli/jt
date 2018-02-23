package com.jt.search.service;

import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.springframework.beans.factory.annotation.Autowired;

import com.jt.dubbo.pojo.Item;
import com.jt.dubbo.service.SearchService;

public class SearchServiceImpl implements SearchService{
	@Autowired
	private HttpSolrServer httpSolrServer;
	@Override
	public List<Item> findItemByKey(String keyword) {
		//定义查询
		SolrQuery query=new SolrQuery();
		
		query.setQuery(keyword);
		//设定分页参数
		query.setStart(0);
		query.setRows(20);
		try {
			QueryResponse queryResponse = httpSolrServer.query(query);
			List<Item> itemList = queryResponse .getBeans(Item.class);
			return itemList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
