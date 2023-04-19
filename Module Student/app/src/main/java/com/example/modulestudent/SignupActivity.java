package com.example.modulestudent;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;


public class SignupActivity extends AppCompatActivity {

    TextInputLayout etPassword, etUsername, etConfirmPassword;
    Button btnSignup;
    ProgressDialog progressDialog;
    FirebaseFirestore db;
    public static String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        db = FirebaseFirestore.getInstance();

        refId();

        initListener();
    }

    private void initListener() {
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickSignup();
                //Toast.makeText(SignupActivity.this, "Click sign up", Toast.LENGTH_LONG).show();

            }
        });

    }

    private void onClickSignup() {
        String strEmail = etUsername.getEditText().getText().toString().trim();
        String strPassword = etPassword.getEditText().getText().toString().trim();
        String strConfirmPassword = etConfirmPassword.getEditText().getText().toString().trim();

        if(strEmail.isEmpty()){
            Toast.makeText(SignupActivity.this, "Username is empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if(strPassword.isEmpty()){
            Toast.makeText(SignupActivity.this, "Password is empty", Toast.LENGTH_SHORT).show();
            return;
        }

        if(!strPassword.equals(strConfirmPassword)){
            Toast.makeText(SignupActivity.this, "Confirm password not correct", Toast.LENGTH_LONG).show();
        }
        else {
            Student student = new Student();
            student.setEmail(strEmail);
            student.setPassword(strPassword);
            student.setUsername(strEmail);

            db.collection("student").document(student.getEmail()).set(student).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    //save to session
                    SharedPreferences sharedPreferences = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("EMAIL", strEmail);
                    editor.commit();
                    //
                    Intent intent = new Intent(SignupActivity.this, Profile.class);
                    startActivity(intent);
                    finish();
                }
            });


        }
    }

    private void refId() {
        btnSignup = findViewById(R.id.btnSignup);
        etPassword = findViewById(R.id.etPassword);
        etUsername = findViewById(R.id.etUsername);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);

        progressDialog = new ProgressDialog(this);
    }
}