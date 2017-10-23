<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>Home</title>
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
					<li class="active"><a href="">Home</a></li>
				</ul>
				<ul class="nav navbar-nav navbar-right">
					<li><button class="btn btn-default"
							data-toggle="modal" data-target="#myModalHorizontal">Login</button></li>
				</ul>

				<div class="modal fade" id="myModalHorizontal" tabindex="-1"
					role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
					<div class="modal-dialog">
						<div class="modal-content">
							<!-- Modal Header -->
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal">
									<span aria-hidden="true">&times;</span> <span class="sr-only">Close</span>
								</button>
								<h4 class="modal-title" id="myModalLabel">Welcome to LibraryApp</h4>
							</div>

							<!-- Modal Body -->
							<div class="modal-body">

								<form class="form-horizontal" role="form" method="post"
									action="${contextPath}/login">
									<div class="form-group">
										<label class="col-sm-2 control-label">Username</label>
										<div class="col-sm-10">
											<input type="text" class="form-control" name="username"
												placeholder="Username" required />
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label">Password</label>
										<div class="col-sm-10">
											<input type="password" class="form-control" name="password"
												placeholder="Password" required />
										</div>
									</div>

									<div class="form-group">
										<div class="col-sm-offset-2 col-sm-10">
											<input type="submit" class="btn btn-secondary"
												value="Sign In" />
										</div>
									</div>
								</form>
							</div>

							<!-- Modal Footer -->
							<div class="modal-footer">
								<button type="button" class="btn btn-default"
									data-dismiss="modal">Close</button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</nav>
	<c:if test="${not empty errorMessage}">
		<font color="red"><h4>${errorMessage}</h4></font>
	</c:if>

	<div class="container">
		<h2>Our collection</h2>
		<c:forEach var="book" items="${books}">
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
</body>
</html>
