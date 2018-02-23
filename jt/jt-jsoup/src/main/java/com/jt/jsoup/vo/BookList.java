package com.jt.jsoup.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jt.jsoup.pojo.StudentBook;

@JsonIgnoreProperties(ignoreUnknown=true)
public class BookList {
	//封装课程集合
	private List<StudentBook> list;
	private Integer result;
	public List<StudentBook> getList() {
		return list;
	}
	public void setList(List<StudentBook> list) {
		this.list = list;
	}
	public Integer getResult() {
		return result;
	}
	public void setResult(Integer result) {
		this.result = result;
	}
	@Override
	public String toString() {
		return "BookList [list=" + list + ", result=" + result + "]";
	}
	
}
