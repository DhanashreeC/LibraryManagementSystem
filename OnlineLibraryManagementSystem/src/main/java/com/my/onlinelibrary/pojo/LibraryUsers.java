package com.my.onlinelibrary.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Type;

@Entity
public class LibraryUsers {
	
	public LibraryUsers() {
		// TODO Auto-generated constructor stub
	}
	
	
	
	public LibraryUsers(String username, String password, String firstName, String lastName, String emailId,
			Authority authority, boolean active) {
		super();
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailId = emailId;
		this.authority = authority;
		this.active = active;
	}



	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "userID", unique=true, nullable = false)
	private long userID;
	
	@Column(name = "userName")
	private String username;

	@Column(name = "password")
	private String password;
	
	@Column(name = "firstName")
	private String firstName;
	
	@Column(name ="lastName")
	private String lastName;
	
	@Column(name = "emailId")
	private String emailId;
	
	@ManyToOne
	private Authority authority;
	
	@Type(type = "true_false")
	private boolean active;

	public long getUserID() {
		return userID;
	}

	public void setUserID(long userID) {
		this.userID = userID;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public Authority getAuthority() {
		return authority;
	}

	public void setAuthority(Authority authority) {
		this.authority = authority;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
	
}
