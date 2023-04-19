package com.example.demo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

@SpringBootApplication
public class FirestoreApplication {

	public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
		ClassLoader classLoader = Firestore.class.getClassLoader();
		
		File file = new File(Objects.requireNonNull(classLoader.getResource("serviceAccountkey.json")).getFile());
		
		InputStream serviceAccount = new FileInputStream(file.getAbsoluteFile());
		
		FirebaseOptions options = new FirebaseOptions.Builder()
				  .setCredentials(GoogleCredentials.fromStream(serviceAccount))
				  .build();

		FirebaseApp.initializeApp(options);
		
		Firestore db = FirestoreClient.getFirestore();
				
		// asynchronously retrieve all users
//		ApiFuture<QuerySnapshot> query = db.collection("users").get();
//		// ...
//		// query.get() blocks on response
//		QuerySnapshot querySnapshot = query.get();
//		List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
//		for (QueryDocumentSnapshot document : documents) {
//		  System.out.println("User: " + document.getId());
//		  System.out.println("Name: " + document.getString("name"));
//		  if (document.contains("middle")) {
//		    System.out.println("Middle: " + document.getString("middle"));
//		  }
//		  System.out.println("Phone: " + document.getString("phone"));
//		  System.out.println("Gender: " + document.getString("gender"));
//		}
		
		SpringApplication.run(FirestoreApplication.class, args);
	}

}
