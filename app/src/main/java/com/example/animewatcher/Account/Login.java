package com.example.animewatcher.Account;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.animewatcher.Helper.SessionManager;
import com.example.animewatcher.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.internal.zzv;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class Login extends AppCompatActivity {
    Button login_btn;
    TextInputLayout username, password;
    EditText usernameEditText, passwordEditText;
    CheckBox rememberMe;
    String accInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);


        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login_btn = findViewById(R.id.signin);
        usernameEditText = findViewById(R.id.username_login);
        passwordEditText = findViewById(R.id.password_login);


        SessionManager sessionManager = new SessionManager(getApplicationContext(), SessionManager.SESSION_REMEMBERME);
        if (sessionManager.checkRememberMe()) {
            HashMap<String, String> rememberMeDetails = sessionManager.getRememberDetailFromSession();
            usernameEditText.setText(rememberMeDetails.get(SessionManager.KEY_SESSIONUSERNAME));
            passwordEditText.setText(rememberMeDetails.get(SessionManager.KEY_SESSIONPASSWORD));
            if (accInfo.equals("profile")) {
                isIUser();
                finish();
            }
        }
        login_btn.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), Profile.class);

            Pair[] pair = new Pair[2];

            pair[0] = new Pair<View, String>(username, "username_tran");
            pair[1] = new Pair<View, String>(password, "password_tran");

            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Login.this, pair);
            startActivity(intent, options.toBundle());
        });
    }


    private void isIUser() {
        String userEnteredUsername = username.getEditText().getText().toString();
        String userEnteredPassword = password.getEditText().getText().toString();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("users");

        if (rememberMe.isChecked()) {
            SessionManager sessionManager = new SessionManager(getApplicationContext(), SessionManager.SESSION_REMEMBERME);
            sessionManager.createRememberMeSession(userEnteredUsername, userEnteredPassword);
        }

        Query checkUser = reference.orderByChild("username").equalTo(userEnteredUsername);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {


                    String passwordFromDB = dataSnapshot.child(userEnteredUsername).child("password").getValue(String.class);

                    if (passwordFromDB.equals(userEnteredPassword)) {

                        String nameFromDB = dataSnapshot.child(userEnteredUsername).child("full_name").getValue(String.class);
                        String usernameFromDB = dataSnapshot.child(userEnteredUsername).child("username").getValue(String.class);
                        String emailFromDB = dataSnapshot.child(userEnteredUsername).child("email").getValue(String.class);

                        SessionManager sessionManager = new SessionManager(getApplicationContext(), "SESSION_USERSESSION");
                        sessionManager.createLoginSession(nameFromDB, usernameFromDB, emailFromDB, passwordFromDB);
                        Intent intent = new Intent(getApplicationContext(), Profile.class);

                        intent.putExtra("username", usernameFromDB);
                        intent.putExtra("full_name", nameFromDB);
                        intent.putExtra("email",emailFromDB);

                        startActivity(intent);

                        finish();
                    } else {
                        password.setError("Wrong Password");
                        password.requestFocus();
                    }
                } else {
                    username.setError("No Such User exist");
                    username.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
