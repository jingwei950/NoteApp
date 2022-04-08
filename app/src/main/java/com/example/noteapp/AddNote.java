package com.example.noteapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;

import com.example.noteapp.model.SharedPrefManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noteapp.model.Adapter;

import java.util.ArrayList;

public class AddNote extends AppCompatActivity {
    EditText addNoteTitle, addNoteContent;

    Toolbar toolbar;
    FloatingActionButton saveFab;
    ProgressBar progressBar;
    NoteDatabase myDB;

    SharedPrefManager prefManager;
    NoteDatabase noteDB;
    BottomNavigationView bottomNavigationView; //BottomNavigationView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        //Database
        noteDB = new NoteDatabase(AddNote.this);

        //Assign the toolbar to variable
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Display back arrow button at top left

        //Textview for title and content
        addNoteTitle = findViewById(R.id.addNoteTitle);
        addNoteContent = findViewById(R.id.addNoteContent);

        //Bottom navigation bar
        bottomNavigationView = findViewById(R.id.addNoteBottomNavigationView);

        //Set the default highlighted navigation item
        bottomNavigationView.getMenu().performIdentifierAction(R.id.AddNoteButton, 0);
        bottomNavigationView.getMenu().getItem(0).setChecked(false);   //Set the check for "Home" button to false, to disable highlight on button
        bottomNavigationView.getMenu().getItem(2).setChecked(false);   //Set the check for "Profile" button to false, to disable highlight on button
        bottomNavigationView.getMenu().getItem(1).setChecked(true);    //Set the check for "AddNote" button to true, to enable highlight on button

        //Floating action button for saving the notes
        saveFab = findViewById(R.id.saveFab);

        //Make the save fab button to white as the design tint unable to make it white
        DrawableCompat.setTint(saveFab.getDrawable(), ContextCompat.getColor(getBaseContext(), R.color.white));

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

        getPref();
    }

    //addNoteBtn Function - Adds data into Table
    public void btnInsertPressed(View v){
        //Get the title and content of edit text
        String title = addNoteTitle.getText().toString();
        String content = addNoteContent.getText().toString();

        //Use the title and content get from edit text for the new note
        Note note = new Note(title, content);

        //Check if both title and content is empty, if it is note will not be save
        if(title.isEmpty() && content.isEmpty()){
            //Make a toast and let user know
            Toast.makeText(AddNote.this, "Please enter something in the fields to save", Toast.LENGTH_SHORT).show();
        }
        else if(title.isEmpty()){ //When only title is empty assign "<Untitled>" to title and save both title and content

            //If title is empty assign "<Untitled>" to variable title
            addNoteTitle.setText(getString(android.R.string.untitled));

            //Insert the newly added note into database
            myDB = new NoteDatabase(AddNote.this);
            myDB.addNote(note);

            //Make a toast to inform user note saved
            Toast.makeText(AddNote.this, "Save button clicked, title: " + title + " content: " + content + " have been saved.", Toast.LENGTH_SHORT).show();

            //Go back to the main page after saving the notes
            backToMain();
        }
        else { //When both title and content is not empty, save both

            //Insert the newly added note into database
            myDB = new NoteDatabase(AddNote.this);
            myDB.addNote(note);

            //Make a toast to inform user note saved
            Toast.makeText(AddNote.this, "Save button clicked, title: " + title + " content: " + content + " have been saved.", Toast.LENGTH_SHORT).show();

            //Go back to the main page after saving the notes
            backToMain();
        }

    }

    //Function for going back to MainActivity
    private void backToMain(){
        Intent mainActivity = new Intent(this, MainActivity.class);
        startActivity(mainActivity);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //Inflate the menu with option of close button on top right
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.close_addnote, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //Check the id of item clicked is the id of back arrow button or close button on action bar
        if(item.getItemId() == android.R.id.home){ //Action bar back button id (android.R.id.home)
            //In-built android back function
            onBackPressed(); //Make the action bar back button to go back to previous activity
            Toast.makeText(AddNote.this, "Back button pressed, note not save", Toast.LENGTH_SHORT).show();
        }
        else if(item.getItemId() == R.id.close_btn){ //Action bar close button
            onBackPressed();
            Toast.makeText(AddNote.this, "Close button pressed, note not save", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }

    public void getPref(){
        prefManager = new SharedPrefManager(getApplicationContext());
        //Typeface
        String typeface = prefManager.get(SharedPrefManager.TYPEFACE, SharedPrefManager.TYPEFACE_DEFAULT);
        prefManager.setTypeface(addNoteContent, typeface);

        //Font Size
        String fontSize = prefManager.get(SharedPrefManager.FONT_SIZE, SharedPrefManager.FONT_SIZE_DEFAULT);
        prefManager.setFontSize(addNoteContent, fontSize);

        //Line Height
        String lineHeight = prefManager.get(SharedPrefManager.LINE_HEIGHT, SharedPrefManager.LINE_HEIGHT_DEFAULT);
        prefManager.setLineHeight(addNoteContent, lineHeight);

        //Letter Spacing
        String letterSpacing = prefManager.get(SharedPrefManager.LETTER_SPACING, SharedPrefManager.LETTER_SPACING_DEFAULT);
        prefManager.setLetterSpacing(addNoteContent, letterSpacing);
    }
}