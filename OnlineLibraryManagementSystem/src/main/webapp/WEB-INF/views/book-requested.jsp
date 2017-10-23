<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>UserPage- Requests raised</title>
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
					<li><a href="${contextPath}/member/viewBookRequest.htm">View
							All Requests</a></li>
				</ul>
				<ul class="nav navbar-nav navbar-right">
					<li><a class="dropdown-toggle" data-toggle="dropdown" href="#"><span
							class="glyphicon glyphicon-user"></span>  ${user.firstName}</a>
						<ul class="dropdown-menu">
							<li><a href="${contextPath}">Logout</a></li>
						</ul></li>
				</ul>
			</div>
		</div>
	</nav>
	<p>${requestedBook.title}</p>
	<br>
	<p>${requestNumber.reservationID}</p>


	<div class="container">
	<h2>Book Issue Requests raised status</h2>
		<table class="table table-bordered">
			<tr>
				<td><b>ID</b></td>
				<td><b>Book</b></td>
				<td><b>Last update date:</b>
				<td><b>Status</b></td>
			</tr>
			<c:forEach var="br" items="${bookreservationlist}">
				<tr>
					<td>${br.reservationID}</td>
					<td>${br.book.title}</td>
					<td>${br.updateDate}</td>
					<td>${br.requestStatus}</td>
				</tr>
			</c:forEach>
		</table>


	</div>

</body>
</html>