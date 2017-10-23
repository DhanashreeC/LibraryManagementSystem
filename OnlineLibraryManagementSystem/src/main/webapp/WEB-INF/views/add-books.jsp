<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
<head>
<title>AdminPage - Add Books</title>
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

	<div class="container">
		<form:form action="addBooks.htm" method="post" commandName="book"
			enctype="multipart/form-data" class="form-horizontal">
			<div class="form-group">
				<label class="control-label col-sm-2">ISBN:</label>
				<div class="col-sm-10">
					<form:input type="text" class="form-control" path="isbn"
						placeholder="Enter ISBN Number in format XXX-XXX-XXX" />
					<font color="red"><form:errors path="isbn"></form:errors></font>
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-sm-2">Book Title:</label>
				<div class="col-sm-10">
					<form:input type="text" class="form-control" path="title"
						placeholder="Enter book title" />
					<font color="red"><form:errors path="title"></form:errors></font>
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-sm-2">Authors:</label>
				<div class="col-sm-10">
					<form:input type="text" class="form-control" path="authors"
						placeholder="Enter Book Author" />
					<font color="red"><form:errors path="authors"></form:errors></font>
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-sm-2">Total Books:</label>
				<div class="col-sm-10">
					<select name="totalbooks" class="form-control">
						<c:forEach begin="1" end="20" var="data">
							<option value="${data}">${data}</option>
						</c:forEach>
					</select>
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-sm-2">Upload Book coverpic:</label>
				<div class="col-sm-10">
					<input type="file" name="photo" class="form-control" required />
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<input type="submit" class="btn btn-secondary btn-lg" value="Add Book" />
				</div>
			</div>
		</form:form>
	</div>

</body>
</html>