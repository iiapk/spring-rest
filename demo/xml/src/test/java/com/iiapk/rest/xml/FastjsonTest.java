package com.iiapk.rest.xml;

import org.junit.Before;
import org.junit.Test;

import com.alibaba.fastjson.JSON;

public class FastjsonTest {
	
	 private Book book = null;
	
	@Before
    public void init() {
		book = new Book();
        book.setTitle("china");
        book.setAuthor("jack@email.com");
        book.setYear("1");
        book.setPrice("jack");
        book.setBookmark(new Bookmark("test1", "test2"));
	}
	
	@Test
	public void writeEntity2JSON() {
		System.out.println(JSON.toJSONString(book)); 
	}
	
	@Test
	public void writeList2JSON() {
		System.out.println(JSON.toJSONString(BookList.getList())); 
	}

}
