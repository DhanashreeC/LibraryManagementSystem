<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Pending requests</title>
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
	$(document).ready(function() {
		var infoModal = $('#myModal');
		$(".viewinfo").click(function() {
			//event.preventDefault();
			var memId = {
				"memberID" : this.id
			};
			$.ajax({
				url : "viewmemberinfo.htm",
				data : memId,
				dataType : 'json',
				type : "GET",
				success : function(data) {
					var htmlData = '<ul class="list-group"><li class="list-group-item"><b>Member Id:</b>';
					htmlData += data.memberId;
					htmlData += '</li><li class="list-group-item"><b>Member name :</b>';
					htmlData += data.memberfirstname
							+ ' '
							+ data.memberlastname;
					htmlData += '</li><li class="list-group-item"><b>Email Id :</b>';
					htmlData += data.emailId;
					htmlData += '</li><li class="list-group-item"><b>No. of late occurrence :</b>';
					htmlData += data.latesubmission;
					htmlData += '</li></ul>';
					infoModal.find('#modal-body')[0].innerHTML = htmlData;
					infoModal.modal();
				}
			})
		});

		$(".approve").click(function() {
			var $that = $(this);
			var reqID = {
				"requestID" : this.id
			};
			$.ajax({
				url : "approverequest.htm",
				data : reqID,
				dataType : 'json',
				type : "POST",
				success : function(response) {
					console.log("I am in approve" + response);
					$that.closest('tr').remove();

				}
			})
		});

		$(".reject").click(function() {
			//event.preventDefault();
			var $that = $(this);
			var rid = {
				"requestID" : this.id
			};
			$.ajax({
				url : "rejectrequest.htm",
				data : rid,
				dataType : 'json',
				type : "POST",
				success : function(response) {
					console.log("I am in delete " + response);
					$that.closest('tr').remove();

				}
			})
		});

	})
</script>
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
	<c:if test="${not empty bookRequests}">
		<h2>Pending Requests</h2>
        <table class="table table-bordered">
			<tr>
				<td><b>ID</b></td>
				<td><b>Raised by</b></td>
				<td><b>Book</b></td>
				<td><b>Status</b></td>
				<td><b>Action to perform</b></td>
			</tr>
			<c:forEach var="br" items="${bookRequests}">
				<tr>
					<td>${br.reservationID}</td>
					<td>${br.librarymember.libraryUser.firstName}&thinsp;
						${br.librarymember.libraryUser.lastName}</td>
					<td>${br.book.title}</td>
					<td>${br.requestStatus}</td>
					<td><button class="btn btn-default viewinfo"
							id="${br.librarymember.memberID}">View Member Info</button>
						&nbsp;
						<button class="btn btn-default approve" id="${br.reservationID}">Approve</button>
						&nbsp;
						<button class="btn btn-default reject" id="${br.reservationID}">Reject</button>
					</td>
				</tr>
			</c:forEach>
		</table>

    </c:if>
	<c:if test="${empty bookRequests}">
		<h3>No Pending requests</h3>
	</c:if>

	</div>
	
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
			aria-labelledby="exampleModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" id="exampleModalLabel">User Info</h4>
					</div>
					<div class="modal-body" id="modal-body"></div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					</div>
				</div>
			</div>
		</div>




</body>
</html>