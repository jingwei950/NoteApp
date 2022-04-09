package com.example.noteapp;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.example.noteapp.model.SharedPrefManager;

import junit.framework.TestCase;

import org.junit.Rule;
import org.junit.Test;

public class AddNoteTest extends TestCase {

    @Rule
    public ActivityScenarioRule<AddNote> activityScenarioRule = new ActivityScenarioRule(AddNote.class);
    public ActivityScenario activityScenario;

    public NoteDatabase myDB;
    public SharedPrefManager prefManager;
    public void setUp() throws Exception {
        super.setUp();
        activityScenario = ActivityScenario.launch(AddNote.class);
        myDB = new NoteDatabase(getInstrumentation().getTargetContext());
        prefManager = new SharedPrefManager(getInstrumentation().getTargetContext());
    }

    //Test new note is added to database
    @Test
    public void testAddNote(){
        String title = "Title 1";
        String content = "Content 1";
        //Type and click
        onView(withId(R.id.addNoteTitle)).perform(typeText(title));
        onView(withId(R.id.addNoteContent)).perform(typeText(content));
        closeSoftKeyboard();
        onView(withId(R.id.saveFab)).perform(click());

        //Get note from database
        long userID = prefManager.get(SharedPrefManager.USER_ID, SharedPrefManager.USER_ID_DEFAULT);
        Note note = myDB.getNoteTest(title, content, userID);

        //Check against database
        assertEquals(title, note.getTitle());
        assertEquals(content, note.getContent());
        assertEquals(userID, note.getUserID());
    }

    public void tearDown() throws Exception {
    }
}
