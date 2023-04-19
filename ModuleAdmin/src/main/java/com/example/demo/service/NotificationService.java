package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Service;

import com.example.demo.model.Employee;
import com.example.demo.model.Notification;
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
public class NotificationService {
	public String createNotification(Notification notification) throws InterruptedException, ExecutionException {
		Firestore db = FirestoreClient.getFirestore();
		ApiFuture<DocumentReference> result = db.collection("notification").add(notification);
		String id = result.get().getId();
		notification.setId(id);
		ApiFuture<WriteResult> data = db.collection("notification").document(id).set(notification);
		
		return data.get().getUpdateTime().toString();
	}
	
	public Notification getNotificationById(String id) throws InterruptedException, ExecutionException {
		Firestore db = FirestoreClient.getFirestore();
		DocumentReference documentReference = db.collection("notification").document(id);
		ApiFuture<DocumentSnapshot> query = documentReference.get();
		
		DocumentSnapshot documentSnapshot = query.get();
		
		Notification notification;
		if(documentSnapshot.exists()) {
			notification = documentSnapshot.toObject(Notification.class);
			return notification;
		}		
		
		return null;
	}
	
	public List<Notification> getNotifications() throws InterruptedException, ExecutionException{
		List<Notification> lstNotifications = new ArrayList<>();
		Firestore db = FirestoreClient.getFirestore();
		
		ApiFuture<QuerySnapshot> future = db.collection("notification").get();

		List<QueryDocumentSnapshot> documents = future.get().getDocuments();
		for (QueryDocumentSnapshot document : documents) {
			Notification notification = (Notification) document.toObject(Notification.class);
			lstNotifications.add(notification);
		}
		
		return lstNotifications;
	}
	
	public String deleteNotification(String id) throws InterruptedException, ExecutionException {
		Firestore db = FirestoreClient.getFirestore();
		ApiFuture<WriteResult> apiFuture = db.collection("notification").document(id).delete();
		return apiFuture.get().getUpdateTime().toString();
	}
	
	public String updateNotification(Notification notification) throws InterruptedException, ExecutionException {
		Firestore db = FirestoreClient.getFirestore();
		ApiFuture<WriteResult> result = db.collection("notification").document(notification.getId()).set(notification);
		
		return "Add success";
	}
}
