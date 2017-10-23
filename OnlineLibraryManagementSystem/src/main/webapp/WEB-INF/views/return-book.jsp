<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
<head>
<title>AdminPage</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

<style>
.navbar {
	margin-bottom: 50px;
	border-radius: 0;
}

.jumbotron {
	margin-bottom: 0;
}

footer {
	background-color: #f2f2f2;
	padding: 25px;
}
#frame {
	border: 1px solid grey;
	background-size: 100%;
	background-image:
		url(/images/background.jpg);
	background-repeat: no-repeat;
	background-position: center;
	background-color: red;
}
#textcenter{
	padding-left: 5%;
	color:  white;
}
</style>
<script>
	$(document)
			.on(
					'click',
					'#booksearch',
					function(event) {
						var infoForm = $('#bookcirculationresult');
						var id = $("#bookid").val();
						var jsonObj = {
							"bookid" : id
						};
						$
								.ajax({
									url : "getBookCirculation.htm",
									data : jsonObj,
									dataType : 'json',
									type : 'post',
									success : function(data) {
										if (data.errormessage == 'No circulation request found') {
											alert("No circulation request found");
										} else {
											var htmlData = '<h2>Book circulation Details:</h2><ul class="list-group"><li class="list-group-item"><b>Circulation Id:</b>';
											htmlData += data.circulationId;
											htmlData += '</li><li class="list-group-item"><b>Book Title :</b>';
											htmlData += data.booktitle;
											htmlData += '</li><li class="list-group-item"><b>Member name :</b>';
											htmlData += data.libraryuserfirstname
													+ ' '
													+ data.libraryuserlastname;
											htmlData += '</li><li class="list-group-item"><b>Date of Issue :</b>';
											htmlData += data.issuedate;
											htmlData += '</li><li class="list-group-item"><b>Expected Return :</b>';
											htmlData += data.expecteddate;
											htmlData += '</li></ul>';
											htmlData += '<a class="btn btn-default" href="confirmbookreturn.htm">Return Book</a>'
											infoForm.html(htmlData);
										}

									},
									error : function(data) {
										alert("Please enter valid data!!");
									}

								})
					});
</script>
<body>
	<c:set var="contextPath" value="${pageContext.request.contextPath}" />
	<div class="jumbotron" id="frame">
		<div id="textcenter">
			<h1>LibraryApp</h1>
			<p>A good book has no ending!!</p>
		</div>
	</div>

	<nav class="navbar navbar-inverse">
		<div class="container-fluid">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target="#myNavbar">
					<span class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#">Logo</a>
			</div>
			<div class="collapse navbar-collapse" id="myNavbar">
				<ul class="nav navbar-nav">
					<li class="active"><a href="${contextPath}/home.htm">Home</a></li>
					<li><a href="${contextPath}/admin/addBooks.htm">Add Books</a></li>
					<li><a href="${contextPath}/admin/addMembers.htm">Add
							Member</a></li>
					<li><a href="${contextPath}/admin/viewRequests.htm">View
							Book Requests</a></li>
					<li><a href="${contextPath}/admin/returnbook.htm">Return a
							Book</a></li>
				</ul>
				<ul class="nav navbar-nav navbar-right">
					<li><a class="dropdown-toggle" data-toggle="dropdown" href="#"><span
							class="glyphicon glyphicon-user"></span> ${user.firstName}</a>
						<ul class="dropdown-menu">
							<li><a href="${contextPath}">Logout</a></li>
						</ul></li>
				</ul>
			</div>
		</div>
	</nav>



	<div class="container-fluid">
		<div class ="col-sm-6">
			<c:if test="${not empty bookcirculationlist}">
			<h2>Books Issued</h2>
				<table class="table table-bordered">
					<tr>
						<td><b>Circulation ID</b></td>
						<td><b>Book ID</b></td>
						<td><b>Book Title</b>
						<td><b>Issue Date</b></td>
					</tr>
					<c:forEach var="bcl" items="${bookcirculationlist}">
						<tr>
							<td>${bcl.circulationId}</td>
							<td>${bcl.book.bookId}
							<td>${bcl.book.title}</td>
							<td>${bcl.issueDate}</td>
						</tr>
					</c:forEach>
				</table>
			</c:if>
			<c:if test="${empty bookcirculationlist}">
				<h3>No books in circulation</h3>
			</c:if>
		</div>
		<div class="col-sm-6 form-group">
			<h2>Search by Book Id:</h2>
			<label>Book Id:</label> <input type="text" class="form-control"
				id="bookid" name="bookid">
			<a href="#" class="btn btn-default" id="booksearch">Search</a>
		</div>
		
	</div>

	<div class="container" id="bookcirculationresult">
		<c:if test="${not empty successmessage}">
			<font color="blue"><h4>${successmessage}</h4></font>
		</c:if>
	</div>

</body>
</html>