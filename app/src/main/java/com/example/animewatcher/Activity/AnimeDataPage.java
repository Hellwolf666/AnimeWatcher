package com.example.animewatcher.Activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.animewatcher.Adapter.EpisodeAdapter;
import com.example.animewatcher.Model.Episode;
import com.example.animewatcher.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnimeDataPage extends AppCompatActivity {

    EpisodeAdapter adapter;
    TextView title,desc;
    ImageView thumb;
    String id,curr_title,curr_desc,curr_image;
    List<Episode> currEpisodeList;
    RecyclerView episodeRecyclerView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anime_data_page);
        thumb = findViewById(R.id.image);
        title = findViewById(R.id.title);
        desc = findViewById(R.id.desc);
        episodeRecyclerView = findViewById(R.id.episodeRecyclerView);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        episodeRecyclerView.setLayoutManager(llm);

        id = getIntent().getStringExtra("id");
        curr_title = getIntent().getStringExtra("title");
        curr_image = getIntent().getStringExtra("image");
        curr_desc = getIntent().getStringExtra("desc");

        title.setText(curr_title);
        desc.setText(curr_desc);
        Glide.with(this).load(curr_image).into(thumb);

        Query query = FirebaseDatabase.getInstance().getReference().child("anime").child(id).child("episodes").orderByChild("id");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterable<DataSnapshot> children = snapshot.getChildren();
                Map<String,Episode> episodeMap = new HashMap<>();
                for (DataSnapshot child : children) {
                    Episode value = child.getValue(Episode.class);
                    assert value != null;
                    value.setUid(child.getKey());
                    episodeMap.put(child.getKey(),value);
                }
                currEpisodeList = new ArrayList<>(episodeMap.values());
                adapter = new EpisodeAdapter(getApplicationContext(),currEpisodeList);
                episodeRecyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                try {
                    throw new Exception("No dATA");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
  }

}
