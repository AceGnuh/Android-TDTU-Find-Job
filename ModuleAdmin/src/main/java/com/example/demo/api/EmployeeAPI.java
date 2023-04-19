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
import com.example.demo.service.EmployeeService;

@RestController
public class EmployeeAPI {
	@Autowired
	private EmployeeService employeeService;
	
	@PostMapping("/employee/create-employee")
	public String createStudent(@RequestBody Employee employee) throws InterruptedException, ExecutionException {
		
		return employeeService.createEmployee(employee);
	}
	
	@GetMapping("/employee/get-employee")
	public Employee getStudent(@RequestParam String id) throws InterruptedException, ExecutionException {
		return employeeService.getEmployeeById(id);
	}
	
	@GetMapping("/employee/get-employees")
	public List<Employee> getAllStudents() throws InterruptedException, ExecutionException {
		return employeeService.getEmployees();
	}
	
	@PostMapping("/employee/update-employee")
	public String updateStudent(@RequestBody Employee employee) throws InterruptedException, ExecutionException {
		return employeeService.createEmployee(employee);
	}
	
	@GetMapping("/employee/delete-employee")
	public String deleteStudent(@RequestParam String id) throws InterruptedException, ExecutionException {
		return employeeService.deleteEmployee(id);
	}
	
}
