package com.my.onlinelibrary.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class BookCirculation {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "circulationID", unique=true, nullable = false)
	private long circulationId;
	
	@ManyToOne
	@JoinColumn(name = "librarymember_id")
	private LibraryMember librarymember;
	
	@OneToOne
	private Book book;
	
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "issue_date")
	private Date issueDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "return_date")
	private Date returnDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "expected_date")
	private Date expectedDate;

	
	public BookCirculation(){
		
	}
	
	public BookCirculation(LibraryMember librarymember, Book book, Date issueDate, Date expectedDate) {
		super();
		this.librarymember = librarymember;
		this.book = book;
		this.issueDate = issueDate;
		this.expectedDate = expectedDate;
	}



	
	public long getCirculationId() {
		return circulationId;
	}

	public void setCirculationId(long circulationId) {
		this.circulationId = circulationId;
	}

	public LibraryMember getLibraryMember() {
		return librarymember;
	}

	public void setLibraryMember(LibraryMember libraryMember) {
		this.librarymember = libraryMember;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public Date getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}

	public Date getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}

	public Date getExpectedDate() {
		return expectedDate;
	}

	public void setExpectedDate(Date expectedDate) {
		this.expectedDate = expectedDate;
	}
	
	
	
}
