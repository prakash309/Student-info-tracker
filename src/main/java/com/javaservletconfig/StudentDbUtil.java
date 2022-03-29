package com.javaservletconfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashSet;

import javax.sql.DataSource;

public class StudentDbUtil {

	private DataSource dataSource;
	
	public StudentDbUtil(DataSource dataSource)
	{
		this.dataSource = dataSource;
	}
	
	public HashSet<Student> getStudents() throws Exception
	{
		HashSet<Student> students = new HashSet<Student>();
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		
		try {
			
			con = dataSource.getConnection();
			String sql = "select * from student order by first_name";
			st = con.createStatement();
			rs = st.executeQuery(sql);
			while(rs.next()) {
				int id = rs.getInt("id");
				String firstName = rs.getString("first_name");
				String lastName = rs.getString("last_name");
				String email = rs.getString("email");
				

				Student tempstudent = new Student(id, firstName, lastName, email);
				students.add(tempstudent);
			}
		return students;
		}
		finally {
			close(con, st, rs);
		}
	}

	private void close(Connection con, Statement st, ResultSet rs) {
		
		try {
			if(rs != null)
				rs.close();
			
			if(st != null)
				st.close();
			
			if(con != null)
				con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addStudent(Student theStudent){
		Connection mycon = null;
		PreparedStatement pst = null;
		
			try {
				mycon = dataSource.getConnection();
			
			
			String sql = "insert into student"
								+ "( first_name, last_name, email) "
								+ "values (?, ?, ?)";
			pst = mycon.prepareStatement(sql);
			
			pst.setString(1, theStudent.getFirstName());
			pst.setString(2, theStudent.getLastName());
			pst.setString(3, theStudent.getEmail());
			
			pst.execute();
			}catch (Exception e) {
				e.printStackTrace();
			}
			finally {
				close(mycon, pst, null);
			}
			
		
	}

	public Student getStudents(String theStudentId) throws Exception{
		Student theStudent = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int studentId;
		
		try {
			
			studentId = Integer.parseInt(theStudentId);
			conn = dataSource.getConnection();
			String sql = "select * from student where id = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, studentId);
			rs = ps.executeQuery();
			if(rs.next()) {
				String firstName = rs.getString("first_name");
				String lastName = rs.getString("last_name");
				String email = rs.getString("email");
				theStudent = new Student(studentId, firstName, lastName, email);
			}
			else
			{
				throw new Exception("could not find the student with id : "+ studentId);
			}
			return theStudent;
		}
		finally {
			close(conn, ps, rs);
		}
		
	}

	public void updateStudent(Student theStudent) throws Exception{
		
		Connection mycon = null;
		
		PreparedStatement pst = null;
		
		try
		{
		mycon = dataSource.getConnection();
		
		String sql = "update student "
							+ "set first_name=?, last_name=?, email=? "
							+ "where id = ?";
		
		pst = mycon.prepareStatement(sql);
		
		pst.setString(1, theStudent.getFirstName());
		pst.setString(2, theStudent.getLastName());
		pst.setString(3, theStudent.getEmail());
		pst.setInt(4, theStudent.getId());
		
		pst.execute();
		}
		finally {
			close(mycon, pst, null);
		}
	}

	public void deleteStudent(String theStudentId) throws Exception{
		Connection con = null;
		PreparedStatement ps = null;
		
		try {
			
			int studentId = Integer.parseInt(theStudentId);
			
			con = dataSource.getConnection();
			
			String sql = "delete from student where id = ?";
			
			ps = con.prepareStatement(sql);
			
			ps.setInt(1, studentId);
			
			ps.execute();
		}
		finally {
			close(con, ps, null);
		}
	}
}
