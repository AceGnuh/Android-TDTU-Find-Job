package com.example.modulestudent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class DetailJobFragment extends Fragment {
    public static final String TAG = DetailJobFragment.class.getName();
    CircleImageView imv_logo;
    TextView tv_jobName, tv_jobCompany, tv_Date, tv_address, tv_salary, tv_major, tv_description_content;
    Button btnApply;
    ImageView btnBack;

    private void mappingView(View view) {
        imv_logo = view.findViewById(R.id.imv_logo);
        tv_jobName = view.findViewById(R.id.tv_jobName);
        tv_jobCompany = view.findViewById(R.id.tv_jobCompany);
        tv_Date = view.findViewById(R.id.tv_Date);
        tv_address = view.findViewById(R.id.tv_address);
        tv_salary = view.findViewById(R.id.tv_salary);
        tv_major = view.findViewById(R.id.tv_major);
        tv_description_content = view.findViewById(R.id.tv_description_content);
        btnApply = view.findViewById(R.id.btnApply);
        btnBack = view.findViewById(R.id.btnBack);

    }

    public void setDataJob(HightlightJobs job){
        tv_jobName.setText(job.getJobName());
        tv_jobCompany.setText(job.getCompanyName());
        tv_Date.setText(job.getDate());
        tv_address.setText(job.getAddress());
        tv_salary.setText(job.getSalary());
        tv_major.setText(job.getMajor());
        tv_description_content.setText(job.getDescription());
        //set image
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
                                imv_logo.setImageBitmap(bitmap);
                            }
                        });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_detail_job_fragment, container, false);

        mappingView(view);



        Bundle bundle = getArguments();
        if(bundle != null){
            HightlightJobs job = (HightlightJobs) bundle.get("job_object");
            if(job != null){
                ///////
                setDataJob(job);

                btnApply.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        applyJob(job);
                    }
                });
            }
        }
        

        
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goBack();
            }
        });


        return view;
    }

    private void goBack() {
        if(getFragmentManager() != null){
            getFragmentManager().popBackStack();
        }
    }

    private void applyJob(HightlightJobs job) {
        //////primary key id student
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        String email = sharedPreferences.getString("EMAIL","");
        ////////////////

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        DocumentReference studentRef = db.collection("job").document(job.getId());

// Atomically add a new region to the "regions" array field.
        studentRef.update("student", FieldValue.arrayUnion(email));

        Toast.makeText(getContext(), "Apply thành công", Toast.LENGTH_SHORT).show();



    }


}