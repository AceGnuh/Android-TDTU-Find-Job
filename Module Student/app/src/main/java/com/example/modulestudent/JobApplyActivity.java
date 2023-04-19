package com.example.modulestudent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class JobApplyActivity extends AppCompatActivity {

    private RecyclerView rcv_jobapply;
    private List<HightlightJobs> listJob;
    private FirebaseFirestore db;
    private JobAdapter jobsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_apply);

        db = FirebaseFirestore.getInstance();

        rcv_jobapply = findViewById(R.id.rcv_jobapply);




        listJob = new ArrayList<>();
        jobsAdapter = new JobAdapter(listJob);
//        rcv_jobapply.setAdapter(jobsAdapter);
        //session data
        SharedPreferences sharedPreferences = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        String email = sharedPreferences.getString("EMAIL","");

        CollectionReference jobRef = db.collection("job");

        jobRef.whereArrayContains("student", email)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("JOBAPPLY", document.getId() + " => " + document.getData());
                                Log.d("Job nek", document.getId() + " => " + document.getData());
                                HightlightJobs job = new HightlightJobs();
                                job.setId(document.getString("id"));
                                job.setJobName(document.getString("name"));
                                job.setAddress(document.getString("address"));
                                job.setCompanyName(document.getString("employee"));
                                job.setDate(document.getString("date"));
                                job.setDescription(document.getString("description"));
                                job.setMajor(document.getString("major"));
                                job.setSalary(document.getString("salary"));
                                job.setImg(document.getString("img"));

                                Log.d("JOBNEK", job.toString());

//                                db.collection("employee")
//                                        .whereEqualTo("name", job.getCompanyName())
//                                        .get()
//                                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                                            @Override
//                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                                                if (task.isSuccessful()) {
//                                                    for (QueryDocumentSnapshot document : task.getResult()) {
//                                                        //Log.d(TAG, document.getId() + " => " + document.getData());
//                                                        job.setJobName(document.getString("name"));
//                                                    }
//                                                } else {
//                                                    //Log.d(TAG, "Error getting documents: ", task.getException());
//                                                }
//                                            }
//                                        });


                                listJob.add(job);
                                jobsAdapter.notifyDataSetChanged();
                            }

                        }
                        Log.d("DATAAA", listJob.toString());
                        jobsAdapter = new JobAdapter(listJob, new JobAdapter.IClickItemListener() {
                            @Override
                            public void OnClickItemListener(HightlightJobs hightlightJobs) {

                            }
                        });

                        jobsAdapter.setData(listJob);
//
                        rcv_jobapply.setAdapter(jobsAdapter);
                        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(JobApplyActivity.this);
                        rcv_jobapply.setLayoutManager(linearLayoutManager2);



                    }
                });


    }
}