package com.my.onlinelibrary.pojo;

import java.util.Date;

import javax.persistence.CascadeType;
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
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class BookReservation {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "reservationID", unique=true, nullable = false)
	private long reservationID;
	
	@ManyToOne
	@JoinColumn(name = "librarymember_id")
	private LibraryMember librarymember;
	
	@OneToOne
	private Book book;
	
	@Column(name="requeststatus")
	private String requestStatus;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "request_date")
	private Date requestDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="till_date")
	private Date tillDate;
	
	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "update_date")
	private Date updateDate;

	public BookReservation() {
		super();
	}

	public long getReservationID() {
		return reservationID;
	}

	public void setReservationID(long reservationID) {
		this.reservationID = reservationID;
	}

	public String getRequestStatus() {
		return requestStatus;
	}

	public void setRequestStatus(String requestStatus) {
		this.requestStatus = requestStatus;
	}

	public Date getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
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

	public Date getTillDate() {
		return tillDate;
	}

	public void setTillDate(Date tillDate) {
		this.tillDate = tillDate;
	}
	
	
	
}
