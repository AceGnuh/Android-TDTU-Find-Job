package com.example.modulestudent;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

public class Home extends AppCompatActivity {
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

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        viewPager = findViewById(R.id.vp_banner);
        circleIndicator = findViewById(R.id.ci_banner);
        rcvCategory = findViewById(R.id.rcv_category);
        rcvCategory1 = findViewById(R.id.rcv_category1);
        rcvCategory2 = findViewById(R.id.rcv_category2);
        rcvCategory3 = findViewById(R.id.rcv_category3);

        bannerList = getListBanner();
        bannerAdapter = new BannerAdapter(this, bannerList);
        viewPager.setAdapter(bannerAdapter);

        circleIndicator.setViewPager(viewPager);
        bannerAdapter.registerDataSetObserver(circleIndicator.getDataSetObserver());
        autoSilde();


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        rcvCategory.setLayoutManager(linearLayoutManager);

        majorAdapter = new MajorAdapter(getMajorList());
        rcvCategory.setAdapter(majorAdapter);

        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        rcvCategory1.setLayoutManager(linearLayoutManager1);

        highlightJobsAdapter = new HighlightJobsAdapter(getHighlightJobs());
        rcvCategory1.setAdapter(highlightJobsAdapter);

        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        rcvCategory2.setLayoutManager(linearLayoutManager2);

        highlightJobsAdapter = new HighlightJobsAdapter(getHighlightJobs());
        rcvCategory2.setAdapter(highlightJobsAdapter);

        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        rcvCategory3.setLayoutManager(linearLayoutManager3);

        highlightJobsAdapter = new HighlightJobsAdapter(getHighlightJobs());
        rcvCategory3.setAdapter(highlightJobsAdapter);


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
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }
}
