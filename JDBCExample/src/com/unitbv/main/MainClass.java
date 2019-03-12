package com.unitbv.main;

import java.sql.Date;
import java.util.List;

import com.unitbv.database.Database;
import com.unitbv.database.DatabaseConnection;
import com.unitbv.model.Student;
import com.unitbv.util.StudentOperations;

public class MainClass {
	public static void main(String[] args) {
		Database database = new Database("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/jdbcexample", "root", "toor");
		DatabaseConnection databaseConnection = new DatabaseConnection(database);
		StudentOperations studentOperations = new StudentOperations(databaseConnection);
		
		System.out.println("Before adding...");
		List<Student> students = studentOperations.getAllStudents();
		studentOperations.printListOfStudents(students);
		
		Student studentToAdd = new Student(47, "Forty", "Two", Date.valueOf("2019-03-11"), "Everywhere");
		
		studentOperations.addStudent(studentToAdd);
		
		System.out.println("After adding...");
		students = studentOperations.getAllStudents();
		studentOperations.printListOfStudents(students);
	}
}
