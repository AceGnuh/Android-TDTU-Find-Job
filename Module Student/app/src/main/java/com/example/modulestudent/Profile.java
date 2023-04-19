package com.example.modulestudent;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Profile extends AppCompatActivity {
    private Button btnSave;
    private TextView tv_username;
    private EditText edt_name, edt_birthDate, edt_phone, edt_email, edt_major, edt_gpa, edit_skills;
    private FirebaseFirestore db;
    private RadioButton genderOption_male, genderOption_female;
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        
        mappingData();
        db = FirebaseFirestore.getInstance();


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveDate();
            }
        });

    }

    private void saveDate() {
        String name = edt_name.getText().toString().trim();
        String birthDate = edt_birthDate.getText().toString().trim();
        String phone = edt_phone.getText().toString().trim();
        String email = edt_email.getText().toString().trim();
        String major = edt_major.getText().toString().trim();
        String gpa = edt_gpa.getText().toString().trim();
        String skills = edit_skills.getText().toString().trim();
        String gender = "";
        if(genderOption_female.isChecked()){
            gender = "female";
        }
        else {
            gender = "male";
        }

        if(!checkData(name, birthDate, phone, email, major, gpa, skills, gender)){
            return;
        }

        SharedPreferences sharedPreferences = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        String emaiSession = sharedPreferences.getString("EMAIL","");

        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("birthDate", birthDate);
        map.put("gender", gender);
        map.put("phone", phone);
        map.put("major", major);
        map.put("gpa", Double.parseDouble(gpa));
        map.put("skills", Arrays.asList(skills));

        DocumentReference studentRef = db.collection("student").document(emaiSession);

        studentRef.update(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(getApplicationContext(), "update success", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(Profile.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }

    private boolean checkData(String name, String birthDate, String phone, String email, String major, String gpa, String skills, String gender) {
        if(name == "" || birthDate == "" || phone == "" || email =="" || major == "" || gpa == "" || skills == ""){
            Toast.makeText(getApplicationContext(), "Pls enter full information", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(Double.parseDouble(gpa) > 10 || Double.parseDouble(gpa) < 0){
            Toast.makeText(getApplicationContext(), "GPA not correct", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(phone.length() != 10){
            Toast.makeText(getApplicationContext(), "Phone number not correct", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void mappingData() {
        btnSave = findViewById(R.id.btnSave);
        edt_name = findViewById(R.id.edt_name);
        edt_birthDate = findViewById(R.id.edt_birthDate);
        edt_phone = findViewById(R.id.edt_phone);
        edt_email = findViewById(R.id.edt_email);
        edt_major = findViewById(R.id.edt_major);
        edt_gpa = findViewById(R.id.edt_gpa);
        edit_skills = findViewById(R.id.edit_skills);
        genderOption_male = findViewById(R.id.genderOption_male);
        genderOption_female = findViewById(R.id.genderOption_female);
    }
}
