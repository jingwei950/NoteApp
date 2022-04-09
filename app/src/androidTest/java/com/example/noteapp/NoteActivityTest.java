package com.example.noteapp;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.matcher.RootMatchers.isPlatformPopup;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

import android.view.View;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.example.noteapp.model.SharedPrefManager;

import junit.framework.TestCase;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;

public class NoteActivityTest extends TestCase {

    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule = new ActivityScenarioRule(MainActivity.class);
    public ActivityScenario activityScenario;

    SharedPrefManager prefManager;
    NoteDatabase myDB;
    public void setUp() throws Exception {
        super.setUp();
        activityScenario = ActivityScenario.launch(MainActivity.class);
        prefManager = new SharedPrefManager(getInstrumentation().getTargetContext());
        myDB = new NoteDatabase(getInstrumentation().getTargetContext());
    }

    @Test
    public void testUpdateNote(){
        String newTitle = "Edit Title Test";
        String newContent = "Edit Content Test";

        //Replace and click
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.noteEditTitle)).perform(replaceText(newTitle));
        onView(withId(R.id.noteEditContent)).perform(replaceText(newContent));
        closeSoftKeyboard();
        onView(withId(R.id.updateNoteBtn)).perform(click());

        //Get note from database
        long userID = prefManager.get(SharedPrefManager.USER_ID, SharedPrefManager.USER_ID_DEFAULT);
        Note note = myDB.getNoteTest(newTitle, newContent, userID);

        //Check against database
        assertEquals(newTitle, note.getTitle());
        assertEquals(newContent, note.getContent());
        assertEquals(userID, note.getUserID());
    }

    public void tearDown() throws Exception {
    }
}