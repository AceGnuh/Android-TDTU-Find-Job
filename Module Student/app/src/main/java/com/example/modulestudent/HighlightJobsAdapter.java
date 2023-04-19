package com.example.modulestudent;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class HighlightJobsAdapter extends RecyclerView.Adapter<HighlightJobsAdapter.HighlightJobsViewHolder> {
    List<HightlightJobs> list;

    private IClickItemListener mIClickItemListener;

    public interface IClickItemListener{
        public void OnClickItemListener(HightlightJobs hightlightJobs);
    }

    public HighlightJobsAdapter(List<HightlightJobs> highlightJobs) {
        this.list = highlightJobs;
    }
    public HighlightJobsAdapter(List<HightlightJobs> highlightJobs, IClickItemListener mIClickItemListener) {
        this.list = highlightJobs;
        this.mIClickItemListener = mIClickItemListener;
    }
    public void setData(List<HightlightJobs> list) {
        this.list = list;
    }
    @NonNull
    @Override
    public HighlightJobsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_highlights, parent,false);
        return new HighlightJobsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HighlightJobsAdapter.HighlightJobsViewHolder holder, int position) {
        HightlightJobs job = list.get(position);
        if(job == null) {
            return;
        }
        holder.imv_logo.setImageResource(job.getResourceID());

        StorageReference mStorageReference;
        mStorageReference = FirebaseStorage.getInstance().getReference("img/"+job.getImg());
        if(job.getImg() != null){
            Log.d("img::", job.getImg());
            try {
                File localfile = File.createTempFile("tempfile",".png");
                mStorageReference.getFile(localfile)
                        .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                Bitmap bitmap = BitmapFactory.decodeFile(localfile.getAbsolutePath());
                                holder.imv_logo.setImageBitmap(bitmap);
                            }
                        });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        holder.tv_jobName.setText(job.getJobName());
        holder.tv_companyName.setText(job.getCompanyName());
        holder.tv_address.setText(job.getAddress());
        holder.tv_salary.setText(job.getSalary());
        holder.tv_date.setText(job.getDate());

        holder.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIClickItemListener.OnClickItemListener(job);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    public class HighlightJobsViewHolder extends RecyclerView.ViewHolder {
        private ImageView imv_logo;
        private TextView tv_jobName;
        private TextView tv_companyName;
        private TextView tv_salary;
        private TextView tv_address;
        private TextView tv_date;
        private CardView cardview;

        public HighlightJobsViewHolder(@NonNull View itemView) {
            super(itemView);
            imv_logo = itemView.findViewById(R.id.imv_logo);
            tv_jobName = itemView.findViewById(R.id.tv_jobName);
            tv_companyName = itemView.findViewById(R.id.tv_companyName);
            tv_salary = itemView.findViewById(R.id.tv_salary);
            tv_address = itemView.findViewById(R.id.tv_address);
            tv_date = itemView.findViewById(R.id.tv_date);
            cardview = itemView.findViewById(R.id.cardview);
        }
    }
}
