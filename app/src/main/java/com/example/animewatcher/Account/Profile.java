package com.example.animewatcher.Account;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.animewatcher.Helper.SessionManager;
import com.example.animewatcher.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class Profile extends AppCompatActivity {

    TextView profileUsername, profileName;
    Button logout_btn;

    private int REQUEST_STORAGE = 111;
    private int REQUEST_FILE = 222;




    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profileUsername = findViewById(R.id.profile_username);
        profileName = findViewById(R.id.profile_name);
        logout_btn = findViewById(R.id.logout_button);

        username = getIntent().getStringExtra("username");

        profileUsername.setText(username);
        profileName.setText(getIntent().getStringExtra("full_name"));


        logout_btn.setOnClickListener(this::Logout);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    public void Logout(View v) {
        SessionManager sessionManager = new SessionManager(getApplicationContext(), SessionManager.SESSION_REMEMBERME);
        sessionManager.logoutUserFromSession();
        Intent intent = new Intent(getApplicationContext(), Login.class);
        startActivity(intent);
        finish();
    }
}
