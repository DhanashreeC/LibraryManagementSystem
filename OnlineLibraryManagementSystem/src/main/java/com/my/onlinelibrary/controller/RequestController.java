package com.my.onlinelibrary.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.my.onlinelibrary.dao.BookDAO;
import com.my.onlinelibrary.dao.CirculationDAO;
import com.my.onlinelibrary.dao.RequestDAO;
import com.my.onlinelibrary.dao.UserDAO;
import com.my.onlinelibrary.json.JsonUtils;
import com.my.onlinelibrary.pojo.Book;
import com.my.onlinelibrary.pojo.BookCirculation;
import com.my.onlinelibrary.pojo.BookReservation;
import com.my.onlinelibrary.pojo.LibraryMember;
import com.my.onlinelibrary.pojo.LibraryUsers;

@Controller
public class RequestController {

	@Autowired
	@Qualifier("bookrequestDAO")
	RequestDAO bookrequestDAO;

	@Autowired
	@Qualifier("userDAO")
	UserDAO userDAO;

	@Autowired
	@Qualifier("bookDAO")
	BookDAO bookDAO;

	@Autowired
	@Qualifier("circulationDAO")
	CirculationDAO circulationDAO;

	@RequestMapping(value = "/admin/viewRequests.htm", method = RequestMethod.GET)
	protected ModelAndView displayOpenRequests() throws Exception {

		ModelAndView model = new ModelAndView();
		List<BookReservation> bookRequests = bookrequestDAO.getPendingRequest("Pending");
		model.addObject("bookRequests", bookRequests);
		model.setViewName("pending-requests");
		return model;
	}

	@RequestMapping(value = "/admin/viewmemberinfo.htm", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public String getUserInfo(@RequestParam String memberID, HttpServletRequest request) throws Exception {

		String result;
		System.out.println(memberID);
		Long id = Long.parseLong(memberID);
		LibraryMember member = userDAO.getMember(id);
		System.out.println("Member ID: " + member.getMemberID());
		result = "{" + JsonUtils.toJsonField("memberId", Long.toString(member.getMemberID()))
				+ (", " + JsonUtils.toJsonField("memberfirstname", member.getLibraryUser().getFirstName()))
				+ (", " + JsonUtils.toJsonField("memberlastname", member.getLibraryUser().getLastName()))
				+ (", " + JsonUtils.toJsonField("latesubmission", Integer.toString(member.getLatesubmission())))
				+ (", " + JsonUtils.toJsonField("emailId", member.getLibraryUser().getEmailId())) + "}";
		return result;

	}

	@RequestMapping(value = "/admin/approverequest.htm", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Long approveRequest(@RequestParam String requestID, HttpServletRequest request) throws Exception {
		System.out.println(requestID);
		Long id = Long.parseLong(requestID);
		BookReservation br = bookrequestDAO.getRequest(id);
		System.out.println("Request ID: " + br.getReservationID());
		bookrequestDAO.updateStatus(br, "Approved");
		BookCirculation bc = new BookCirculation(br.getLibrarymember(), br.getBook(), br.getRequestDate(), br.getTillDate());
		bc = circulationDAO.addBookCirculation(bc);
		return bc.getCirculationId();
	}

	@RequestMapping(value = "/admin/rejectrequest.htm", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public int deleteRequest(@RequestParam String requestID, HttpServletRequest request) throws Exception {
		System.out.println(requestID);
		Long id = Long.parseLong(requestID);
		BookReservation br = bookrequestDAO.getRequest(id);
		System.out.println("Request ID: " + br.getReservationID());
		Book b = br.getBook();
		b.setAvailable(true);
		bookDAO.updateBookAvailability(b);
		bookrequestDAO.updateStatus(br, "Declined");
		br.getBook().setAvailable(true);
		int data = 1;
		return data;
	}

	@RequestMapping(value = "/member/viewraisedrequest.htm", method = RequestMethod.GET)
	public ModelAndView getUserRequests(HttpServletRequest request) throws Exception {
		HttpSession session = request.getSession();
		LibraryUsers u = (LibraryUsers) session.getAttribute("user");
		LibraryMember mem = userDAO.getLibraryMember(u);
		List<BookReservation> result = bookrequestDAO.getUserRequests(mem);
		return new ModelAndView("book-requested", "bookreservationlist", result);
	}

}
