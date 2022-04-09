package com.example.noteapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.noteapp.model.SharedPrefManager;

public class LoginActivity extends AppCompatActivity {

    SharedPrefManager prefManager;
    NoteDatabase myDB;
    EditText userTextName;
    EditText userTextPass;
    EditText userTextEmail;
    Button login;
    Users user;
    TextView errorMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        //New SharedPref and Database managers
        prefManager = new SharedPrefManager(getApplicationContext());
        myDB = new NoteDatabase(getApplicationContext());
        //Initialize all views
        userTextName = findViewById(R.id.userTextName);
        userTextPass = findViewById(R.id.userTextPass);
        userTextEmail = findViewById(R.id.userTextEmail);
        login = findViewById(R.id.loginButton);
        errorMessage =findViewById(R.id.errorMessageLogin);
    }

    //Defines login button
   public void logInClick(View view){
        //Get username and password
       String username = userTextName.getText().toString();
       String password = userTextPass.getText().toString();
       String email = userTextEmail.getText().toString();
       user = new Users(email, username, password);
       if(username.equals("") || password.equals("") || email.equals("")){
           Toast.makeText(this, R.string.empty_fields, Toast.LENGTH_SHORT).show();
           errorMessage.setText(R.string.empty_fields);
       }
       else{
           boolean checkUserPass = myDB.checkUsernamePasswordEmail(user);
           if(checkUserPass==true){
               user = myDB.getUser(userTextEmail.getText().toString());
               prefManager.set(SharedPrefManager.USER_ID, user.getUserID());
               backToProfile();
           }
           else{
               Toast.makeText(this, R.string.invalid_credentials, Toast.LENGTH_SHORT).show();
               errorMessage.setText(R.string.invalid_credentials);
           }
       }
   }

    //Go to ProfileActivity
    private void backToProfile() {
        Intent profileActivity = new Intent(this, ProfileActivity.class);
        startActivity(profileActivity);
    }


}