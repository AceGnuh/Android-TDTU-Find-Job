package com.example.modulestudent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{
    public ActionBarDrawerToggle actionBarDrawerToggle;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createBottomNavigationView();
    }

//    public void createNavigationView(){
//        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);
//
//        // pass the Open and Close toggle for the drawer layout listener
//        // to toggle the button
//        drawerLayout.addDrawerListener(actionBarDrawerToggle);
//        actionBarDrawerToggle.syncState();
//
//        // to make the Navigation drawer icon always appear on the action bar
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//
//        //NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//
//        mNavigationView.setNavigationItemSelectedListener(this::onNavigationItemSelected);
//        mNavigationView.setCheckedItem(R.id.nav_view);
//        onNavigationItemSelected(mNavigationView.getMenu().getItem(0));
//    }

    public void goToDetailFragment(HightlightJobs job){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        DetailJobFragment detailJobFragment = new DetailJobFragment();

        Bundle bundle = new Bundle();
        bundle.putSerializable("job_object", job);

        detailJobFragment.setArguments(bundle);

        fragmentTransaction.replace(R.id.content_frame, detailJobFragment);
        fragmentTransaction.addToBackStack(DetailJobFragment.TAG);
        fragmentTransaction.commit();
    }

    public void createBottomNavigationView(){
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.home);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new HomeFragment()).commit();
                return true;
            case R.id.job:
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new ListJobFragment()).commit();
                return true;

            case R.id.search:
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new NotificationFragment()).commit();
                return true;

            case R.id.app:
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new ProfileFragment()).commit();
                return true;
            case R.id.nav_account:
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                //fragmentTransaction.replace(R.id.content_frame, new FragmentApp());
                //fragmentTransaction.commit();
                //drawerLayout.closeDrawers();
                //Toast.makeText(this, "Hello", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.nav_logout:
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
                return true;
        }
        return false;
    }
}