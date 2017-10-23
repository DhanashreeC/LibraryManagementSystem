<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>Error Page</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<style>
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
	
	<div class="container">
		<h2>Ooops!!!</h2>
		<div class="well">
		<h3>We are sorry!! There seems to be some error.</h3><br>
		<h3>Please click <a href="${contextPath}/home.htm">here</a> to go back to home page</h3>
		</div>
	</div>
</body>
</html>