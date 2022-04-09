package com.example.noteapp;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class SignUpActivityTest extends TestCase {

    @Rule
    public ActivityScenarioRule<SignUpActivity> activityScenarioRule = new ActivityScenarioRule<>(SignUpActivity.class);
    public ActivityScenario activityScenario;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        //Start simulating activity
        activityScenario = ActivityScenario.launch(SignUpActivity.class);
        Intents.init();
    }

    //Field Validity Tests
    @Test
    public void testEmptyFields(){
        //Click
        Espresso.onView(withId(R.id.buttonSignUp)).perform(click());
        //Check
        Espresso.onView(withId(R.id.textError)).check(matches(withText(R.string.empty_fields)));
    }

    @Test
    public void testEmailTaken(){
        String email = "user1@gmail.com";
        String username = "user";
        String password = "123";
        String rePassword = "123";

        //Simulate
        writeAndClick(email, username, password, rePassword);
        //Check
        Espresso.onView(withId(R.id.textError)).check(matches(withText(R.string.email_taken)));
    }

    @Test
    public void testInvalidEmail(){
        String email = "email";
        String username = "user";
        String password = "123";
        String rePassword = "123";

        //Simulate
        writeAndClick(email, username, password, rePassword);
        //Check
        Espresso.onView(withId(R.id.textError)).check(matches(withText(R.string.invalid_email)));
    }

    @Test
    public void testUsernameTaken(){
        String email = "user@gmail.com";
        String username = "user1";
        String password = "123";
        String rePassword = "123";

        //Simulate
        writeAndClick(email, username, password, rePassword);
        //Check
        Espresso.onView(withId(R.id.textError)).check(matches(withText(R.string.username_taken)));
    }

    @Test
    public void testNotSamePassword(){
        String email = "user@gmail.com";
        String username = "user";
        String password = "123";
        String rePassword = "987";

        //Simulate
        writeAndClick(email, username, password, rePassword);

        //Check
        Espresso.onView(withId(R.id.textError)).check(matches(withText(R.string.password_not_same)));
    }

    @Test
    public void testValidFields(){
        String email = "user@gmail.com";
        String username = "user";
        String password = "123";
        String rePassword = "123";

        //Simulate
        writeAndClick(email, username, password, rePassword);
        //Check
        intended(hasComponent(ProfileActivity.class.getName()));
    }

    @After
    public void tearDown() throws Exception {
        Intents.release();
    }

    public void writeAndClick(String email, String username, String password, String rePassword){
        //Input
        Espresso.onView(withId(R.id.editSignUpEmail)).perform(typeText(email));
        Espresso.onView(withId(R.id.editSignUpUsername)).perform(typeText(username));
        Espresso.onView(withId(R.id.editSignUpPassword)).perform(typeText(password));
        Espresso.onView(withId(R.id.editSignUpRePassword)).perform(typeText(rePassword));
        //Close keyboard
        Espresso.closeSoftKeyboard();
        //Click sign up
        Espresso.onView(withId(R.id.buttonSignUp)).perform(click());
    }
}