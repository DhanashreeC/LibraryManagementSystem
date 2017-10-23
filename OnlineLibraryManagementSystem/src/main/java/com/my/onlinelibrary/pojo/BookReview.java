package com.my.onlinelibrary.pojo;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class BookReview {
	
	public BookReview() {
		
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "reviewID", unique=true, nullable = false)
	private long reviewID;
	
	@ManyToOne
	@JoinColumn(name = "librarymember_id")
	private LibraryMember librarymember;
	
	@OneToOne
	private Book book;
	
	@Column(name="comment")
	private String comment;
	
	@Column(name="rating")
	private int rating;

	public long getReviewID() {
		return reviewID;
	}

	public void setReviewID(long reviewID) {
		this.reviewID = reviewID;
	}

	public LibraryMember getLibrarymember() {
		return librarymember;
	}

	public void setLibrarymember(LibraryMember librarymember) {
		this.librarymember = librarymember;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}
	
	
	
}
