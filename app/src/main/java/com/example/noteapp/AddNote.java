package com.example.noteapp;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

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

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.noteapp.databinding.ActivityAddNoteBinding;

public class AddNote extends AppCompatActivity {

    EditText addNoteTitle;
    EditText addNoteContent;
    Toolbar toolbar;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        //Assign the toolbar to variable
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Display back arrow button at top left

        //Textview for title and content
        addNoteTitle = findViewById(R.id.addNoteTitle);
        addNoteContent = findViewById(R.id.addNoteContent);

        //Floating action button for saving the notes
        FloatingActionButton saveFab = findViewById(R.id.saveFab);

        //Make the save fab button to white as the design tint unable to make it white
        DrawableCompat.setTint(saveFab.getDrawable(), ContextCompat.getColor(getBaseContext(), R.color.white));

        saveFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Get the title and content of edit text
                String title = addNoteTitle.getText().toString();
                String content = addNoteContent.getText().toString();

                if(title.isEmpty() && content.isEmpty()){ //When both title and content is empty, note will not be save
                    Toast.makeText(AddNote.this, "Note not save, field is empty", Toast.LENGTH_SHORT).show();
                }
                else if(title.isEmpty()){ //When only title is empty assign "<Untitled>" to title and save both title and content

                    //If title is empty assign "<Untitled>" to variable title
                    title = getString(android.R.string.untitled);

                    /*TODO add database Insert function here*/
                    //db.insert(title, content);

                    //Save the notes
                    Toast.makeText(AddNote.this, "Save button clicked, title: " + title + " content: " + content + " have been saved.", Toast.LENGTH_SHORT).show();

                    //Go back to the main page after saving the notes
                    onBackPressed();
                }
                else { //When both title and content is not empty, save both



                    /*TODO add database Insert function here*/
                    //db.insert(title, content);

                    //Save the notes
                    Toast.makeText(AddNote.this, "Save button clicked, title: " + title + " content: " + content + " have been saved.", Toast.LENGTH_SHORT).show();

                    //Go back to the main page after saving the notes
                    onBackPressed();
                }
            }
        });
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
        //Check the id of item clicked is the id of back arrow button on action bar
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
}