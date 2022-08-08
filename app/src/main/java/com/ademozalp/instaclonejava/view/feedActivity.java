package com.ademozalp.instaclonejava.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.ademozalp.instaclonejava.R;
import com.ademozalp.instaclonejava.adapter.postAdapter;
import com.ademozalp.instaclonejava.databinding.ActivityFeedBinding;
import com.ademozalp.instaclonejava.model.Post;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.Map;

public class feedActivity extends AppCompatActivity {

    private ActivityFeedBinding binding;
    private FirebaseAuth auth;
    private FirebaseFirestore firebaseFirestore;
    ArrayList<Post> postArrayList;
    postAdapter Postadapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFeedBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        postArrayList = new ArrayList<>();
        auth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        getData();

        binding.rcyclerListView.setLayoutManager(new LinearLayoutManager(this));
        Postadapter = new postAdapter(postArrayList);
        binding.rcyclerListView.setAdapter(Postadapter);
    }

    private void getData() {
        firebaseFirestore.collection("Posts").orderBy("date", Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error != null){
                    Toast.makeText(feedActivity.this,error.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                }
                if(value != null){
                    for(DocumentSnapshot document : value.getDocuments()){
                        Map<String, Object> data = document.getData();

                        String email =(String) data.get("email");
                        String comment = (String) data.get("comment");
                        String downloadUrl = (String) data.get("downloadUrl");

                        Post post = new Post(email,comment,downloadUrl);
                        postArrayList.add(post);
                    }
                    Postadapter.notifyDataSetChanged();
                }
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.optionsmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.add_post){
            Intent intent = new Intent(feedActivity.this, uploadActivity.class);
            startActivity(intent);
        }
        else if(item.getItemId() == R.id.logout){
            auth.signOut();
            Intent intentToMain = new Intent(feedActivity.this, MainActivity.class);
            startActivity(intentToMain);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}