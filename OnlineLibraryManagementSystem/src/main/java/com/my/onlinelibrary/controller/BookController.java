package com.my.onlinelibrary.controller;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.my.onlinelibrary.dao.BookDAO;
import com.my.onlinelibrary.dao.CirculationDAO;
import com.my.onlinelibrary.dao.UserDAO;
import com.my.onlinelibrary.exception.BookException;
import com.my.onlinelibrary.json.JsonUtils;
import com.my.onlinelibrary.pojo.Book;
import com.my.onlinelibrary.pojo.BookCirculation;
import com.my.onlinelibrary.pojo.BookReservation;
import com.my.onlinelibrary.pojo.LibraryMember;
import com.my.onlinelibrary.pojo.LibraryUsers;
import com.my.onlinelibrary.validator.BookReservationValidator;
import com.my.onlinelibrary.validator.BookValidator;

@Controller
public class BookController {

	@Autowired
	@Qualifier("bookDAO")
	BookDAO bookDAO;

	@Autowired
	@Qualifier("userDAO")
	UserDAO userDAO;

	@Autowired
	@Qualifier("circulationDAO")
	CirculationDAO bookCirculationDAO;

	@Autowired
	@Qualifier("bookValidator")
	BookValidator validator;

	@Autowired
	@Qualifier("bookreservationValidator")
	BookReservationValidator brvalidator;

	@InitBinder("book")
	private void initBookBinder(WebDataBinder binder) {
		binder.setValidator(validator);
	}

	@InitBinder("reserveBook")
	private void initReservationBinder(WebDataBinder binder) {
		binder.setValidator(brvalidator);
	}

	@Autowired
	ServletContext servletContext;

	private static String UPLOAD_LOCATION = "C:/images/books";

	// display Add new books page to admin
	@RequestMapping(value = "/admin/addBooks.htm", method = RequestMethod.GET)
	protected ModelAndView addBooksForm() throws Exception {
		return new ModelAndView("add-books", "book", new Book());
	}

