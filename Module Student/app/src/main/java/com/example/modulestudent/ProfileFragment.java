package com.example.modulestudent;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class ProfileFragment extends Fragment {
    private Button btnLogout, btnViewJob;
    private TextView tv_username;
    private TextView edt_name, tv_birthDateTitle, edt_birthDate
            , tv_gender, edt_phone, edt_email, edt_major, edt_gpa, edit_skills;
    private FirebaseFirestore db;
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void mappingData(View view) {
        tv_username = view.findViewById(R.id.tv_username);
        edt_name = view.findViewById(R.id.edt_name);
        tv_birthDateTitle = view.findViewById(R.id.tv_birthDateTitle);
        edt_birthDate = view.findViewById(R.id.edt_birthDate);
        tv_gender = view.findViewById(R.id.tv_gender);
        edt_phone = view.findViewById(R.id.edt_phone);
        edt_email = view.findViewById(R.id.edt_email);
        edt_major = view.findViewById(R.id.edt_major);
        edt_gpa = view.findViewById(R.id.edt_gpa);
        edit_skills = view.findViewById(R.id.edit_skills);
        btnViewJob = view.findViewById(R.id.btnViewJob);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        db = FirebaseFirestore.getInstance();

        mappingData(view);
        btnLogout = view.findViewById(R.id.btnLogout);
        
        setInfor();

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences sharedPreferences = getContext().getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("EMAIL");
                editor.apply();

                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);

                getActivity().finish();

            }
        });

        btnViewJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), JobApplyActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    private void setInfor() {

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        String email = sharedPreferences.getString("EMAIL","");

        db.collection("student")
                .whereEqualTo("email", email)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                tv_username.setText(document.getString("username"));
                                edt_name.setText(document.getString("name"));
                                edt_birthDate.setText(document.getString("birthDate"));
                                tv_gender.setText(document.getString("sex"));
                                edt_phone.setText(document.getString("phone"));
                                edt_email.setText(document.getString("email"));
                                edt_major.setText(document.getString("major"));
                                edt_gpa.setText(String.valueOf(document.getDouble("gpa")));

                                List<String> skills = (List<String>) document.get("skills");
                                edit_skills.setText(skills.toString());
//                              Lis
                            }
                        } else {
                            Log.d("Error", "Error getting documents: ", task.getException());
                        }
                    }
                });

    }


}