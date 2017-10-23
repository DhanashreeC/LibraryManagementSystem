package com.my.onlinelibrary.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

import com.my.onlinelibrary.exception.BookException;
import com.my.onlinelibrary.exception.UserException;
import com.my.onlinelibrary.pojo.Authority;
import com.my.onlinelibrary.pojo.LibraryMember;
import com.my.onlinelibrary.pojo.LibraryUsers;

public class UserDAO extends DAO {

	public UserDAO() {

	}

	public LibraryUsers checkCredentials(String username, String password) throws UserException {
		try {
			begin();
			Query q = getSession().createQuery("from LibraryUsers where username = :username and password = :password");
			q.setString("username", username);
			q.setString("password", password);
			LibraryUsers user = (LibraryUsers) q.uniqueResult();
			commit();
			if (user != null && user.isActive()) {
				return user;
			}
			return null;

		} catch (HibernateException e) {
			rollback();
			throw new UserException("Could not get user " + username, e);
		}
	}
	
	public boolean validateUsername(String username) {
			begin();
			Query q = getSession().createQuery("from LibraryUsers where username = :username");
			q.setString("username", username);
			List<LibraryUsers> users = q.list();
			if(users.isEmpty()){
				System.out.println("Username does not exists");
				return false;
			}
			else{
				return true;
			}
	}
	
	public boolean validateEmail(String email){
		begin();
		Query q = getSession().createQuery("from LibraryUsers where emailId = :eID");
		q.setString("eID", email);
		List<LibraryUsers> users = q.list();
		if(users.isEmpty()){
			System.out.println("Username does not exists");
			return false;
		}
		else{
			return true;
		}
	}

	public LibraryMember createLibraryMemeber(LibraryMember member) throws UserException {
		try {
			begin();
			System.out.println("inside DAO");

			LibraryUsers libraryuser = new LibraryUsers(member.getLibraryUser().getUsername(),
					member.getLibraryUser().getPassword(), member.getLibraryUser().getFirstName(),
					member.getLibraryUser().getLastName(), member.getLibraryUser().getEmailId(),
					getAuthorityByRole("ROLE_MEMBER"), true);

			getSession().save(libraryuser);
			LibraryMember user = new LibraryMember();
			user.setLibraryUser(libraryuser);
			user.setLatesubmission(0);
			getSession().save(user);
			commit();
			return user;

		} catch (HibernateException e) {
			rollback();
			throw new UserException("Exception while creating user: " + e.getMessage());
		}
	}

	public LibraryMember getLibraryMember(LibraryUsers u) throws UserException {
		Query q = getSession().createQuery("from LibraryMember where libraryUser = :userId");
		q.setLong("userId", u.getUserID());
		LibraryMember m = (LibraryMember) q.uniqueResult();
		return m;
	}

	public LibraryMember getMember(Long id) throws UserException {

		try {
			begin();
			Query q = getSession().createQuery("from LibraryMember where memberID = :i");
			q.setLong("i", id);
			LibraryMember m = (LibraryMember) q.uniqueResult();
			commit();
			System.out.println("Coming out of getmember doa");
			return m;
		} catch (HibernateException e) {
			rollback();
			throw new UserException("Cannot find User role.");
		}

	}
	
	public void updateLibraryMember(LibraryMember m) throws UserException{
		try{
			begin();
			getSession().save(m);
			commit();
		}catch(HibernateException e){
			rollback();
			throw new UserException("Exception while updating user occurence: " + e.getMessage());
		}
		
	}

	public Authority getAuthorityByRole(String role) throws UserException {
		try {
			Query q = getSession().createQuery("from Authority WHERE role LIKE :value");
			q.setString("value", role);
			Authority authority = (Authority) q.uniqueResult();
			return authority;

		} catch (HibernateException e) {
			rollback();
			throw new UserException("Cannot find User role.");
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<LibraryMember> getAllLibraryMembers(){
		begin();
		Criteria memcrit = getSession().createCriteria(LibraryMember.class);
		Criteria usercrit = memcrit.createCriteria("libraryUser");
		LibraryUsers u = new LibraryUsers();
		u.setActive(true);
		usercrit.add(Example.create(u));
		List<LibraryMember> result = memcrit.list();
		commit();
		return result;
	}

}
