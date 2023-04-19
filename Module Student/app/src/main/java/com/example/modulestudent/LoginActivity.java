package com.example.modulestudent;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class LoginActivity extends AppCompatActivity {
    TextInputLayout etPassword, etUsername;
    Button btnLogin;
    TextView tvCreateaccount;
    ProgressDialog progressDialog;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //reference Id component
        refId();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Test :<
                String userName = etUsername.getEditText().getText().toString().trim();
                String password = etPassword.getEditText().getText().toString().trim();
                if(userName.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Username is empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(password.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Username is empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!userName.isEmpty() && !password.isEmpty()){
                    Log.d("Ckeck user", "Click rồi nek: ");

                    db.collection("student")
                            .whereEqualTo("email", userName)
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        if(task.getResult().isEmpty()){
                                            Toast.makeText(LoginActivity.this,"Username not correct", Toast.LENGTH_SHORT).show();
                                            return;
                                        }
                                        Log.d("ACC", task.getResult().toString());
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            Log.d("ACC", document.getString("email").toString());
                                            Log.d("ACC", document.getString("password").toString());

                                            if(document.get("password").equals(password)){
                                                SharedPreferences sharedPreferences = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
                                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                                editor.putString("EMAIL", userName);
                                                editor.commit();

                                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                                startActivity(intent);
                                            }
                                            else{
                                                Toast.makeText(LoginActivity.this,"Password not correct", Toast.LENGTH_SHORT).show();

                                            }
                                            Log.d("Ckeck user", document.getId() + " => " + document.getData());
                                        }
                                    } else {
                                        Log.d("Ckeck user", "Error getting documents: ", task.getException());
                                    }
                                }
                            });
                }

            }
        });

        tvCreateaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });
    }

    public void refId(){
        db = FirebaseFirestore.getInstance();

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);

        tvCreateaccount = findViewById(R.id.tvCreateaccount);
        tvCreateaccount = findViewById(R.id.tvCreateaccount);

        progressDialog = new ProgressDialog(LoginActivity.this);
    }
}