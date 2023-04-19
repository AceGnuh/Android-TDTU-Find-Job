package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class Student {
	private String id;
	private String name;
	private String username;
	private String password;
	private double gpa;
	private List<String> employee;
	private List<String> skills;
	private String birthDate;
	private String sex;
	private String major;
	private String email;
	private String phone;
	
	public Student() {
		this.employee = new ArrayList<>();
		this.skills = new ArrayList<>();
	}
	
	public Student(String id, String name, String username, String password) {
		super();
		this.id = id;
		this.name = name;
		this.username = username;
		this.password = password;
	}

	public Student(String id, String name, String username, String password, double gpa, List<String> employee,
			List<String> skills, String birthDate, String sex, String major, String email, String phone) {
		super();
		this.id = id;
		this.name = name;
		this.username = username;
		this.password = password;
		this.gpa = gpa;
		this.employee = employee;
		this.skills = skills;
		this.birthDate = birthDate;
		this.sex = sex;
		this.major = major;
		this.email = email;
		this.phone = phone;
	}

}
