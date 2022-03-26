package com.example.noteapp;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
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

        //Assign text views to variables
        TextView toolbarTitle = findViewById(R.id.noteActivityTitle);
        EditText title = findViewById(R.id.noteEditTitle);
        EditText content = findViewById(R.id.noteEditContent);

        //Allow the scrolling when the content have too many words
        content.setMovementMethod(new ScrollingMovementMethod());

        //Set the text that is passed from the adapter
        toolbarTitle.setText(data.getStringExtra("title"));
        title.setText(data.getStringExtra("title"));    //Title passed from adapter
        content.setText(data.getStringExtra("content"));//Content passed from adapter

        //Set the content background color as the same in the main activity (Generated and passed from adapter)
        content.setBackgroundColor(getResources().getColor(data.getIntExtra("color", 0))); //Color passed from adapter

        //Floating action button for saving the notes
        FloatingActionButton saveNoteFab = findViewById(R.id.saveNoteFab);

        //Make the save fab button to white as the design tint unable to make it white
        DrawableCompat.setTint(saveNoteFab.getDrawable(), ContextCompat.getColor(getBaseContext(), R.color.white));

        saveNoteFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*TODO add database Insert function here*/
                //db.update(title, content);
            }
        });
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
}