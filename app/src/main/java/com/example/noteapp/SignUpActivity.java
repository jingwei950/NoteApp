package com.example.noteapp;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionProvider;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import java.util.List;

public class SignUpActivity extends AppCompatActivity {

    SharedPreferences pref;
    SharedPreferences.Editor prefEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_layout);
        pref = getSharedPreferences("day_night", MODE_PRIVATE);
        prefEditor = pref.edit();

        getPref();
    }

    public void getPref(){
        boolean isNightMode = pref.getBoolean("night_mode", false);
        setDayNightMode(isNightMode);
    }

    public void setDayNightMode(boolean isNightMode){
        if(isNightMode){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            prefEditor.putBoolean("night_mode", true);
            prefEditor.apply();
        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            prefEditor.putBoolean("night_mode", false);
            prefEditor.apply();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void signUpClick(View view){
        TextView email = findViewById(R.id.editSignUpEmail);
        TextView username = findViewById(R.id.editSignUpUsername);
        TextView password = findViewById(R.id.editSignUpPassword);
        TextView rePassword = findViewById(R.id.editSignUpRePassword);

        //validate
        Validation valid = validateFields(email.getText().toString(), username.getText().toString(), password.getText().toString(), rePassword.getText().toString());
        if(!valid.getValid()){
            String errorMessage = String.join("\n", valid.getErrorMessage());
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
        }
        //check exists in database



        //add to database
    }

    public Validation validateFields(String email, String username, String password, String rePassword){
        Validation valid = new Validation();
        valid.setValid(true);

        if(!email.contains("@")){
            valid.setValid(false);
            valid.addErrorMessage("Invalid Email");
        }

        if(email.isEmpty() || username.isEmpty() || password.isEmpty() || rePassword.isEmpty()){
            valid.setValid(false);
            valid.addErrorMessage("Empty Fields");
        }
        if(!password.equals(rePassword)){

            valid.setValid(false);
            valid.addErrorMessage("Password are not the same");
        }
        return valid;
    }

    public boolean checkExistsInDatabase(){
        boolean exists = false;

        return exists;
    }
}
