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
		<div class="col-sm-8">
			<h2>Our collection</h2>
			<c:forEach var="book" items="${books}">
				<div class="col-md-3">
					<h3>
						<span>${book.title}</span>
					</h3>
					<small>by ${book.authors}</small>
					<figure>
						<img width="200px" height="250px"
							src="${book.filename}"
							class="img-rounded" alt="Book photo" />
					</figure>
				</div>
			</c:forEach>
		</div>
		<div class="col-sm-4">
		<h2>Members List</h2>
			<ul class="list-group">
				<c:forEach var="member" items="${members}">
				<li class="list-group-item">${member.libraryUser.firstName} ${member.libraryUser.lastName}</li>
				</c:forEach>
			</ul>
		</div>
	</div>
</body>
</html>