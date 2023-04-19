package com.example.modulestudent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MajorAdapter extends RecyclerView.Adapter<MajorAdapter.MajorViewHolder> {
    private List<Major> majorList;

    public void setData(List<Major> list) {
        this.majorList = list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public MajorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_major, parent,false);
        return new MajorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MajorViewHolder holder, int position) {
        Major major= (Major) majorList.get(position);
        if(major == null) {
            return;
        }
        holder.majorImage.setImageResource(major.getResourceID());
        holder.majorName.setText(major.getMajorName());
        holder.majorDescription.setText(major.getMajorDescription());
    }

    public MajorAdapter(List<Major> majorList) {
        this.majorList = majorList;
    }

    @Override
    public int getItemCount() {
        if (majorList != null) {
            return majorList.size();
        }
        return 0;
    }

    public class MajorViewHolder extends RecyclerView.ViewHolder {
        private ImageView majorImage;
        private TextView majorName;
        private TextView majorDescription;

        public MajorViewHolder(@NonNull View itemView) {
            super(itemView);
            majorImage = itemView.findViewById(R.id.imv_major);
            majorName = itemView.findViewById(R.id.tv_majorName);
            majorDescription = itemView.findViewById(R.id.tv_majorDescription);
        }
    }
}
