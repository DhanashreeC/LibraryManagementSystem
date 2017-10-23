package com.my.onlinelibrary.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.my.onlinelibrary.pojo.LibraryUsers;

public class UserInterceptor extends HandlerInterceptorAdapter {

	String errorPage;

	public String getErrorPage() {
		return errorPage;
	}

	public void setErrorPage(String errorPage) {
		this.errorPage = errorPage;
	}
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		HttpSession session = (HttpSession) request.getSession();
		LibraryUsers user = (LibraryUsers) session.getAttribute("user");
		if(user != null){
			return true;
		}
		
		System.out.println("no user");
		response.sendRedirect(errorPage);
		return false;
	}
}