	// add new books to the system
	@RequestMapping(value = "/admin/addBooks.htm", method = RequestMethod.POST)
	public String handleUpload(HttpServletRequest request, @ModelAttribute("book") Book book, BindingResult result) {

		validator.validate(book, result);

		if (result.hasErrors()) {
			return "add-books";
		}

		HttpSession session = request.getSession();

		try {
			int countOfBooks = Integer.parseInt(request.getParameter("totalbooks"));
			CommonsMultipartFile photoInMemory = book.getPhoto();
			String fileName = photoInMemory.getOriginalFilename();
			File localFile = new File(UPLOAD_LOCATION + File.separator + fileName);
			photoInMemory.transferTo(localFile);

			book.setFilename(File.separator + "images" + File.separator + fileName);
			System.out.println(book.getFilename());
			System.out.println("File is stored at" + localFile.getPath());
			System.out.print("added new Book");
			List<Book> books = new ArrayList<Book>();

			for (int i = 0; i < countOfBooks; i++) {
				System.out.println(i + " " + countOfBooks);
				books.add(book);
			}
			System.out.println(books.size());
			bookDAO.addBooks(books);
			List<Book> updatedbooks = bookDAO.listAllDistinctBooks();
			session.setAttribute("books", updatedbooks);

		} catch (IllegalStateException e) {
			System.out.println("*** IllegalStateException: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("*** IOException: " + e.getMessage());
		} catch (BookException e) {
			e.printStackTrace();
		}

		return "admin-home";
	}

	@RequestMapping(value = "/admin/returnbook.htm", method = RequestMethod.GET)
	protected ModelAndView returnBookform() throws Exception {
		List<BookCirculation> result = bookCirculationDAO.getAllBooksInCirculation();
		return new ModelAndView("return-book", "bookcirculationlist", result);
	}

	@RequestMapping(value = "/admin/getBookCirculation.htm", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	protected String getBookbyID(HttpServletRequest request, @RequestParam String bookid) throws Exception {
		String result;
		System.out.println("Inside getBookid" + bookid);
		long bookId = Long.parseLong(bookid);

		BookCirculation bc = bookCirculationDAO.getBookCirculationByBookID(bookId);

		if (bc == null) {
			result = "{" + JsonUtils.toJsonField("errormessage", "No circulation request found") + "}";
			return result;
		}

		request.getSession().setAttribute("bookCirculation", bc);
		DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");

		result = "{" + JsonUtils.toJsonField("circulationId", Long.toString(bc.getCirculationId()))
				+ (", " + JsonUtils.toJsonField("booktitle", bc.getBook().getTitle()))
				+ (", " + JsonUtils.toJsonField("libraryuserfirstname",
						bc.getLibraryMember().getLibraryUser().getFirstName()))
				+ (", " + JsonUtils.toJsonField("libraryuserlastname",
						bc.getLibraryMember().getLibraryUser().getLastName()))
				+ (", " + JsonUtils.toJsonField("issuedate", dateFormat.format(bc.getIssueDate())))
				+ (", " + JsonUtils.toJsonField("expecteddate", dateFormat.format(bc.getExpectedDate()))) + "}";
		return result;

	}

	@RequestMapping(value = "/admin/confirmbookreturn.htm", method = RequestMethod.GET)
	public ModelAndView confirmBookReturn(HttpServletRequest request) throws Exception {
		System.out.println("Inside Return book");
		HttpSession session = request.getSession();
		BookCirculation bc = (BookCirculation) session.getAttribute("bookCirculation");
		BookCirculation updatedbc = bookCirculationDAO.getBookCirculationByBookID(bc.getBook().getBookId());

		Book b = updatedbc.getBook();
		b.setAvailable(true);
		bookDAO.updateBookAvailability(b);

		if (updatedbc.getExpectedDate().before(new Date())) {
			LibraryMember mem = updatedbc.getLibraryMember();
			mem.setLatesubmission(mem.getLatesubmission() + 1);
			userDAO.updateLibraryMember(mem);
		}

		updatedbc.setReturnDate(new Date());
		bookCirculationDAO.updateBookCirculation(updatedbc);

		return new ModelAndView("return-book", "successmessage", "Book Returned successfully");
	}

	@RequestMapping(value = "/member/addBookRequest.htm", method = RequestMethod.GET)
	public ModelAndView getAvailableBooks(HttpServletRequest request) throws Exception {
		List<Book> availableBooks = new ArrayList<Book>();
		try {
			availableBooks = bookDAO.getAvailableBooks();
		} catch (BookException e) {
			e.printStackTrace();
		}

		ModelAndView model = new ModelAndView();

		model.addObject("availableBooks", availableBooks);
		BookReservation br = new BookReservation();
		LibraryUsers lb = (LibraryUsers) request.getSession().getAttribute("user");
		br.setLibrarymember(userDAO.getLibraryMember(lb));
		model.addObject("reserveBook", br);
		model.setViewName("books-available");

		return model;
	}

	@RequestMapping(value = "/member/addBookRequest.htm", method = RequestMethod.POST)
	public ModelAndView registerBookRequest(HttpServletRequest request,
			@ModelAttribute("reserveBook") BookReservation reserveBook, BindingResult result) throws Exception {

		HttpSession session = request.getSession();
		brvalidator.validate(reserveBook, result);

		if (result.hasErrors()) {
			return new ModelAndView("books-available", "reserveBook", reserveBook);
		}

		ModelAndView model = new ModelAndView();
		LibraryUsers u = (LibraryUsers) session.getAttribute("user");
		LibraryMember mem = userDAO.getLibraryMember(u);
		reserveBook.setLibrarymember(mem);

		Long bookId = Long.parseLong(request.getParameter("bookid"));
		Book b = bookDAO.getBookRequested(bookId);
		b.setAvailable(false); // setting the book availability as false
		bookDAO.updateBookAvailability(b);
		reserveBook.setBook(b);
		BookReservation r = bookDAO.createReservation(reserveBook);

		model.addObject("requestedBook", r.getBook());
		model.addObject("requestNumber", r);
		List<BookCirculation> bookCirculationList = bookCirculationDAO.getBooksInCirculation(mem);
		session.setAttribute("bookcirculationlist", bookCirculationList);
		model.setViewName("user-home");

		return model;
	}

}
