package com.example.modulestudent;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ListJob extends AppCompatActivity {
    private RecyclerView rcv_list;
    private JobAdapter jobAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listjob);

//        rcv_list = findViewById(R.id.rcv_listjob);
//
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
//        rcv_list.setLayoutManager(linearLayoutManager);
//
//        jobAdapter = new JobAdapter(getJobsList());
//        rcv_list.setAdapter(jobAdapter);

    }

//    private List<Job> getJobsList() {
//        List<Job> jobList = new ArrayList<Job>();
//        jobList.add(new Job(R.drawable.logo1, "Tuyển lập trình viên","FPT Software", "Quận 8", "Thỏa thuận","22/12/2022"));
//        jobList.add(new Job(R.drawable.logo1, "Tuyển lập trình viên","FPT Software", "Quận 8", "Thỏa thuận","22/12/2022"));
//        jobList.add(new Job(R.drawable.logo1, "Tuyển lập trình viên","FPT Software", "Quận 8", "Thỏa thuận","22/12/2022"));
//        jobList.add(new Job(R.drawable.logo1, "Tuyển lập trình viên","FPT Software", "Quận 8", "Thỏa thuận","22/12/2022"));
//
//        return jobList;
//    }
}
