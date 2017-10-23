package com.my.onlinelibrary.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.my.onlinelibrary.pojo.Book;
import com.my.onlinelibrary.pojo.BookReservation;
import com.my.onlinelibrary.pojo.LibraryMember;
import com.my.onlinelibrary.exception.BookException;

public class BookDAO extends DAO{
	public BookDAO() {
	}
	
	public List<Book> listAllDistinctBooks() throws BookException{
		try {
            begin();
            Query q = getSession().createQuery("from Book b group by b.title");
            List<Book> books = q.list();
            commit();
            return books;
        } catch (HibernateException e) {
            rollback();
            throw new BookException("Could not retrieve Books", e);
        }
	}

	public Book addBook(Book book) throws BookException{
		try {
			begin();
			System.out.println("inside DAO");
			getSession().save(book);
			commit();
			return book;

		} catch (HibernateException e) {
			rollback();
			throw new BookException("Exception while creating user: " + e.getMessage());
		}
	}
	
	public void addBooks(List<Book> books) throws BookException {
		try{
			begin();
			for(Book book : books){
				getSession().save(new Book(book));
			}
			commit();
		}
		catch(HibernateException e){
			rollback();
			throw new BookException("Exception while creating book: " + e.getMessage());
		}
		
	}

	public List<Book> getAvailableBooks() throws BookException{
		try {
            begin();
            String hql = "select b from Book b where b.available = true group by b.title";
            Query q = getSession().createQuery(hql);
            List<Book> books = q.list();
            commit();
            return books;
        } catch (HibernateException e) {
            rollback();
            throw new BookException("No books available", e);
        }
	}

	public Book getBookRequested(Long bookId) throws Exception{
		try{
			Query q = getSession().createQuery("from Book where bookId = :id");
			q.setLong("id" , bookId);
			Book b = (Book) q.uniqueResult();
			return b;
		}catch(HibernateException e){
			rollback();
			throw new BookException("Exception while retriving book: " + e.getMessage());
		}
	}

	public BookReservation createReservation(BookReservation reserveBook) throws Exception {
		try {
			begin();
			System.out.println("inside DAO");
			reserveBook.setRequestStatus("Pending");
			getSession().save(reserveBook);
			commit();
			return reserveBook;

		} catch (HibernateException e) {
			rollback();
			throw new BookException("Exception while creating book request: " + e.getMessage());
		}
	}
	
	public void updateBookAvailability(Book book) throws BookException{
		try{
			begin();
			getSession().update(book);
			commit();
		}
		catch(HibernateException e){
			rollback();
			throw new BookException("Exception while updating book availability: " + e.getMessage());
		}
	}
	
	public boolean checkISBN(String isbnNo){
		begin();
		Criteria bookcrit = getSession().createCriteria(Book.class);
		System.out.println("inside bookDao checkISBN method");
		bookcrit.add(Restrictions.like("isbn", isbnNo, MatchMode.EXACT));
		if(bookcrit.list().isEmpty()){
			return false;
		}else{
			return true;
		}
	}
}
