package com.my.onlinelibrary.validator;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.my.onlinelibrary.dao.UserDAO;
import com.my.onlinelibrary.pojo.LibraryMember;

@Component
public class UserValidator implements Validator {

	@Autowired
	@Qualifier("userDAO")
	UserDAO userDAO;
	
	@Override
	public boolean supports(Class aClass) {
		return aClass.equals(LibraryMember.class);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		
		LibraryMember mem = (LibraryMember) obj;
		
		System.out.println("inside Member validator");
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "libraryUser.firstName", "error.invalid.libraryUser.firstName", "First Name Required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "libraryUser.lastName", "error.invalid.libraryUser.lastName", "Last Name Required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "libraryUser.emailId", "error.invalid.libraryUser.emailId", "Email ID Required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "libraryUser.username", "error.invalid.libraryUser.username", "UserName Required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "libraryUser.password", "error.invalid.libraryUser.password", "Password Required");
		
		String username = mem.getLibraryUser().getUsername();
		System.out.println(username);
		boolean b = userDAO.validateUsername(username);
		boolean e = userDAO.validateEmail(mem.getLibraryUser().getEmailId());
		if(b){
			errors.rejectValue("libraryUser.username", "libraryUser.username-invalid", "Username already exists; enter another value");
		}
		if(e){
			errors.rejectValue("libraryUser.emailId", "libraryUser.emailId-invalid", "Email ID already exists; enter another value");
		}
		
		Pattern p = Pattern.compile("[^\\w ]+");
		 if(p.matcher(mem.getLibraryUser().getFirstName()).find()){
			 errors.rejectValue("libraryUser.firstName", "libraryUser.firstName-invalid", "enter valid value");
		 }
		 if(p.matcher(mem.getLibraryUser().getLastName()).find()){
			 errors.rejectValue("libraryUser.password", "libraryUser.password-invalid", "enter valid value");
		 }
		 if(p.matcher(mem.getLibraryUser().getUsername()).find()){
			 errors.rejectValue("libraryUser.username", "libraryUser.username-invalid", "enter valid value");
		 }
		 if(p.matcher(mem.getLibraryUser().getPassword()).find()){
			 errors.rejectValue("libraryUser.password", "libraryUser.password-invalid", "enter valid value");
		 }
		
	}

}
