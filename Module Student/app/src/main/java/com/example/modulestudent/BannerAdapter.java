package com.example.modulestudent;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;

import java.util.List;

public class BannerAdapter extends PagerAdapter {
    @NonNull
    private Context context;
    List<Banner> bannerList;

    public BannerAdapter(@NonNull Context context, List<Banner> bannerList) {
        this.context = context;
        this.bannerList = bannerList;
    }

    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_photo, container, false);
        ImageView imageView = view.findViewById(R.id.imv_photo);

        Banner banner = (Banner) bannerList.get(position);
        if (banner != null) {
            Glide.with(context).load(banner.getResourceID()).into(imageView);
        }
        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        if (bannerList != null) {
            return bannerList.size();
        }
        return 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);

    }
}
