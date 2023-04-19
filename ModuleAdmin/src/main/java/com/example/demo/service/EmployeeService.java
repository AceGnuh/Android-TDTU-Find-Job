package com.example.demo.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Address;
import com.example.demo.model.Employee;
import com.example.demo.model.Job;
import com.example.demo.model.Student;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;

@Service
public class EmployeeService {
	
	@Autowired
	private StudentService studentService;
	
	@Autowired 
	private JobService jobService;
	
	public String createEmployee(Employee employee) throws InterruptedException, ExecutionException {
		Firestore db = FirestoreClient.getFirestore();
		ApiFuture<DocumentReference> result = db.collection("employee").add(employee);
		String id = result.get().getId();
		employee.setId(id);
		db.collection("employee").document(id).set(employee);
		
		return "Add success";
	}
	
	public Employee getEmployeeById(String id) throws InterruptedException, ExecutionException {
		Firestore db = FirestoreClient.getFirestore();
		DocumentReference documentReference = db.collection("employee").document(id);
		ApiFuture<DocumentSnapshot> query = documentReference.get();
		
		DocumentSnapshot documentSnapshot = query.get();
		
		Employee employee;
		if(documentSnapshot.exists()) {
			employee = documentSnapshot.toObject(Employee.class);
			return employee;
		}		
		
		return null;
	}
	
	public List<Employee> getEmployees() throws InterruptedException, ExecutionException{
		List<Employee> lstEmployees = new ArrayList<>();
		Firestore db = FirestoreClient.getFirestore();

		
		ApiFuture<QuerySnapshot> query = db.collection("employee").get();

		QuerySnapshot querySnapshot = query.get();
		
		List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
		
		for (QueryDocumentSnapshot document : documents) {			
			String id = document.getId();
			String name = document.getString("name");
			String address = document.getString("address");
			String logo = document.getString("logo");
			String username = document.getString("username");
			String password = document.getString("password");
			String description = document.getString("description");
			String email = document.getString("email");
			String phone = document.getString("phone");

			lstEmployees.add(new Employee(id, name, address, username, password, logo, description, email, phone));
		  
		}
		return lstEmployees;
		
	}
	
	public String deleteEmployee(String id) {
		Firestore db = FirestoreClient.getFirestore();
		ApiFuture<WriteResult> apiFuture = db.collection("employee").document(id).delete();
		return "Delete successful";
	}
	
	public String updateEmployee(Employee employee) throws InterruptedException, ExecutionException {
		Firestore db = FirestoreClient.getFirestore();
		ApiFuture<WriteResult> result = db.collection("employee").document(employee.getId()).set(employee);
		
		return "Add success";
	}
	
	public List<Student> getStudentOfEmployee(String employeeId) throws InterruptedException, ExecutionException{
		//list chua student cua employee
		List<Student> listStudentOfEmployees = new ArrayList<>();
		//get data employee
		Employee employee = this.getEmployeeById(employeeId);
		//get list student
		List<Student> listStudents = studentService.getStudents();
		//check 
		for(Student student : listStudents) {
			for(String idEmployeeStudent : student.getEmployee()) {
				if(idEmployeeStudent.equals(employeeId)) {
					listStudentOfEmployees.add(student);
				}
			}
		}
		
		return listStudentOfEmployees;
	}
	
	public List<Employee> getEmployeesByStudent(String studentId) throws InterruptedException, ExecutionException{
		//init data
		List<Employee> listEmployeeByStudent = new ArrayList<>();
		
		//get lists data
		List<Employee> listEmployees = this.getEmployees();
		Student student = studentService.getStudentById(studentId);
		
		//process
		for(Employee employee : listEmployees) {
			for(String employee_student : student.getEmployee()) {
				if(employee.getId().equals(employee_student)) {
					listEmployeeByStudent.add(employee);
				}
			}
		}
		
		return listEmployeeByStudent;
	}
	
	public List<Student> getStudentByJob(String id) throws InterruptedException, ExecutionException{
//		
//		Job job = jobService.getJobById(id);
//		Firestore db = FirestoreClient.getFirestore();
//		
//		List<String> emailStudents = new ArrayList<>();
//		List<Student> students = studentService.getStudents();
//		
//		for(Student student : students) {
//			emailStudents.add(student.getEmail());
//			//emailStudents.add(student.getId())
//		}
//		
//		System.out.println("list email" + emailStudents);
//		
//		
//		CollectionReference studentsRef = db.collection("job");
//		Query westCoastQuery = studentsRef.whereEqualTo("id", id);
//		
//		List<QueryDocumentSnapshot> documents = westCoastQuery.get().get().getDocuments();
//		
//		List<Student> listStudent = new ArrayList<>();
//		
//		for (DocumentSnapshot document : documents) {
//		  System.out.println("Data student and job's "+document.getId() + " => " + document.toObject(Student.class));
//		  listStudent.add(document.toObject(Student.class));
//		  
//		}
		
		return null;
	}
	
	public List<Job> getJobByEmployee(String name) throws InterruptedException, ExecutionException{
		Firestore db = FirestoreClient.getFirestore();
		List<Job> listJob = new ArrayList<>();
		
		CollectionReference jobsRef = db.collection("job");

		Query query =
		    jobsRef.whereEqualTo("employee", name);
		
		List<QueryDocumentSnapshot> documents = query.get().get().getDocuments();
		
		for(DocumentSnapshot document : documents) {
			System.out.println("JOB: " + document.toObject(Job.class));
			listJob.add(document.toObject(Job.class));
		}
		return listJob;
	}
}
