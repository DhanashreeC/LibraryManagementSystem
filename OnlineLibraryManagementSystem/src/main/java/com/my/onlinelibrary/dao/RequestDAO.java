package com.my.onlinelibrary.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import com.my.onlinelibrary.exception.BookException;
import com.my.onlinelibrary.exception.RequestException;
import com.my.onlinelibrary.pojo.BookReservation;
import com.my.onlinelibrary.pojo.LibraryMember;

public class RequestDAO extends DAO{

	public List<BookReservation> getPendingRequest(String status) throws Exception {
		try{
			begin();
			Query q = getSession().createQuery("from BookReservation where requestStatus = :status");
			q.setString("status", status);
			List<BookReservation> requests = q.list();
			return requests;
		}catch(HibernateException e){
			rollback();
			throw new RequestException("Exception while retreiving requests: " + e.getMessage());
		}
	}
	
	public BookReservation getRequest(Long id) throws Exception{
		try{
			begin();
			Query q = getSession().createQuery("from BookReservation where reservationID = :id");
			q.setLong("id", id);
			BookReservation r = (BookReservation) q.uniqueResult();
			return r;
		}catch(HibernateException e){
			rollback();
			throw new RequestException("Exception while retreiving requests 1: " + e.getMessage());
		}
	}
	
	public List<BookReservation> getUserRequests(LibraryMember mem) throws Exception{
		try{
			begin();
			Query q = getSession().createQuery("from BookReservation br where librarymember.memberID =:memId order by br.updateDate desc");
			q.setLong("memId", mem.getMemberID());
			List<BookReservation> result = q.list();
			return result;
		}catch(HibernateException e){
			rollback();
			throw new RequestException("Exception while retreiving requests: " + e.getMessage());
		}
	}
	
	public void updateStatus(BookReservation b, String status) throws Exception{
		try{
			begin();
			b.setRequestStatus(status);
			getSession().update(b);
			commit();
		}catch(HibernateException e){
			throw new RequestException("Exception while updating request: " + e.getMessage());
		}
	}

}
