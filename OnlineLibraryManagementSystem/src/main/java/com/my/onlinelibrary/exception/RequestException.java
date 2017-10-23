package com.my.onlinelibrary.exception;

public class RequestException extends Exception{
	public RequestException(String message)
	{
		super("RequestException-"+message);
	}
	
	public RequestException(String message, Throwable cause)
	{
		super("RequestException-"+message,cause);
	}
}
