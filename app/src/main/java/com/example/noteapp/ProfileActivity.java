package com.example.noteapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_layout);
    }

    public void typefaceClick(View view){
        TextView typeface = findViewById(R.id.textCurrentTypeface);
        if(typeface.getText().equals("Monospace")){
            typeface.setText("Pier");
        }else{
            typeface.setText("Monospace");
        }
    }

    public void fontSizeClick(View view){
        TextView fontSize = findViewById(R.id.textCurrentFontSize);
        if(fontSize.getText().equals("10")){
            fontSize.setText("20");
        }else{
            fontSize.setText("10");
        }
    }

    public void lineHeightClick(View view){
        TextView lineHeight = findViewById(R.id.textCurrentLineHeight);
        if(lineHeight.getText().equals("10")){
            lineHeight.setText("30");
        }else{
            lineHeight.setText("10");
        }
    }

    public void lineWidthClick(View view){
        TextView lineWidth = findViewById(R.id.textCurrentLineWidth);
        if(lineWidth.getText().equals("10")){
            lineWidth.setText("40");
        }else{
            lineWidth.setText("10");
        }
    }

    public void loginClick(View view){
        Button buttonLogin = findViewById(R.id.buttonLogin);
        TextView textName = findViewById((R.id.textName));
        TextView textEmail = findViewById(R.id.textEmail);

        if(buttonLogin.getText().equals("Login")){
            buttonLogin.setText("Logout");
            textName.setText("Max");
            textEmail.setText("max@gamil.com");
        }else{
            buttonLogin.setText("Login");
            textName.setText("Name");
            textEmail.setText("Email");
        }
    }
}
