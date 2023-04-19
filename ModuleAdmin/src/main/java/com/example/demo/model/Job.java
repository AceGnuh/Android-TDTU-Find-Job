package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class Job {
	private String id;
	private String name;
	private String description;
	private String isApprove;
	private String employee;
	private List<String> student;
	private String salary;
	private String date;
	private String address;
	private String img;
	private String major;
	
	public Job() {
		this.isApprove = "false";
		this.student = new ArrayList<>();
	}

	public Job(String id, String name, String description) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.student = new ArrayList<>();
	}

	public Job(String id, String name, String description, String employee) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.employee = employee;
		this.student = new ArrayList<>();
	}

	public Job(String id, String name, String description, String isApprove, String employee, List<String> student) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.isApprove = isApprove;
		this.employee = employee;
		this.student = student;
	}

	public Job(String id, String name, String description, String isApprove, String employee, List<String> student,
			String salary, String date, String address, String img, String major) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.isApprove = isApprove;
		this.employee = employee;
		this.student = student;
		this.salary = salary;
		this.date = date;
		this.address = address;
		this.img = img;
		this.major = major;
	}

	
	

}
