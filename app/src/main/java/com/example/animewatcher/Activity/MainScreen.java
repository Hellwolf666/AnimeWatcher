package com.example.animewatcher.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.animewatcher.Adapter.AnimeAdapter;
import com.example.animewatcher.Helper.About;
import com.example.animewatcher.Account.Login;
import com.example.animewatcher.Account.SignUp;
import com.example.animewatcher.Adapter.RecyclerViewInterfaceClick;
import com.example.animewatcher.Model.Anime;
import com.example.animewatcher.R;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainScreen extends AppCompatActivity implements RecyclerViewInterfaceClick, NavigationView.OnNavigationItemSelectedListener {

    RecyclerView featureRecycleView;
    AnimeAdapter animeAdapter;
    ImageView menuIcon, profileIcon;
    LinearLayout content;
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);


        content = findViewById(R.id.content);

        menuIcon = findViewById(R.id.menu_icon);
        profileIcon = findViewById(R.id.profileIcon);


        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);

        navigationDrawer();
        featureRecycleView = findViewById(R.id.featured_recycler);


        featureRecycleView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        Query query = FirebaseDatabase.getInstance().getReference().child("anime").orderByChild("id");
        query.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Iterable<DataSnapshot> children = snapshot.getChildren();
                Map<String,Anime> anime = new HashMap<>();

                for (DataSnapshot child : children) {
                    Anime value = child.getValue(Anime.class);
                    assert value != null;
                    value.setUid(child.getKey());

                    anime.put(child.getKey(),value);
                }
                List<Anime> animeList = new ArrayList<>(anime.values());
                animeAdapter = new AnimeAdapter(animeList);
                featureRecycleView.setAdapter(animeAdapter);
            }

            @Override
            public void onCancelled( DatabaseError error) {
                try {
                    throw new Exception("No dATA");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


    }

    private void navigationDrawer() {

        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);

        menuIcon.setOnClickListener(v -> {
            if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START);
            } else
                drawerLayout.openDrawer(GravityCompat.START);
        });

        profileIcon.setOnClickListener(v -> {

            drawerLayout.closeDrawer(GravityCompat.START);
            Account(v);

        });

        animetedNavigationDrawer();
    }

    private void animetedNavigationDrawer() {


        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

                final float diffScaledOffset = slideOffset * (1 - 0.7f);
                final float offsetScale = 1 - diffScaledOffset;
                content.setScaleX(offsetScale);
                content.setScaleY(offsetScale);

                final float xOffset = drawerView.getWidth() * slideOffset;
                final float xOffsetDiff = content.getWidth() * diffScaledOffset / 2;
                final float xTranslation = xOffset - xOffsetDiff;
                content.setTranslationX(xTranslation);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
//        firebaseRecyclerAdapter.startListening();

    }

    @Override
    protected void onStop() {
        super.onStop();
//        firebaseRecyclerAdapter.stopListening();

    }


    @Override
    public void onItemClick(int position, String type, @NonNull DataSnapshot dataSnapshot) {

        Intent intent = new Intent(getApplicationContext(), AnimeDataPage.class);
        Anime anime = dataSnapshot.getValue(Anime.class);
        intent.putExtra("title", anime.getTitle());
        intent.putExtra("image", anime.getImage());
        intent.putExtra("desc", anime.getDesc());
        intent.putExtra("id", anime.getUid());
        intent.putExtra("episodes", (Parcelable) anime.getEpisodes());
        startActivity(intent);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.nav_login: {
                drawerLayout.closeDrawer(GravityCompat.START);
                Intent intent = new Intent(getApplicationContext(), Login.class);
                intent.putExtra("info", "Login");
                startActivity(intent);
                break;
            }
            case R.id.nav_sign: {
                drawerLayout.closeDrawer(GravityCompat.START);
                Intent intent = new Intent(getApplicationContext(), SignUp.class);
                intent.putExtra("info", "Register");
                startActivity(intent);
                break;
            }


            case R.id.about: {
                drawerLayout.closeDrawer(GravityCompat.START);
                Intent intent = new Intent(getApplicationContext(), About.class);
                intent.putExtra("info", "About");
                startActivity(intent);
                break;
            }
            case R.id.home: {
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            }
        }

        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else
            super.onBackPressed();
    }

    public void Account(View v) {
        Intent intent = new Intent(getApplicationContext(), Login.class);
        intent.putExtra("info", "profile");
        startActivity(intent);
    }

}