package com.my.onlinelibrary.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.my.onlinelibrary.dao.BookDAO;
import com.my.onlinelibrary.dao.CirculationDAO;
import com.my.onlinelibrary.dao.UserDAO;
import com.my.onlinelibrary.exception.BookException;
import com.my.onlinelibrary.exception.UserException;
import com.my.onlinelibrary.pojo.Book;
import com.my.onlinelibrary.pojo.BookCirculation;
import com.my.onlinelibrary.pojo.LibraryMember;
import com.my.onlinelibrary.pojo.LibraryUsers;

@Controller
public class MainController {
	
	@Autowired
	@Qualifier("bookDAO")
	BookDAO bookDAO;

	@Autowired
	@Qualifier("userDAO")
	UserDAO userDAO;
	
	@Autowired
	@Qualifier("circulationDAO")
	CirculationDAO bookCirculationDAO;

	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView home(HttpServletRequest request) {
		request.getSession().invalidate();
		ModelAndView model = new ModelAndView("home");
		List<Book> books;
		try {
			books = bookDAO.listAllDistinctBooks();
			model.addObject("books", books);
			
		} catch (BookException e) {
			
			e.printStackTrace();
		}
		return model;
	}
	
	@RequestMapping(value="/login", method = RequestMethod.POST)
	public ModelAndView checkLogin(HttpServletRequest request){
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		HttpSession session = (HttpSession) request.getSession();
		ModelAndView result = new ModelAndView();
		try {
			List<Book> books = bookDAO.getAvailableBooks();
			session.setAttribute("books", books);
		} catch (BookException e1) {
			e1.printStackTrace();
		}
		
		try {
			System.out.print("loginUser");
			LibraryUsers user = userDAO.checkCredentials(username, password);
			
			if(user == null){
				System.out.println("UserName/Password does not exist");
				result.addObject("errorMessage", "UserName/Password does not exist");
				result.setViewName("home");
				
			}
			else if(user.getAuthority().getRole().equalsIgnoreCase("ROLE_ADMIN")){
				session.setAttribute("user", user);
				List<LibraryMember> members = userDAO.getAllLibraryMembers();
				session.setAttribute("members", members);
				result.setViewName("admin-home");
			}else if(user.getAuthority().getRole().equalsIgnoreCase("ROLE_MEMBER")){
				session.setAttribute("user", user);
				LibraryMember mem = userDAO.getLibraryMember(user);
				List<BookCirculation> bookCirculationList = bookCirculationDAO.getBooksInCirculation(mem);
				session.setAttribute("bookcirculationlist", bookCirculationList);
				result.setViewName("user-home");
			}
			
			return result;
				
		} catch (UserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result.addObject("someerror", "Some error occured");
			result.setViewName("error-page");
			return result;
		}
		
	}
	

	
	@RequestMapping(value={"/home.htm","/login"}, method = RequestMethod.GET)
	public String returnHome(HttpServletRequest request, Model model){
		HttpSession session = request.getSession();
		LibraryUsers u = (LibraryUsers) session.getAttribute("user");
		if(u == null){
			return "unauthorizedaccess";
		}
		if(u.getAuthority().getRole().equalsIgnoreCase("ROLE_ADMIN")){
			return "admin-home";
		}else{
			return "user-home";
		}
	}
}
