package com.example.noteapp;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.noteapp.model.SharedPrefManager;
import com.example.noteapp.model.Validation;

public class SignUpActivity extends AppCompatActivity {
    SharedPrefManager prefManager;

    TextView editSignUpEmail;
    TextView editSignUpUsername;
    TextView editSignUpPassword;
    TextView editSignUpRePassword;
    Button signup_button;
    NoteDatabase myDB;
    Users user;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_layout);

        prefManager = new SharedPrefManager(getApplicationContext());
        myDB = new NoteDatabase(getApplicationContext());

        editSignUpEmail = findViewById(R.id.editSignUpEmail);
        editSignUpUsername = findViewById(R.id.editSignUpUsername);
        editSignUpPassword = findViewById(R.id.editSignUpPassword);
        editSignUpRePassword = findViewById(R.id.editSignUpRePassword);
        signup_button = findViewById(R.id.buttonSignUp);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void signUpClick(View view){
        //Get the title and content of edit text
        String email = editSignUpEmail.getText().toString();
        String username = editSignUpUsername.getText().toString();
        String password = editSignUpPassword.getText().toString();
        String rePassword = editSignUpRePassword.getText().toString();
        user = new Users(email, username, password);

        Validation validation = validateFields(email, username, password, rePassword);
        //if not valid
        if(!validation.getValid()){
            String errorMessage = String.join("\n", validation.getErrorMessageList());
            Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
        }else{
            //check exists
            if(myDB.checkEmailExists(user)){
                Toast.makeText(getApplicationContext(), "Email Already Exists", Toast.LENGTH_SHORT).show();
            }else if(myDB.checkUsernameExists(user)){
                Toast.makeText(getApplicationContext(), "Username Already Exists", Toast.LENGTH_SHORT).show();
            }else{
                myDB.addUser(user);
                backToProfile();
            }
        }
    }

    private void backToProfile() {
        user = myDB.getUser(editSignUpEmail.getText().toString());
        prefManager.set(SharedPrefManager.USER_ID, user.getUserID());

        Intent profileActivity = new Intent(this, ProfileActivity.class);
        startActivity(profileActivity);
    }

    //Validation
    public Validation validateFields(String email, String username, String password, String rePassword) {
        Validation validation = new Validation();
        validation.setValid(true);

        if (email.isEmpty() || username.isEmpty() || password.isEmpty() || rePassword.isEmpty()) {
            validation.setValid(false);
            validation.addErrorMessage("Empty Fields");
        } else {
            if (!email.contains("@")) {
                validation.setValid(false);
                validation.addErrorMessage("Invalid Email");
            }
            if (!password.equals(rePassword)) {

                validation.setValid(false);
                validation.addErrorMessage("Password are not the same");
            }
        }
        return validation;
    }
}
