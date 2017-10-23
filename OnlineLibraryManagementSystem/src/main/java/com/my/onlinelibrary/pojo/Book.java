package com.my.onlinelibrary.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

@Entity
public class Book {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "bookID", unique=true, nullable = false)
	private long bookId;
	
	@Column(name= "isbn")
	private String isbn;
	
	@Column(name= "title")
    private String title;
	
	@Column(name= "authors")
    private String authors;
	
	@Column(name = "filename")
	private String filename; 
	
	@Type(type = "true_false")
	private boolean available;
	
	@Transient
	private CommonsMultipartFile photo;
	
	public Book() {
		super();
	}
	
	public Book(Book b) {
		this.isbn = b.isbn;
		this.filename = b.filename;
		this.authors = b.authors;
		this.title = b.title;
		this.available = true;
	}
	
		
	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}


	public long getBookId() {
		return bookId;
	}
	
	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthors() {
		return authors;
	}

	public void setAuthors(String authors) {
		this.authors = authors;
	}

	public CommonsMultipartFile getPhoto() {
		return photo;
	}
	public void setPhoto(CommonsMultipartFile photo) {
		this.photo = photo;
	}

	public void setBookId(long bookId) {
		this.bookId = bookId;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}
	
	
}
