package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Job;
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
public class JobService {
	public String createEmployee(Job job) throws InterruptedException, ExecutionException {
		Firestore db = FirestoreClient.getFirestore();
		ApiFuture<DocumentReference> result = db.collection("job").add(job);
		String id = result.get().getId();
		job.setId(id);
		ApiFuture<WriteResult> future = db.collection("job").document(id).set(job);

		return future.get().getUpdateTime().toString();
	}
	
	public Job getJobById(String id) throws InterruptedException, ExecutionException {
		Firestore db = FirestoreClient.getFirestore();
		DocumentReference documentReference = db.collection("job").document(id);
		ApiFuture<DocumentSnapshot> query = documentReference.get();
		
		DocumentSnapshot documentSnapshot = query.get();
		
		Job job;
		if(documentSnapshot.exists()) {
			job = documentSnapshot.toObject(Job.class);
			return job;
		}		
		
		return null;
	}
	
	public List<Job> getJobs() throws InterruptedException, ExecutionException{
		List<Job> lstJobs = new ArrayList<>();
		Firestore db = FirestoreClient.getFirestore();
				
		ApiFuture<QuerySnapshot> future = db.collection("job").get();
		// future.get() blocks on response
		List<QueryDocumentSnapshot> documents = future.get().getDocuments();
		for (QueryDocumentSnapshot document : documents) {
			Job job = (Job) document.toObject(Job.class);
			System.out.println(document.getId() + " => " + document.toObject(Job.class));
			lstJobs.add(job);
		}

		return lstJobs;
		
	}
	
	public String deleteJob(String id) throws InterruptedException, ExecutionException {
		Firestore db = FirestoreClient.getFirestore();
		
		ApiFuture<WriteResult> apiFuture = db.collection("job").document(id).delete();
		
		return apiFuture.get().getUpdateTime().toString();
	}
	
	public String updateJob(Job job) throws InterruptedException, ExecutionException {
		Firestore db = FirestoreClient.getFirestore();
		ApiFuture<WriteResult> result = db.collection("job").document(job.getId()).set(job);

		return result.get().getUpdateTime().toString();
	}
}
