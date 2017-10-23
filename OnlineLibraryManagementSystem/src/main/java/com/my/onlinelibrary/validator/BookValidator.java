package com.my.onlinelibrary.validator;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.my.onlinelibrary.dao.BookDAO;
import com.my.onlinelibrary.pojo.Book;

@Component
public class BookValidator implements Validator{

	@Autowired
	@Qualifier("bookDAO")
	BookDAO bookDAO;
	
	@Override
	public boolean supports(Class aClass) {
		return aClass.equals(Book.class);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		Book book = (Book) obj;
		System.out.println("inside Book validator");
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "error.invalid.title", "Title Required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "isbn", "error.invalid.isbn", "ISBN Required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "authors", "error.invalid.author", "Authors Required");
		
		String isbn = book.getIsbn();
		System.out.println(isbn);
		boolean b = bookDAO.checkISBN(isbn);
		if(b){
			System.out.println("Calling DAO");
			errors.rejectValue("isbn", "isbn-invalid", "ISBN already exists; enter valid value");
		}
		
		
		Pattern p = Pattern.compile("[^\\w\\. ]+");
		Pattern isbnp  = Pattern.compile("\\d{3}[-]\\d{3}[-]\\d{3}");
		 if(!isbnp.matcher(book.getIsbn()).find()){
			 errors.rejectValue("isbn", "isbn-invalid", "Enter valid data!");
		 }
		 if(p.matcher(book.getTitle()).find()){
			 errors.rejectValue("title", "title-invalid", "Enter valid data!");
		 }
		 if(p.matcher(book.getAuthors()).find()){
			 errors.rejectValue("authors", "authors-invalid", "Enter valid data!");
		 }
	}

}
