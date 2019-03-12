package com.unitbv.util;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.unitbv.database.DatabaseConnection;
import com.unitbv.model.Student;

public class StudentOperations {

	private DatabaseConnection databaseConnection;

	public StudentOperations(DatabaseConnection databaseConnection) {
		this.databaseConnection = databaseConnection;
	}

	public List<Student> getAllStudents() {
		databaseConnection.createConnection();
		String query = "SELECT idStudent, firstName, lastName, dateOfBirth, city FROM students";
		List<Student> students = new ArrayList<Student>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = databaseConnection.getConnection().prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				Student student = new Student();
				student.setId(rs.getInt("idStudent"));
				student.setFirstName(rs.getString("firstName"));
				student.setLastName(rs.getString("lastName"));
				student.setDateOfBirth(rs.getDate("dateOfBirth"));
				student.setCity(rs.getString("city"));

				students.add(student);
			}
		} catch (SQLException e) {
			System.err.println("Error when creating query: " + e.getMessage());
		} finally {
			try {
				rs.close();
				ps.close();
				databaseConnection.getConnection().close();
			} catch (SQLException e) {
				System.err.println("Failed closing streams: " + e.getMessage());
			}
		}

		return students;
	}
	
	public boolean addStudent(Student student) {
		databaseConnection.createConnection();
		String query = "INSERT INTO students VALUES (?, ?, ?, ?, ?)";
		PreparedStatement ps = null;
		try {
			ps = databaseConnection.getConnection().prepareStatement(query);
			ps.setInt(1, student.getId());
			ps.setString(2, student.getFirstName());
			ps.setString(3, student.getLastName());
			ps.setDate(4, (Date) student.getDateOfBirth());
			ps.setString(5, student.getCity());
			ps.executeUpdate();
		} catch (SQLException e) {
			System.err.println("Error when creating query: " + e.getMessage());
			return false;
		} finally {
			try {
				ps.close();
				databaseConnection.getConnection().close();
			} catch (SQLException e) {
				System.err.println("Failed closing streams: " + e.getMessage());
			}
		}
		
		return true;
	}

	public void printListOfStudents(List<Student> students) {
		for (Student stud : students) {
			System.out.println(stud.toString());
		}
	}
}
