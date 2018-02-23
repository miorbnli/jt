package com.jt.jsoup.service;

import java.util.List;

import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.jsoup.mapper.StudentBookMapper;
import com.jt.jsoup.mapper.StudentSectionMapper;
import com.jt.jsoup.pojo.StudentBook;
import com.jt.jsoup.pojo.StudentSection;
import com.jt.jsoup.vo.BookList;

@Service
public class StudentBookServiceImpl implements StudentBookService {
	private static ObjectMapper objectMapper=new ObjectMapper();
	@Autowired
	private StudentSectionMapper studentSectionMapper;
	@Autowired
	private StudentBookMapper studentBookMapper ;
	@Override
	public void insertBook(String url) {
		
		try {
			String resultJSON = Jsoup.connect(url).ignoreContentType(true).execute().body();
			BookList bookList = objectMapper.readValue(resultJSON,BookList.class);	
			
			for(StudentBook book: bookList.getList()){
					if(book.getSections()!=null){
						//获取list章节
						List<StudentSection> sList=book.getSections();
						for(StudentSection section :sList){
							//进行主外键关联
							section.setBookId(book.getId());
							studentSectionMapper.insert(section);
						}
					}
					 studentBookMapper.insert(book);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
