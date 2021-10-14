package com.begers.instagramclone.View;

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
import android.widget.Adapter;
import android.widget.Toast;

import com.begers.instagramclone.R;
import com.begers.instagramclone.adapter.PostAdapter;
import com.begers.instagramclone.databinding.ActivityFeedBinding;
import com.begers.instagramclone.model.Post;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FeedActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseFirestore firebaseFirestore;
    private ActivityFeedBinding binding;

    List<Post> postList;

    PostAdapter postAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFeedBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        postList = new ArrayList<>();

        auth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        getData();
        binding.recyclerViewFeed.setLayoutManager(new LinearLayoutManager(this));
        postAdapter = new PostAdapter(postList);
        binding.recyclerViewFeed.setAdapter(postAdapter);
    }

    private void getData(){

        firebaseFirestore.collection("Post").orderBy("date", Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null){
                    Toast.makeText(FeedActivity.this, error.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                }

                if (value != null){
                    for (DocumentSnapshot snapshot : value.getDocuments()){

                        Map<String, Object> data = snapshot.getData();

                        String userEmail = (String) data.get("useremail");
                        String comment = (String) data.get("comment");
                        String downloadurl = (String) data.get("downloadurl");

                        postList.add(new Post(userEmail, comment, downloadurl));
                    }
                    postAdapter.notifyDataSetChanged();
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.option_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.add_post){
            Intent intent = new Intent(FeedActivity.this, UploadActivity.class);
            startActivity(intent);
        }else if (item.getItemId() == R.id.signout){

            auth.signOut();

            Intent intent = new Intent(FeedActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}