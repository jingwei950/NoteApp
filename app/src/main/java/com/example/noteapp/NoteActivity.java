package com.example.noteapp;

import android.content.Intent;
import android.os.Bundle;

import com.example.noteapp.model.SharedPrefManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
//import android.widget.Toolbar;
import androidx.appcompat.widget.Toolbar;

import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import org.w3c.dom.Text;

public class NoteActivity extends AppCompatActivity {

    private EditText title;                   //Edit text title
    private EditText content;                 //Edit text content
    private View view;                        //View for this activity
    private FloatingActionButton saveNoteFab; //Floating action button for saving note

    SharedPrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        //Assign the toolbar to variable
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Show back arrow on the top left of action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Get the data passed adapter
        Intent data = getIntent();

        //Assign views to variables
        title = findViewById(R.id.noteEditTitle);
        content = findViewById(R.id.noteEditContent);
        view = findViewById(R.id.note);

        //Allow the scrolling when the content have too many words
        content.setMovementMethod(new ScrollingMovementMethod());

        //Set the text that is passed from the adapter
        title.setText(data.getStringExtra("title"));    //Set the title passed from adapter
        content.setText(data.getStringExtra("content"));//Set the content passed from adapter

        //Set the content background color as the same in the main activity (Generated and passed from adapter)
        view.setBackgroundColor(getResources().getColor(data.getIntExtra("color", 0))); //Set the view's color passed from adapter

        //Floating action button for saving the notes
        saveNoteFab = findViewById(R.id.saveNoteFab);

        //Make the save fab button to white as the design tint unable to make it white
        DrawableCompat.setTint(saveNoteFab.getDrawable(), ContextCompat.getColor(getBaseContext(), R.color.white));

        saveNoteFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*TODO add database Insert function here*/
                //db.update(title, content);
                Toast.makeText(view.getContext(), "You have clicked on save button, saved title: " + title.getText().toString() +
                        " content: " + content.getText().toString() , Toast.LENGTH_SHORT).show();

                //Go back to main page
                onBackPressed();
            }
        });

        getPref();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //Check the id of item clicked is the id of back arrow button on action bar
        if(item.getItemId() == android.R.id.home){ //Action bar back button id (android.R.id.home)
            //In-built android back function
            onBackPressed(); //Make the action bar back button to go back to previous activity
        }

        return super.onOptionsItemSelected(item);
    }

    public void getPref(){
        prefManager = new SharedPrefManager(getApplicationContext());

        //Typeface
        String typeface = prefManager.get(SharedPrefManager.TYPEFACE, SharedPrefManager.TYPEFACE_DEFAULT);
        prefManager.setTypeface(content, typeface);

        //Font Size
        String fontSize = prefManager.get(SharedPrefManager.FONT_SIZE, SharedPrefManager.FONT_SIZE_DEFAULT);
        prefManager.setFontSize(content, fontSize);

        //Line Height
        String lineHeight = prefManager.get(SharedPrefManager.LINE_HEIGHT, SharedPrefManager.LINE_HEIGHT_DEFAULT);
        prefManager.setLineHeight(content, lineHeight);

        //Letter Spacing
        String letterSpacing = prefManager.get(SharedPrefManager.LETTER_SPACING, SharedPrefManager.LETTER_SPACING_DEFAULT);
        prefManager.setLetterSpacing(content, letterSpacing);
    }
}