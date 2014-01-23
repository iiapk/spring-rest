package com.iiapk.rest.xml;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
@XStreamAlias("bookList")
public class BookList {
	
	private int size = 5;
	
	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	List<Book> books = new ArrayList<Book>();

	public List<Book> getBooks() {
		return books;
	}

	public void setBooks(List<Book> books) {
		this.books = books;
	}
	
	public void addBooks(Book book) {
		this.books.add(book);
	}
	
	public static BookList getList(){
		Book book = new Book();
		book.setAuthor("hey");
		book.setTitle("title");
		Book book1 = new Book();
		book1.setAuthor("hey");
		book1.setTitle("title");
		BookList bookList = new BookList();
		bookList.getBooks().add(book);
		bookList.getBooks().add(book1);
		bookList.setSize(2);
		return bookList;
	}
	
}
