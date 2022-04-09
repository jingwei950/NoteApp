package com.example.noteapp;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.contrib.DrawerActions;
import androidx.test.espresso.contrib.NavigationViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import junit.framework.TestCase;

import org.junit.Rule;
import org.junit.Test;

public class MainActivityTest extends TestCase {

    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule = new ActivityScenarioRule<>(MainActivity.class);
    public ActivityScenario activityScenario;

    public void setUp() throws Exception {
        super.setUp();
        activityScenario = ActivityScenario.launch(MainActivity.class);
        Intents.init();
    }

    //Bottom Navigation Bar Tests
    @Test
    public void testBottomNavBarHome(){
        //Click
        onView(withId(R.id.HomeButton)).perform(click());
        //Check
        intended(hasComponent(MainActivity.class.getName()));
    }

    @Test
    public void testBottomNavBarAddNote(){
        //Click
        onView(withId(R.id.AddNoteButton)).perform(click());
        //Check
        intended(hasComponent(AddNote.class.getName()));
    }

    @Test
    public void testBottomNavBarProfile(){
        //Click
        onView(withId(R.id.ProfileButton)).perform(click());
        //Check
        intended(hasComponent(ProfileActivity.class.getName()));
    }

    @Test
    public void testDrawerProfile(){
        //Click drawer and click profile
        onView(withId(R.id.drawer)).perform(DrawerActions.open());
        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.profile));
        //Check
        intended(hasComponent(ProfileActivity.class.getName()));
    }

    @Test
    public void testDrawerAddNote(){
        //Click drawer and click add note
        onView(withId(R.id.drawer)).perform(DrawerActions.open());
        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.addNote));
        //Check
        intended(hasComponent(AddNote.class.getName()));
    }

    public void tearDown() throws Exception {
        Intents.release();
    }
}