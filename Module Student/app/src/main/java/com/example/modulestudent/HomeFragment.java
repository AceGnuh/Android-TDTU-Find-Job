package com.example.modulestudent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

public class HomeFragment extends Fragment {
    private ViewPager viewPager;
    private CircleIndicator circleIndicator;
    private BannerAdapter bannerAdapter;
    private MajorAdapter majorAdapter;
    private List<Banner> bannerList;
    private Timer timer;
    private RecyclerView rcvCategory;
    private RecyclerView rcvCategory1;
    private RecyclerView rcvCategory2;
    private RecyclerView rcvCategory3;
    private HighlightJobsAdapter highlightJobsAdapter;
    FirebaseFirestore db;
    static List<Job> listJobDb;
    MainActivity mainActivity;
    private SearchView searchView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);




        db = FirebaseFirestore.getInstance();
        listJobDb = new ArrayList<>();
        Log.d("Data_JOb_111", listJobDb.toString());

        mainActivity = (MainActivity) getActivity();
        viewPager = view.findViewById(R.id.vp_banner);
        circleIndicator = view.findViewById(R.id.ci_banner);
        rcvCategory = view.findViewById(R.id.rcv_category);
        rcvCategory1 = view.findViewById(R.id.rcv_category1);
        rcvCategory2 = view.findViewById(R.id.rcv_category2);
        rcvCategory3 = view.findViewById(R.id.rcv_category3);


        getListJobDb();

        bannerList = getListBanner();
        bannerAdapter = new BannerAdapter(getContext(), bannerList);
        viewPager.setAdapter(bannerAdapter);

        circleIndicator.setViewPager(viewPager);
        bannerAdapter.registerDataSetObserver(circleIndicator.getDataSetObserver());
        autoSilde();


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        rcvCategory.setLayoutManager(linearLayoutManager);

        majorAdapter = new MajorAdapter(getMajorList());
        rcvCategory.setAdapter(majorAdapter);

        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        rcvCategory1.setLayoutManager(linearLayoutManager1);

        highlightJobsAdapter = new HighlightJobsAdapter(getHighlightJobs());
        rcvCategory1.setAdapter(highlightJobsAdapter);

        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        rcvCategory2.setLayoutManager(linearLayoutManager2);

        highlightJobsAdapter = new HighlightJobsAdapter(getHighlightJobs());
        rcvCategory2.setAdapter(highlightJobsAdapter);

        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        rcvCategory3.setLayoutManager(linearLayoutManager3);

        highlightJobsAdapter = new HighlightJobsAdapter(getHighlightJobs());
        rcvCategory3.setAdapter(highlightJobsAdapter);


        return view;
    }

    private List<HightlightJobs> getListJobDb() {
        List<HightlightJobs> dataJob = new ArrayList<>();
        Log.d("Job nek", "Get data job");

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





                                dataJob.add(job);

                            }
                            List<HightlightJobs> listJob1 = new ArrayList<>();

                            for(int i = 0; i < 5; i++){
                                listJob1.add(dataJob.get(i));
                            }

                            highlightJobsAdapter = new HighlightJobsAdapter(listJob1, new HighlightJobsAdapter.IClickItemListener() {
                                @Override
                                public void OnClickItemListener(HightlightJobs hightlightJobs) {
                                    mainActivity.goToDetailFragment(hightlightJobs);
                                }
                            });
                            rcvCategory1.setAdapter(highlightJobsAdapter);

                            //Viec lam moi

                            List<HightlightJobs> listJob2 = new ArrayList<>();

                            for(int i = 5; i < 10; i++){
                                listJob2.add(dataJob.get(i));
                            }

                            //highlightJobsAdapter = new HighlightJobsAdapter(listJob2);
                            rcvCategory2.setAdapter(new HighlightJobsAdapter(listJob2, new HighlightJobsAdapter.IClickItemListener() {
                                @Override
                                public void OnClickItemListener(HightlightJobs hightlightJobs) {
                                    mainActivity.goToDetailFragment(hightlightJobs);
                                }
                            }));

                            //Viec lam co the ban quan tam

                            List<HightlightJobs> listJob3 = new ArrayList<>();

                            for(int i = 10; i < 15; i++){
                                listJob3.add(dataJob.get(i));
                            }

                            //highlightJobsAdapter = new HighlightJobsAdapter(listJob2);
                            rcvCategory3.setAdapter(new HighlightJobsAdapter(listJob3, new HighlightJobsAdapter.IClickItemListener() {
                                @Override
                                public void OnClickItemListener(HightlightJobs hightlightJobs) {
                                    mainActivity.goToDetailFragment(hightlightJobs);
                                }
                            }));

                        } else {
                            Log.d("Job nek", "Error getting documents: ", task.getException());
                        }



                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("Job nek", e.toString());

                    }
                });
        Log.d("Data_JOb", dataJob.toString());
        return dataJob;
    }



    private List<HightlightJobs> getHighlightJobs() {
        List<HightlightJobs> highlightJobsList = new ArrayList<>();
        highlightJobsList.add(new HightlightJobs(R.drawable.logo1, "Tuyển lập trình viên", "FPT Software","Quận 8", "Thỏa thuận","22/12/2022"));
        highlightJobsList.add(new HightlightJobs(R.drawable.logo1, "Tuyển lập trình viên", "FPT Software","Quận 8", "Thỏa thuận","22/12/2022"));
        highlightJobsList.add(new HightlightJobs(R.drawable.logo1, "Tuyển lập trình viên", "FPT Software","Quận 8", "Thỏa thuận","22/12/2022"));
        highlightJobsList.add(new HightlightJobs(R.drawable.logo1, "Tuyển lập trình viên", "FPT Software","Quận 8", "Thỏa thuận","22/12/2022"));
        highlightJobsList.add(new HightlightJobs(R.drawable.logo1, "Tuyển lập trình viên", "FPT Software","Quận 8", "Thỏa thuận","22/12/2022"));
        highlightJobsList.add(new HightlightJobs(R.drawable.logo1, "Tuyển lập trình viên", "FPT Software","Quận 8", "Thỏa thuận","22/12/2022"));
        highlightJobsList.add(new HightlightJobs(R.drawable.logo1, "Tuyển lập trình viên", "FPT Software","Quận 8", "Thỏa thuận","22/12/2022"));
        highlightJobsList.add(new HightlightJobs(R.drawable.logo1, "Tuyển lập trình viên", "FPT Software","Quận 8", "Thỏa thuận","22/12/2022"));
        highlightJobsList.add(new HightlightJobs(R.drawable.logo1, "Tuyển lập trình viên", "FPT Software","Quận 8", "Thỏa thuận","22/12/2022"));

        return highlightJobsList;
    }

    private List<Major> getMajorList() {
        List<Major> majorList = new ArrayList<>();
        majorList.add(new Major(R.drawable.major1, "Công nghệ thông tin", "Xem thêm 99+ việc làm"));
        majorList.add(new Major(R.drawable.major2, "Marketing", "Xem thêm 99+ việc làm"));
        majorList.add(new Major(R.drawable.major3, "Ngôn ngữ Anh", "Xem thêm 99+ việc làm"));
        majorList.add(new Major(R.drawable.major4, "Tài chính ngân hàng", "Xem thêm 99+ việc làm"));
        majorList.add(new Major(R.drawable.major5, "Thiết kế thời trang", "Xem thêm 99+ việc làm"));
        majorList.add(new Major(R.drawable.major6, "Luật", "Xem thêm 99+ việc làm"));
        majorList.add(new Major(R.drawable.major7, "Y dược", "Xem thêm 99+ việc làm"));
        majorList.add(new Major(R.drawable.major8, "Kiến trúc", "Xem thêm 99+ việc làm"));
        majorList.add(new Major(R.drawable.major9, "Điện - điện tử", "Xem thêm 99+ việc làm"));
        majorList.add(new Major(R.drawable.major10, "Công nghệ sinh học", "Xem thêm 99+ việc làm"));
        return majorList;
    }


    private List<Banner> getListBanner() {
        List<Banner> bannerList = new ArrayList<Banner>();
        bannerList.add(new Banner(R.drawable.banner1));
        bannerList.add(new Banner(R.drawable.banner2));
        bannerList.add(new Banner(R.drawable.banner3));
        bannerList.add(new Banner(R.drawable.banner4));
        bannerList.add(new Banner(R.drawable.banner5));
        bannerList.add(new Banner(R.drawable.banner6));
        return bannerList;
    }
    private void autoSilde() {
        if (bannerList == null || bannerList.isEmpty() || viewPager == null) {
            return;
        }
        if (timer == null) {
            timer = new Timer();
        }
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        int current = viewPager.getCurrentItem();
                        int total = bannerList.size() - 1;
                        if (current < total) {
                            current++;
                            viewPager.setCurrentItem(current);
                        }
                        else {
                            viewPager.setCurrentItem(0);
                        }

                    }
                });
            }
        },300,4000);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }
}