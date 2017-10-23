package com.my.onlinelibrary.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.my.onlinelibrary.dao.UserDAO;
import com.my.onlinelibrary.exception.UserException;
import com.my.onlinelibrary.pojo.LibraryMember;
import com.my.onlinelibrary.validator.UserValidator;

@Controller
public class MemberController {

	@Autowired
	@Qualifier("userDAO")
	UserDAO userDAO;
	
	@Autowired
	@Qualifier("userValidator")
	UserValidator userValidator;
	
	@InitBinder
	private void initBinder(WebDataBinder binder) {
		binder.setValidator(userValidator);
	}
	
	@RequestMapping(value = "/admin/addMembers.htm", method = RequestMethod.GET)
	protected ModelAndView addMemberForm() throws Exception{
		
		ModelAndView model = new ModelAndView();
		model.addObject("member", new LibraryMember());
		model.setViewName("add-member");
		return model;
	}
	
	@RequestMapping(value = "/admin/addMembers.htm", method = RequestMethod.POST)
	protected ModelAndView addMember(HttpServletRequest request,  @ModelAttribute("member") LibraryMember member, BindingResult result){
		HttpSession session = request.getSession();
		userValidator.validate(member, result);
		
		if (result.hasErrors()) {
			return new ModelAndView("add-member", "member", member);
		}
		try {
			LibraryMember createdMember = userDAO.createLibraryMemeber(member);
			List<LibraryMember> members = userDAO.getAllLibraryMembers();
			session.setAttribute("members", members);
		} catch (UserException e) {
			e.printStackTrace();
		}
		return new ModelAndView("admin-home");
	}
}
