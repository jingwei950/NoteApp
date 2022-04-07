package com.example.noteapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
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
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.noteapp.model.Adapter;
import com.example.noteapp.model.SharedPrefManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
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
    NoteDatabase myDB;                  //Note database
    ArrayList<String> titles, contents; //For storing all the note titles
    SharedPrefManager prefManager;
    ArrayList<Note> allNotes;           //Note arraylist for storing all notes
    MenuItem searchItem;                //MenuItem search bar
    SearchView searchView;              //SearchView
    BottomNavigationView bottomNavigationView; //BottomNavigationView


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        //Using toolbar as actionbar
        setSupportActionBar(toolbar); //This helps to set the menu options to toolbar

        //Bottom navigation bar
        bottomNavigationView = findViewById(R.id.mainBottomNavigationView);

        //Set the default highlighted navigation item
        bottomNavigationView.getMenu().performIdentifierAction(R.id.HomeButton, 0);
        bottomNavigationView.getMenu().getItem(1).setChecked(false);    //Set the check for "Add Note" button to false, to disable highlight on button
        bottomNavigationView.getMenu().getItem(2).setChecked(false);    //Set the check for "Profile" button to false, to disable highlight on button
        bottomNavigationView.getMenu().getItem(0).setChecked(true);     //Set the check for "Home" button to true, to enable highlight on button

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

        //Initialize NoteDatabase and Arraylist
        myDB = new NoteDatabase(MainActivity.this);

        //Select all data in database and store it in List
        allNotes = myDB.getAllNotes();

        //Passing the ArrayList of notes into adapter
        adapter = new Adapter(allNotes);
        //Set the layout of each note to show staggered, 2 rows vertically
        noteLists.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        //Set the adapter of RecyclerView
        noteLists.setAdapter(adapter);

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

    //Create options menu that contains 3 dot menu and search bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu, this adds items to the action bar if it is present
        MenuInflater inflater = getMenuInflater();

        //Show 3 dots menu (options menu)
        inflater.inflate(R.menu.options_menu, menu);
        //Show Search bar
        inflater.inflate(R.menu.search_bar, menu);
        //Show bottom navigation bar
        inflater.inflate(R.menu.navigation_bar, menu);

        //MenuItem of search bar
        searchItem = menu.findItem(R.id.search_bar);
        //Search View
        searchView = (SearchView) searchItem.getActionView();

        //Set the query text listener when user type in the search bar
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) { //Check the text change filter
                //Pass the text of user input into the adapter to trigger performFiltering callback function
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }



    //Handle 3 dots menu item click
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
                break;

            default:
                Toast.makeText(this, "You have clicked on some button", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    //Handle navigation item click
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        //Check which item is clicked
        switch(item.getItemId()){

            case R.id.notes:
                Toast.makeText(this, "You have clicked on Notes", Toast.LENGTH_SHORT).show();

                //Close the navigation drawer layout and go back to main page
                drawerLayout.close();
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
