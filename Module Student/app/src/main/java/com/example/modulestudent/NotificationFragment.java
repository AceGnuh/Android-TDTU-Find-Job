package com.example.modulestudent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class NotificationFragment extends Fragment {
    private RecyclerView rcvNotification;
    private MessageAdapter mMessageAdapter;
    private List<Message> mListJob;
    private FirebaseFirestore db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_notification_fragment, container, false);

        mappingView(view);
        db = FirebaseFirestore.getInstance();

        db.collection("notification")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document : task.getResult()){
                                Log.d("TAG_MSG", document.getData().toString());
                                Message msg = document.toObject(Message.class);
                                mListJob.add(msg);
                            }
                        }
                        else {
                            //fail
                        }
                        mMessageAdapter.setListMessage(mListJob);
                        rcvNotification.setAdapter(mMessageAdapter);
                    }
                });


        return view;
    }

    private void mappingView(View view) {
        rcvNotification = view.findViewById(R.id.rcvNotification);
        rcvNotification.setLayoutManager(new LinearLayoutManager(getContext()));
        mListJob = new ArrayList<>();
        mMessageAdapter = new MessageAdapter(mListJob);
    }
}