package com.my.onlinelibrary.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;

import com.my.onlinelibrary.exception.BookException;
import com.my.onlinelibrary.exception.UserException;
import com.my.onlinelibrary.pojo.Book;
import com.my.onlinelibrary.pojo.BookCirculation;
import com.my.onlinelibrary.pojo.LibraryMember;

public class CirculationDAO extends DAO{
	public BookCirculation addBookCirculation(BookCirculation bc) throws BookException{
		try {
			begin();
			System.out.println("inside CirculationDAO - addBookCirculation");
			getSession().save(bc);
			commit();
			return bc;

		} catch (HibernateException e) {
			rollback();
			throw new BookException("Exception while creating user: " + e.getMessage());
		}
	}
	
	public List<BookCirculation> getBooksInCirculation(LibraryMember m){
		begin();
		System.out.println("inside CirculationDAO - getBooksIncirculation");
		System.out.println("Member Id: "+ m.getMemberID());
//		Criteria bookcirCrit  = getSession().createCriteria(BookCirculation.class);
//		Criteria memCrit = bookcirCrit.createCriteria("libraryMember");
//		LibraryMember mem = new LibraryMember();
//		mem.setMemberID(m.getMemberID());
//		mem.setLibraryUser(m.getLibraryUser());
//		memCrit.add(Example.create(mem));
//		List<BookCirculation> bookCirculationList = bookcirCrit.list();
		
		Query q = getSession().createQuery("from BookCirculation where librarymember.memberID =:memId and returnDate is null");
		q.setLong("memId", m.getMemberID());
		List<BookCirculation> bookCirculationList = q.list();
		System.out.println("Size of circulation list: " + bookCirculationList.size());
		return bookCirculationList;
	}
	
	public List<BookCirculation> getAllBooksInCirculation(){
		begin();
		Query q = getSession().createQuery("from BookCirculation where returnDate is null");
		List<BookCirculation> result = q.list();
		System.out.println(result.size());
		commit();
		return result;
	}
	
	public BookCirculation getBookCirculationByBookID(long bookId){
		begin();
		Query q = getSession().createQuery("from BookCirculation where book.bookId = :bid and returnDate is null");
		q.setLong("bid",bookId);
		BookCirculation bc = (BookCirculation) q.uniqueResult();
		return bc;
	}
	
	public void updateBookCirculation(BookCirculation bc) throws BookException{
		try{
			begin();
			getSession().update(bc);
			commit();
		}catch(HibernateException e){
			rollback();
			throw new BookException("Exception while updating user occurence: " + e.getMessage());
		}
		
	}
}
