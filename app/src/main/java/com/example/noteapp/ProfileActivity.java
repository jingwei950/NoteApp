package com.example.noteapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.res.ResourcesCompat;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.DecimalFormat;

public class ProfileActivity extends AppCompatActivity {
    Toolbar toolbar;
    BottomNavigationView bottomNavigationView; //BottomNavigationView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //Bottom navigation bar
        bottomNavigationView = findViewById(R.id.profileBottomNavigationView);

        //Set the default highlighted navigation item
        bottomNavigationView.getMenu().performIdentifierAction(R.id.ProfileButton, 0);
        bottomNavigationView.getMenu().getItem(0).setChecked(false);    //Set the check for "Home" button to false, to disable highlight on button
        bottomNavigationView.getMenu().getItem(1).setChecked(false);    //Set the check for "AddNote" button to false, to disable highlight on button
        bottomNavigationView.getMenu().getItem(2).setChecked(true);     //Set the check for "Profile" button to true , to enable highlight on button

        //Assign the toolbar to variable
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Display back arrow button at top left

        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .replace(R.id.profileFragmentContainer, ProfileFragment.class, null)
                .commit();

        fm.beginTransaction()
                .replace(R.id.typographyFragmentContainer, TypographyFragment.class, null)
                .commit();

        fm.beginTransaction()
                .replace(R.id.themeFragmentContainer, ThemeFragment.class, null)
                .commit();

        //Handle Bottom navigation button when button is clicked
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.HomeButton:
                    //Go to MainActivity when "Home" button clicked
                    startActivity(new Intent(this, MainActivity.class));
                    break;
                case R.id.AddNoteButton:
                    //Go to AddNote Activity when "Add Note" button clicked
                    startActivity(new Intent(this, AddNote.class));
                    break;
                case R.id.ProfileButton:
                    //Go to ProfileActivity when "Profile" button clicked
                    startActivity(new Intent(this, ProfileActivity.class));
                    break;
            }
            return false;
        });
    }

    //Top back arrow
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //Check the id of item clicked is the id of back arrow button on action bar
        if (item.getItemId() == android.R.id.home) { //Action bar back button id (android.R.id.home)
            //In-built android back function
            onBackPressed(); //Make the action bar back button to go back to previous activity
        }

        return super.onOptionsItemSelected(item);
    }
}

