package com.example.noteapp;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;



import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import junit.framework.TestCase;


import org.junit.Rule;
import org.junit.Test;

public class LoginActivityTest extends TestCase {

    @Rule
    public ActivityScenarioRule<LoginActivity> activityScenarioRule = new ActivityScenarioRule(LoginActivity.class);
    public ActivityScenario activityScenario;

    public void setUp() throws Exception {
        super.setUp();
        activityScenario = ActivityScenario.launch(LoginActivity.class);
        Intents.init();
    }

    //Login success
    @Test
    public void testLoginButtonSuccess(){
        String email = "user1@gmail.com";
        String username = "user1";
        String password = "123";

        //Type and click
        onView(withId(R.id.userTextEmail)).perform(typeText(email));
        onView(withId(R.id.userTextName)).perform(typeText(username));
        onView(withId(R.id.userTextPass)).perform(typeText(password));
        onView(withId(R.id.loginButton)).perform(click());

        //Check
        intended(hasComponent(ProfileActivity.class.getName()));
    }

    //Test no user matched
    @Test public void testLoginButtonInvalidCredentials(){
        String email = "email";
        String username = "name";
        String password = "pass";

        //Type and click
        onView(withId(R.id.userTextEmail)).perform(typeText(email));
        onView(withId(R.id.userTextName)).perform(typeText(username));
        onView(withId(R.id.userTextPass)).perform(typeText(password));
        onView(withId(R.id.loginButton)).perform(click());

        //Check
        onView(withId(R.id.errorMessageLogin)).check(matches(withText(R.string.invalid_credentials)));
    }

    //Test empty fields
    @Test
    public void testLoginButtonEmptyFields(){
        //Click
        onView(withId(R.id.loginButton)).perform(click());

        //Check
        onView(withId(R.id.errorMessageLogin)).check(matches(withText(R.string.empty_fields)));
    }

    public void tearDown() throws Exception {
        Intents.release();
    }
}