package com.example.noteapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.res.ResourcesCompat;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
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

import java.text.DecimalFormat;

public class ProfileActivity extends AppCompatActivity {
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

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

