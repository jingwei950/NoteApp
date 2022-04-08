package com.example.noteapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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


    }

    //Defines login button
   public void logInClick(View view){
        //Get username and password
       String username = userTextName.getText().toString();
       String password = userTextPass.getText().toString();
       String email = userTextEmail.getText().toString();
       user = new Users(email, username, password);
       if(username.equals("") || password.equals("")){
           Toast.makeText(this, "Fill in empty space", Toast.LENGTH_SHORT).show();
       }
       else{
           Boolean checkuserpass = myDB.checkUsernamePasswordEmail(user);
           if(checkuserpass==true){
               user = myDB.getUser(userTextEmail.getText().toString());
               prefManager.set(SharedPrefManager.USER_ID, user.getUserID());
               backToProfile();
           }
           else{
               Toast.makeText(this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
           }
       }
   }

    //Go to ProfileActivity
    private void backToProfile() {
        Intent profileActivity = new Intent(this, ProfileActivity.class);
        startActivity(profileActivity);
    }


}