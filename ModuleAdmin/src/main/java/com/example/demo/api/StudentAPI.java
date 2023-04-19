package com.example.demo.api;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Employee;
import com.example.demo.model.Student;
import com.example.demo.service.EmployeeService;
import com.example.demo.service.StudentService;

@RestController
public class StudentAPI {
	@Autowired
	private StudentService studentService;
	
	@PostMapping("/student/create")
	public String createStudent(@RequestBody Student student) throws InterruptedException, ExecutionException {
		return studentService.createStudent(student);
	}
	
	@GetMapping("/student/get")
	public Student getStudent(@RequestParam String id) throws InterruptedException, ExecutionException {
		return studentService.getStudentById(id);
	}
	
	@GetMapping("/student/get-students")
	public List<Student> getAllStudents() throws InterruptedException, ExecutionException {
		return studentService.getStudents();
	}
	
	@PostMapping("/student/update")
	public String updateStudent(@RequestBody Student student) throws InterruptedException, ExecutionException {
		return studentService.createStudent(student);
	}
	
	@GetMapping("/student/delete")
	public String deleteStudent(@RequestParam String id) throws InterruptedException, ExecutionException {
		return studentService.deleteStudent(id);
	}
	
}
