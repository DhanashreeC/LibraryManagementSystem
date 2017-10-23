package com.my.onlinelibrary.interceptor;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.my.onlinelibrary.pojo.LibraryUsers;

public class MemberInterceptor extends HandlerInterceptorAdapter {
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
		System.out.println("Inside Admin Interceptor");
		LibraryUsers user = (LibraryUsers) session.getAttribute("user");
		if(user != null && user.getAuthority().getRole().equalsIgnoreCase("ROLE_MEMBER")){
			return true;
		}
		
		System.out.println("no user");
		
		RequestDispatcher rd=request.getRequestDispatcher(errorPage);  
		rd.forward(request, response);
		return false;
	}
}
