<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>

<%@ page import="java.util.*, com.javaservletconfig.*" %>

<%@ page isELIgnored="false" %>

<!Doctype html>
<html>
<head>
	<title>Student tracker app</title>
	<link type="text/css" rel="stylesheet" href="css/style.css">
</head>

<!-- 		HashSet<Student> theStudent = (HashSet<Student>) request.getAttribute("Student_list");      -->	
<body>

	<div id="wrapper">
		<div id="header">
			<h2>CodePy university</h2>
		</div>
	</div>

	<div id="container">
	<div id="context">
	
	<input type="button" value="Add Student" 
				onclick="window.location.href='add-student-form.jsp'; return false;"
				class="add-student-button"
	/>
	
		<table>
			<tr>
				<th>First Name</th>
				<th>Last Name</th>
				<th>Email</th>
				<th>Action</th>
			</tr>
			
			<!--  for(Student tempStudent : theStudent) { -->
			<c:forEach var="tempStudent" items="${STUDENT_LIST}">
			
			<c:url var="tempLink" value="StudentControllerServlet">
				<c:param name="command" value="LOAD"></c:param>
				<c:param name="studentId" value="${tempStudent.id}"></c:param>
			</c:url>
			
			<c:url var="deleteLink" value="StudentControllerServlet">
				<c:param name="command" value="DELETE"></c:param>
				<c:param name="studentId" value="${tempStudent.id}"></c:param>
			</c:url>
				
				<tr>
					<td> ${tempStudent.firstName} </td>
					<td> ${tempStudent.lastName} </td>
					<td> ${tempStudent.email} </td>
					<td> 
					<a href="${tempLink}">Update</a> 
						|
					<a href="${deleteLink}"
					onclick = "if(!(confirm('Are you sure you have  guts to do this * *'))) return false">Delete</a>
					</td>
				</tr>
			
			</c:forEach>
			
		</table>
	</div>
	</div>
</body>
</html>