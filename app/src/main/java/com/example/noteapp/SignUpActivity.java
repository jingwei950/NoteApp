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

    TextView textError;
    TextView editSignUpEmail;
    TextView editSignUpUsername;
    TextView editSignUpPassword;
    TextView editSignUpRePassword;
    Button signup_button;
    NoteDatabase myDB;
    Users user;

    //Initialize
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_layout);

        //New SharedPref and Database managers
        prefManager = new SharedPrefManager(getApplicationContext());
        myDB = new NoteDatabase(getApplicationContext());

        //Initialize all views
        editSignUpEmail = findViewById(R.id.editSignUpEmail);
        editSignUpUsername = findViewById(R.id.editSignUpUsername);
        editSignUpPassword = findViewById(R.id.editSignUpPassword);
        editSignUpRePassword = findViewById(R.id.editSignUpRePassword);
        signup_button = findViewById(R.id.buttonSignUp);
        textError = findViewById(R.id.textError);
    }

    //Defines the sign up onclick event
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
            textError.setText(errorMessage);
            Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
        }else{
            //check exists
            if(myDB.checkEmailExists(user)){
                textError.setText(R.string.email_taken);
                Toast.makeText(getApplicationContext(), R.string.email_taken, Toast.LENGTH_SHORT).show();
            }else if(myDB.checkUsernameExists(user)){
                textError.setText(R.string.username_taken);
                Toast.makeText(getApplicationContext(), R.string.username_taken, Toast.LENGTH_SHORT).show();
            }else{
                myDB.addUser(user);
                user = myDB.getUser(editSignUpEmail.getText().toString());
                prefManager.set(SharedPrefManager.USER_ID, user.getUserID());
                backToProfile();
            }
        }
    }

    //Go to ProfileActivity
    private void backToProfile() {
        Intent profileActivity = new Intent(this, ProfileActivity.class);
        startActivity(profileActivity);
    }

    //Validation
    public Validation validateFields(String email, String username, String password, String rePassword) {
        Validation validation = new Validation();
        validation.setValid(true);

        //Check empty fields
        if (email.isEmpty() || username.isEmpty() || password.isEmpty() || rePassword.isEmpty()) {
            validation.setValid(false);
            validation.addErrorMessage(getString(R.string.empty_fields));
        } else {
            //Check email format
            if (!email.contains("@")) {
                validation.setValid(false);
                validation.addErrorMessage(getString(R.string.invalid_email));
            }
            //Check password same
            if (!password.equals(rePassword)) {

                validation.setValid(false);
                validation.addErrorMessage(getString(R.string.password_not_same));
            }
        }
        return validation;
    }
}
