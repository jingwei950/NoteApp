package com.example.noteapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_layout);
    }

    public void signUpClick(View view){
        TextView email = findViewById(R.id.editSignUpEmail);
        TextView username = findViewById(R.id.editSignUpUsername);
        TextView password = findViewById(R.id.editSignUpPassword);
        TextView rePassword = findViewById(R.id.editSignUpRePassword);

        Log.i("test", email.getText().toString());
        Log.i("test", username.getText().toString());
        Log.i("test", password.getText().toString());
        Log.i("test", rePassword.getText().toString());
    }
}
