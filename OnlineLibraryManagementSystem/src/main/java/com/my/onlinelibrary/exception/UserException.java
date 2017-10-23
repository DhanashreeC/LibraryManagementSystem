package com.my.onlinelibrary.exception;

public class UserException extends Exception{
	public UserException(String message)
	{
		super("LibrarianException-"+message);
	}
	
	public UserException(String message, Throwable cause)
	{
		super("LibrarianException-"+message,cause);
	}
}

