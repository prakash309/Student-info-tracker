package com.javaservletconfig;

import java.io.IOException;
import java.util.HashSet;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;


public class StudentControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private StudentDbUtil studentDb;
	
	@Resource(name="jdbc/student_info_tracker")
	private DataSource dataSource;
	
	
	@Override
	public void init() throws ServletException {
		super.init();
		try {
			studentDb = new StudentDbUtil(dataSource);
		} catch (Exception ex) {
			throw new ServletException(ex);
		}
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
			try {
				String theCommand = request.getParameter("command");
				if(theCommand == null)
				{
					theCommand = "LIST";
				}
				
				switch (theCommand) {
				case "LIST":
					listStudent(request, response);
					break;
				
				case "ADD":
					addStudent(request, response);
					break;
					
				case "LOAD":
					loadStudent(request, response);
					break;
					
				case "UPDATE":
					updateStudent(request, response);
					break;
					
				case "DELETE":
					deleteStudent(request, response);
					break;
					
				default:
					listStudent(request, response);
				}
				listStudent(request, response);
			} catch (Exception e) {
				throw new ServletException(e);
			}
	}


	private void deleteStudent(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		String theStudentId = request.getParameter("studentId");
		
		studentDb.deleteStudent(theStudentId);
		
		listStudent(request, response);
	}


	private void updateStudent(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		int id = Integer.parseInt(request.getParameter("studentId"));
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		
		Student theStudent = new Student(id, firstName, lastName, email);
		
		studentDb.updateStudent(theStudent);
		
		listStudent(request, response);
		
	}


	private void loadStudent(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		String theStudentId = request.getParameter("studentId");
		
		Student theStudent = studentDb.getStudents(theStudentId);
		
		request.setAttribute("THE_STUDENT", theStudent);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/update-student-form.jsp");
		dispatcher.forward(request, response);
	}


	private void addStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		
		Student theStudent = new Student(firstName, lastName, email);
		
		studentDb.addStudent(theStudent);
		
		listStudent(request, response);
	}


	private void listStudent(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		HashSet<Student> students = studentDb.getStudents();
		
		request.setAttribute("STUDENT_LIST", students);
		
		RequestDispatcher rDispatcher = request.getRequestDispatcher("/listOfStudents.jsp");
		rDispatcher.forward(request, response);
	}

}
