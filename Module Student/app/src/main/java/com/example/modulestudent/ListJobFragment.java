package com.example.modulestudent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ListJobFragment extends Fragment {

    private RecyclerView rcv_job;
    private List<HightlightJobs> listJob;
    private FirebaseFirestore db;
    private JobAdapter jobsAdapter;
    MainActivity mainActivity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_list_job_fragment, container, false);

        mainActivity = (MainActivity) getActivity();
        rcv_job = view.findViewById(R.id.rcv_job);
        SearchView searchView = (SearchView) view.findViewById(R.id.searchView);
        listJob = new ArrayList<>();
        db = FirebaseFirestore.getInstance();

        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getContext());
        rcv_job.setLayoutManager(linearLayoutManager2);

        jobsAdapter = new JobAdapter(listJob);
        rcv_job.setAdapter(jobsAdapter);

        db.collection("job").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

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

                            }

                            Log.d("_Length", String.valueOf(listJob.size()));

                            jobsAdapter = new JobAdapter(listJob, new JobAdapter.IClickItemListener() {
                                @Override
                                public void OnClickItemListener(HightlightJobs hightlightJobs) {
                                    mainActivity.goToDetailFragment(hightlightJobs);
                                }
                            });

                            rcv_job.setAdapter(jobsAdapter);
                        }
                    }
                });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                List<HightlightJobs> listJobSearch = new ArrayList<>();

                db.collection("job").get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    listJob.clear();
                                    for (QueryDocumentSnapshot document : task.getResult()) {

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

//                                        db.collection("employee")
//                                                .whereEqualTo("name", job.getCompanyName())
//                                                .get()
//                                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                                                    @Override
//                                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                                                        if (task.isSuccessful()) {
//                                                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                                                //Log.d(TAG, document.getId() + " => " + document.getData());
//                                                                job.setJobName(document.getString("name"));
//                                                            }
//                                                        }
//                                                    }
//                                                });
                                        listJob.add(job);

                                    }

                                    listJobSearch.clear();
                                    for(HightlightJobs job: listJob){
                                        if(job.getJobName().toLowerCase(Locale.ROOT).matches("(.*)"+s.toLowerCase(Locale.ROOT)+"(.*)")
                                                || job.getCompanyName().toLowerCase(Locale.ROOT).matches("(.*)"+s.toLowerCase(Locale.ROOT)+"(.*)")){
                                            listJobSearch.add(job);
                                        }
                                    }
                                    jobsAdapter = new JobAdapter(listJobSearch, new JobAdapter.IClickItemListener() {
                                        @Override
                                        public void OnClickItemListener(HightlightJobs hightlightJobs) {
                                            mainActivity.goToDetailFragment(hightlightJobs);
                                        }
                                    });
                                    jobsAdapter.notifyDataSetChanged();
                                    rcv_job.setAdapter(jobsAdapter);


                                }
                            }
                        });

                return false;
            }
        });

        return view;
    }
}