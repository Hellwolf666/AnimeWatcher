package com.example.animewatcher.Account;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.animewatcher.Model.UserHelperClass;
import com.example.animewatcher.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.auth.FirebaseAppCheckTokenProvider;
import com.hbb20.CountryCodePicker;

public class SignUp extends AppCompatActivity {
    TextInputLayout regName, regUsername, regEmail, regPassword;


    DatabaseReference reference;


    Button reg_button;
    public SignUp() {
        this.reference = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        regName = findViewById(R.id.name);
        regUsername = findViewById(R.id.Username);
        regEmail = findViewById(R.id.email);
        regPassword = findViewById(R.id.password);
        reg_button = findViewById(R.id.register_button);

        reg_button.setOnClickListener(v-> {
            if(validationEmail() &&validationName() &&validationUsername()) {

                registerUser(v);
            }
        });
    }

    public boolean validationName() {
        String val = regName.getEditText().getText().toString();

        if (val.isEmpty()) {
            regName.setError("Feild cannot be Empty");
            return false;
        }
        else {
            regName.setError(null);
            regName.setEnabled(false);
            return true;
        }
    }

    public boolean validationUsername() {
        String val = regUsername.getEditText().getText().toString();
        String noWhiteSpace = "(?=\\s+$)";

        if (val.isEmpty()) {
            regUsername.setError("Field cannot be Empty");
            return false;
        }
        else if (val.length() > 15){
            regUsername.setError("Username is too long");
            return false;
        }
        else if (val.matches(noWhiteSpace)){
            regUsername.setError("White Spaces are not Allowed");
            return false;
        }
        else {
            regUsername.setError(null);
            regUsername.setErrorEnabled(false);
            return true;
        }
    }

    public boolean validationEmail() {
        String val = regEmail.getEditText().getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (val.isEmpty()) {
            regEmail.setError("Field cannot be Empty");
            return false;
        }
        else if (!val.matches(emailPattern)){
            regEmail.setError("Invalid email address");
            return false;
        }
        else {
            regEmail.setError(null);
            return true;
        }
    }



    public void registerUser(View v) {

        if (!validationName() | !validationUsername() | !validationEmail()){
            return;
        }

        String name = regName.getEditText().getText().toString();
        String username = regUsername.getEditText().getText().toString();
        String email = regEmail.getEditText().getText().toString();
        String password = regPassword.getEditText().getText().toString();
        String id = regPassword.getEditText().getText().toString();
        String image = "drawable/profile.pgn";

        Intent intent = new Intent(getApplicationContext(), Profile.class);
        intent.putExtra("name", name);
        intent.putExtra("username", username);
        intent.putExtra("email", email);
        intent.putExtra("password", password);
        intent.putExtra("image",image);

        startActivity(intent);

        UserHelperClass helperClass = new UserHelperClass(name, username, email, password);
        reference.child("users").child(id).setValue(helperClass);
    }
}
