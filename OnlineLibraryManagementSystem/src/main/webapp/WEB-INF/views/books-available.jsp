<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
<head>
<title>Available books</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script>
	$(function() {
		$("#txtFrom").datepicker({
			minDate : '0',
			onSelect : function(selected) {
				var dt = new Date(selected);
				dt.setDate(dt.getDate() + 1);
				$("#txtTo").datepicker("option", "minDate", dt);
			}
		});
		$("#txtTo").datepicker({
			minDate : '0',
			onSelect : function(selected) {
				var dt = new Date(selected);
				dt.setDate(dt.getDate() - 1);
				$("#txtFrom").datepicker("option", "maxDate", dt);
			}
		});
	});
</script>
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
	background-image: url(/images/background.jpg);
	background-repeat: no-repeat;
	background-position: center;
	background-color: red;
}

#textcenter {
	padding-left: 5%;
	color: white;
}
</style>
</head>
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
					<li><a href="${contextPath}/member/addBookRequest.htm">Request
							Book</a></li>
					<li><a href="${contextPath}/member/viewraisedrequest.htm">View
							All Requests</a></li>
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
		<div class="col-sm-7">
			<h3>Available Book</h3>
			<br>
			<div>
				<c:forEach var="book" items="${availableBooks}">
					<div class="col-md-3">
						<h3>
							<span>${book.title}</span>
						</h3>
						<small>by ${book.authors}</small>

						<figure>
							<img width="200px" height="250px" src="${book.filename}"
								class="img-rounded" alt="Book photo" />
						</figure>
					</div>
				</c:forEach>
			</div>
		</div>
		<div class="col-sm-5">
			<h3>Create a Book Issue Request!</h3>
			<form:form action="${contextPath}/member/addBookRequest.htm"
				method="post" commandName="reserveBook">
				<div>
					<div style="display: inline-block">
						<label for="start"><i class='glyphicon glyphicon-calendar'></i>
							From Date:</label>
						<form:input path="requestDate" type="text" class="form-control"
							id="txtFrom" />
						<font style=""><form:errors path="requestDate"></form:errors></font>
					</div>
					<div style="display: inline-block">
						<label for="end"><i class='glyphicon glyphicon-calendar'></i>
							End Date:</label>
						<form:input path="tillDate" type="text" class="form-control "
							id="txtTo" />
						<font style=""><form:errors path="tillDate"></form:errors></font>
					</div>
					<div>
						<br> <label><i class="glyphicon glyphicon-book"></i>Book</label>
						<select class="form-control" name="bookid">
							<c:forEach var="ab" items="${availableBooks}">
								<option value="${ab.bookId}">${ab.title}</option>
							</c:forEach>
						</select>
					</div>


					<div class="form-group">
						<br>
						<div class="col-sm-10">
							<input class="btn btn-secondary" type="submit" value="Submit" />
							<input class="btn btn-default" type="reset" value="Reset" />
						</div>
					</div>

				</div>
			</form:form>
		</div>

	</div>


</body>
</html>