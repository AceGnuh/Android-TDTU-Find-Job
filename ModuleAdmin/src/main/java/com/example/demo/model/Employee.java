package com.example.demo.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
	
	private String id;
	private String name;
	private String address;
	private String username;
	private String password;
	private String logo;
	private String description;
	private String email;
	private String phone;
	

}
