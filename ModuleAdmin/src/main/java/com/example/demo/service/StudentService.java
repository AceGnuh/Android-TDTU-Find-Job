package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Employee;
import com.example.demo.model.Student;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;

@Service
public class StudentService {
	
	//method service
	public String createStudent(Student student) throws InterruptedException, ExecutionException {
		Firestore db = FirestoreClient.getFirestore();
		ApiFuture<WriteResult> result = db.collection("student").document(student.getEmail()).set(student);
//		String id = result.get().getId();
//		student.setId(id);
//		db.collection("student").document(id).set(student);
		return result.get().getUpdateTime().toString();
	}
	
	public Student getStudentById(String id) throws InterruptedException, ExecutionException {
		Firestore db = FirestoreClient.getFirestore();
		DocumentReference documentReference = db.collection("student").document(id);
		ApiFuture<DocumentSnapshot> query = documentReference.get();
		
		DocumentSnapshot documentSnapshot = query.get();
		
		Student student;
		if(documentSnapshot.exists()) {
			student = documentSnapshot.toObject(Student.class);
			return student;
		}		
		
		return null;
	}
	
	public Student getStudentByEmail(String email) throws InterruptedException, ExecutionException {
		Firestore db = FirestoreClient.getFirestore();
		DocumentReference documentReference = db.collection("student").document(email);
		ApiFuture<DocumentSnapshot> query = documentReference.get();
		
		DocumentSnapshot documentSnapshot = query.get();
		
		Student student;
		if(documentSnapshot.exists()) {
			student = documentSnapshot.toObject(Student.class);
			return student;
		}		
		
		return null;
	}
	
	public List<Student> getStudents() throws InterruptedException, ExecutionException{
		List<Student> lstStudents = new ArrayList<>();
		Firestore db = FirestoreClient.getFirestore();
		
		ApiFuture<QuerySnapshot> future = db.collection("student").get();
		// future.get() blocks on response
		List<QueryDocumentSnapshot> documents = future.get().getDocuments();
		for (QueryDocumentSnapshot document : documents) {
			Student student = (Student) document.toObject(Student.class);
			System.out.println(document.getId() + " => " + document.toObject(Student.class));
			lstStudents.add(student);
		}
		
		
		/*
		
		ApiFuture<QuerySnapshot> query = db.collection("student").get();

		QuerySnapshot querySnapshot = query.get();
		
		List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
		
		for (QueryDocumentSnapshot document : documents) {			
			String id = document.getId();
			String name = document.getString("name");
			String username = document.getString("username");
			String password = document.getString("password");
			double gpa = document.getDouble("gpa");
			List<String> skills = (List<String>) document.get("skills");
			List<String> employee = (List<String>) document.get("employee");
			String password = document.getString("password");
			String password = document.getString("password");
			String password = document.getString("password");
			String password = document.getString("password");
			lstStudents.add(new Student(id, name, username, password, gpa, employee, skills));
		  
		}
		
		*/
		
		
		return lstStudents;
		
	}
	
	public String updateStudent(Student student) throws InterruptedException, ExecutionException {
		Firestore db = FirestoreClient.getFirestore();
		ApiFuture<WriteResult> result = db.collection("student").document(student.getEmail()).set(student);

		return result.get().getUpdateTime().toString();
	}
	
	public String deleteStudent(String email) {
		Firestore db = FirestoreClient.getFirestore();
		ApiFuture<WriteResult> apiFuture = db.collection("student").document(email).delete();
		return "Delete successful";
	}
	
	
}
