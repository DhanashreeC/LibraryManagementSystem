package com.my.onlinelibrary.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.my.onlinelibrary.pojo.BookReservation;

@Component
public class BookReservationValidator implements Validator{

	@Override
	public boolean supports(Class aClass) {
		return aClass.equals(BookReservation.class);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		// TODO Auto-generated method stub
		BookReservation br = (BookReservation) obj;
		System.out.println("inside Book Reservation validator");
		
		if(br.getTillDate().before(br.getRequestDate()) || br.getTillDate().compareTo(br.getRequestDate()) == 0){
			errors.rejectValue("tillDate", "tillDate-invalid", "Please choose a valid date");
		}
		
	}

}