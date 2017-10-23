package com.my.onlinelibrary.pojo;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class LibraryMember {
	


	public LibraryMember() {
		super();
	}
	
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "memberID", unique=true, nullable = false)
	private long memberID;
	
	@OneToOne
	private LibraryUsers libraryUser;

	
	@Column(name="latesubmission")
	private int latesubmission;
	
	
	@OneToMany(mappedBy="librarymember", cascade=CascadeType.ALL)
	private List<BookReservation> reservation;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="librarymember") 
	private List<BookReview> reviews;
	

	@OneToMany(cascade=CascadeType.ALL, mappedBy="librarymember")
	private List<BookCirculation> bookcirculation;
	
	public int getLatesubmission() {
		return latesubmission;
	}

	public void setLatesubmission(int latesubmission) {
		this.latesubmission = latesubmission;
	}

	public List<BookReview> getReviews() {
		return reviews;
	}

	public void setReviews(List<BookReview> reviews) {
		this.reviews = reviews;
	}

	public long getMemberID() {
		return memberID;
	}

	public void setMemberID(long memberID) {
		this.memberID = memberID;
	}

	public List<BookReservation> getReservation() {
		return reservation;
	}

	public void setReservation(List<BookReservation> reservation) {
		this.reservation = reservation;
	}

	public LibraryUsers getLibraryUser() {
		return libraryUser;
	}

	public void setLibraryUser(LibraryUsers libraryUser) {
		this.libraryUser = libraryUser;
	}

	public List<BookCirculation> getBookcirculation() {
		return bookcirculation;
	}

	public void setBookcirculation(List<BookCirculation> bookcirculation) {
		this.bookcirculation = bookcirculation;
	}
	
	

}
