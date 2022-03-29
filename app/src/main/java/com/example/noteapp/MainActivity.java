package com.example.noteapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.noteapp.model.Adapter;
import com.example.noteapp.model.SharedPrefManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    DrawerLayout drawerLayout;          //DrawerLayout for navigation view
    Toolbar toolbar;
    ActionBarDrawerToggle drawerToggle; //The hamburger toggle button
    NavigationView navView;             //The navigation view that comes out when click on the toggle button
    RecyclerView noteLists;             //The view for dynamic contents on the main page, for different notes
    Adapter adapter;                    //The adapter to handle the RecyclerView, so that it can display multiple views(notes) on screen

    List<String> titles = new ArrayList<>();    //For storing all the note titles
    List<String> contents = new ArrayList<>();  //For storing all the note contents
    List<Note> notes = new ArrayList<Note>();   //For storing all the notes (titles and contents)

    SharedPrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        //Using toolbar as actionbar
        setSupportActionBar(toolbar); //This helps to set the menu options to toolbar

        //Layout for list of notes
        noteLists = findViewById(R.id.recyclerView);

        //Drawer layout in activity_main for Navigation menu at the side
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);

        //Navigation from the side
        navView = (NavigationView) findViewById(R.id.nav_view); //This contains both nav_header and nav_menu in it
        navView.setNavigationItemSelectedListener(this); //Set a listener that will be notified when a menu item is selected

        //Hamburger button
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close); //Specify the activity, layout, toolbar, string open and string close
        drawerLayout.addDrawerListener(drawerToggle); //Add hamburger button to the layout listener
        drawerToggle.setDrawerIndicatorEnabled(true); //Make sure the hamburger button is shown
        drawerToggle.syncState(); //Inform ActionBarDrawerToggle if it is open or close currently

        /*TODO 1 (Display notes) - Get the query selectall and process the data to display notes in main page*/
//      processDBData(dm.selectall());

        titles.add("1st note title.");
        contents.add("1st note content");

        titles.add("2nd note title.");
        contents.add("2nd note content. 2nd note content. 2nd note content. 2nd note content. 2nd note content. ");

        titles.add("3rd note title.");
        contents.add("3rd note content");

        /*TODO 2 (Display notes) - Loop thru notes*/
        //Loop thru notes ArrayList and add each titles and notes into their respective ArrayList
//        for(int i = 0; i < notes.size(); i++){
//            titles.add(notes.get(i).getTitle());
//            contents.add(notes.get(i).getContent());
//        }

        //Passing the List of titles and contents into adapter
        adapter = new Adapter(titles, contents);
        //Set the layout of each note to show staggered, 2 rows vertically
        noteLists.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        //Set the adapter of RecyclerView
        noteLists.setAdapter(adapter);

        //Floating action button for add new note
        FloatingActionButton addNoteFab = findViewById(R.id.addNoteFab);
        addNoteFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Go to add note activity
                startActivity(new Intent(view.getContext(), AddNote.class));
            }
        });

        getPref();
    }

    /* TODO 3 (Display notes) - Get all the titles and contents from database*/
    //Process all titles and contents from database
    public void processDBData(Cursor c){

        String noteTitle;
        String noteContent;

        //Loop thru and get all the titles and contents
        while (c.moveToNext())
        {
            Log.i("displayRecords", c.getString(1)+ " " +c.getString(2));

            noteTitle = c.getString(1);
            noteContent = c.getString(2);
            notes.add(new Note(noteTitle, noteContent)); //Add all titles and contents in respective ArrayList

            //OR
//            titles.add(noteTitle);
//            contents.add(noteContent);
        }
    }

    //Create options menu that contains 3 dot menu and search bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();

        //Show 3 dots menu (options menu)
        inflater.inflate(R.menu.options_menu, menu);
        //Show Search bar
        inflater.inflate(R.menu.search_bar, menu);

        return true;
    }



    //Hand 3 dots menu item click
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        //Check which item is clicked
        switch(item.getItemId()){

            case R.id.newFolder:
                Toast.makeText(this, "You have clicked on new folder item", Toast.LENGTH_SHORT).show();
                break;

            case R.id.home:
                Toast.makeText(this, "You have clicked on home item", Toast.LENGTH_SHORT).show();
                break;

            case R.id.select:
                Toast.makeText(this, "You have clicked on select item", Toast.LENGTH_SHORT).show();

                //For testing add "new note"
//                addItem(titles, contents);
                break;

            default:
                Toast.makeText(this, "You have clicked on some button", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    //Function for testing adding new note
//    private void addItem(List<String> titles, List<String> contents){
//        //Place outside of function for count++ to work
//        int count = 4;
//
//        String countNotes = Integer.toString(count);
//        titles.add("Note title " + countNotes);
//        contents.add("Note content " + countNotes);
//
//        //Update the adapter so data will update on screen
//        adapter.notifyDataSetChanged();
//
//        count++;
//    }

    //Handle navigation item click
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        //Check which item is clicked
        switch(item.getItemId()){

            case R.id.notes:
                Toast.makeText(this, "You have clicked on Notes", Toast.LENGTH_SHORT).show();
                break;

            case R.id.addNote:
                //Go to AddNote activity
                startActivity(new Intent(this, AddNote.class));
                break;

            case R.id.profile:
                //Go to Profile activity
                startActivity(new Intent(this, ProfileActivity.class));
                break;

            case R.id.rating:
                Toast.makeText(this, "You have clicked on rate", Toast.LENGTH_SHORT).show();
                break;

            case R.id.shareapp:
                Toast.makeText(this, "You have clicked on share", Toast.LENGTH_SHORT).show();
                break;

            default:
                Toast.makeText(this, "You have clicked on some button", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    public void getPref(){
        prefManager = new SharedPrefManager(getApplicationContext());
        String isNightMode = prefManager.get(SharedPrefManager.NIGHT_MODE, SharedPrefManager.NIGHT_MODE_DEFAULT);
        prefManager.setDayNightMode(isNightMode);
    }
}