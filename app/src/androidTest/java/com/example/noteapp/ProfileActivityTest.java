package com.example.noteapp;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.pressBack;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.RootMatchers.isPlatformPopup;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

import android.widget.TextView;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.intent.Intents;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.example.noteapp.model.SharedPrefManager;

import junit.framework.TestCase;

import org.junit.Rule;
import org.junit.Test;

import java.text.DecimalFormat;

public class ProfileActivityTest extends TestCase {

    @Rule
    public ActivityScenarioRule<ProfileActivity> activityScenarioRule = new ActivityScenarioRule<>(ProfileActivity.class);
    public ActivityScenario activityScenario;

    SharedPrefManager prefManager;
    NoteDatabase myDB;
    public void setUp() throws Exception {
        super.setUp();
        activityScenario = ActivityScenario.launch(ProfileActivity.class);
        prefManager = new SharedPrefManager(getInstrumentation().getTargetContext());
        myDB = new NoteDatabase(getInstrumentation().getTargetContext());
        Intents.init();
    }

    //Fragment Display Tests
    @Test
    public void testProfileFragmentDisplay(){
        onView(withId(R.id.profileFragmentContainer)).check(matches(isDisplayed()));
    }

    @Test
    public void testTypographyFragmentDisplay(){
        onView(withId(R.id.typographyFragmentContainer)).check(matches(isDisplayed()));
    }

    @Test
    public void testThemeFragmentDisplay(){
        onView(withId(R.id.themeFragmentContainer)).check(matches(isDisplayed()));
    }

    //Shared Preference Test
    @Test
    public void testTypefaceDisplay(){
        //Get typeface from SharedPreferences
        String typeface = prefManager.get(SharedPrefManager.TYPEFACE, SharedPrefManager.TYPEFACE_DEFAULT);
        //Check
        onView(withId(R.id.textCurrentTypeface)).check(matches(withText(typeface)));
    }

    @Test
    public void testFontSizeDisplay(){
        //Get font size from SharedPreferences
        String fontSize = prefManager.get(SharedPrefManager.FONT_SIZE, SharedPrefManager.FONT_SIZE_DEFAULT);
        //Check
        onView(withId(R.id.textCurrentFontSize)).check(matches(withText(fontSize)));
    }

    @Test
    public void testLineHeightDisplay(){
        //Get line height from SharedPreferences
        String lineHeight = prefManager.get(SharedPrefManager.LINE_HEIGHT, SharedPrefManager.LINE_HEIGHT_DEFAULT);
        //Check
        onView(withId(R.id.textCurrentLineHeight)).check(matches(withText(lineHeight)));
    }

    @Test
    public void testLetterSpacingDisplay(){
        //Get letter spacing from SharedPreferences
        String letterSpacing = prefManager.get(SharedPrefManager.LETTER_SPACING, SharedPrefManager.LETTER_SPACING_DEFAULT);
        //Check
        onView(withId(R.id.textCurrentLetterSpacing)).check(matches(withText(letterSpacing)));
    }

    @Test
    public void testDayNightDisplay(){
        //Get night mode from SharedPreferences
        String dayNight = prefManager.get(SharedPrefManager.NIGHT_MODE, SharedPrefManager.NIGHT_MODE_DEFAULT);
        //Check
        onView(withId(R.id.textCurrentDayNight)).check(matches(withText(dayNight)));
    }

    @Test
    public void testSignUpButton(){
        //Click sign up button
        onView(withId(R.id.buttonSignUpPage)).perform(click());
        //Check
        intended(hasComponent(SignUpActivity.class.getName()));
    }

    //Click Tests
    @Test
    public void testTypefaceMenu(){
        //Click typeface and click lancelot
        onView(withId(R.id.typefaceLayout)).perform(click());
        onView(withText("Lancelot")).inRoot(isPlatformPopup()).perform(click());
        //Check
        onView(withId(R.id.textCurrentTypeface)).check(matches(withText("lancelot")));
    }

    @Test
    public void testFontSizeMenu(){
        //Click font size and click large
        onView(withId(R.id.fontSizeLayout)).perform(click());
        onView(withText("Large")).inRoot(isPlatformPopup()).perform(click());
        //Check
        onView(withId(R.id.textCurrentFontSize)).check(matches(withText("large")));
    }

    @Test
    public void testLineHeightDialog(){
        //Get initial value, and set expected value
        final String[] lineHeight = new String[1];
        activityScenario.onActivity(activity -> {
            TextView textCurrentLineHeight = activity.findViewById(R.id.textCurrentLineHeight);
            DecimalFormat df = new DecimalFormat("0.0");
            lineHeight[0] = df.format(Double.parseDouble(textCurrentLineHeight.getText().toString()) + 0.1);
        });
        //Click
        onView(withId(R.id.lineHeightLayout)).perform(click());
        onView(withId(R.id.imageButtonIncreaseLineHeight)).perform(click());
        onView(isRoot()).perform(pressBack());

        //Check
        onView(withId(R.id.textCurrentLineHeight)).check(matches(withText(lineHeight[0])));
    }

    @Test
    public void testLetterSpacingDialog(){
        //Get initial value, and set expected value
        final String[] lineHeight = new String[1];
        activityScenario.onActivity(activity -> {
            TextView textCurrentLetterSpacing = activity.findViewById(R.id.textCurrentLetterSpacing);
            DecimalFormat df = new DecimalFormat("0.0");
            lineHeight[0] = df.format(Double.parseDouble(textCurrentLetterSpacing.getText().toString()) + 0.1);
        });
        //Click
        onView(withId(R.id.letterSpacingLayout)).perform(click());
        onView(withId(R.id.imageButtonIncreaseLetterSpacing)).perform(click());
        onView(isRoot()).perform(pressBack());

        //Check
        onView(withId(R.id.textCurrentLetterSpacing)).check(matches(withText(lineHeight[0])));
    }

    @Test
    public void testDayNightMenu(){
        //Click dark light and click night
        onView(withId(R.id.dayNightLayout)).perform(scrollTo(), click());
        onView(withText("Night")).inRoot(isPlatformPopup()).perform(click());
        //Check Text
        onView(withId(R.id.textCurrentDayNight)).check(matches(withText("night")));
        activityScenario.onActivity(activity -> {
            //Check actual night mode
            assertEquals(AppCompatDelegate.MODE_NIGHT_YES, AppCompatDelegate.getDefaultNightMode());
        });
    }

    @Test
    public void testUsernameChange(){
        String newUsername = "user";
        //Type and click
        onView(withId(R.id.textProfileUsername)).perform(click());
        onView(withId(R.id.editDialogProfileUsername)).perform(replaceText(newUsername));
        onView(withId(R.id.imageButtonUsernameConfirm)).perform(click());

        //Get user from database
        Users user = myDB.getUser(prefManager.get(SharedPrefManager.USER_ID, SharedPrefManager.USER_ID_DEFAULT));

        //Check
        onView(withId(R.id.textProfileUsername)).check(matches(withText(user.getUserName())));
    }

    @Test
    public void testEmailChange(){
        String newEmail = "user@yahoo.com";
        //Type and click
        onView(withId(R.id.textProfileEmail)).perform(click());
        onView(withId(R.id.editDialogProfileEmail)).perform(replaceText(newEmail));
        onView(withId(R.id.imageButtonEmailConfirm)).perform(click());

        //Get user from database
        Users user = myDB.getUser(prefManager.get(SharedPrefManager.USER_ID, SharedPrefManager.USER_ID_DEFAULT));

        //Check
        onView(withId(R.id.textProfileEmail)).check(matches(withText(user.getUserEmail())));
    }

    @Test
    public void testPasswordChange(){
        String newPassword = "newPass";
        //Type and click
        onView(withId(R.id.textProfilePassword)).perform(click());
        onView(withId(R.id.editDialogProfilePassword)).perform(replaceText(newPassword));
        onView(withId(R.id.editDialogProfileRePassword)).perform(replaceText(newPassword));
        onView(withId(R.id.imageButtonPasswordConfirm)).perform(click());

        //Get user from database
        Users user = myDB.getUser(prefManager.get(SharedPrefManager.USER_ID, SharedPrefManager.USER_ID_DEFAULT));

        //Check
        onView(withId(R.id.textProfilePassword)).check(matches(withText(user.getUserPassword())));
    }

    public void tearDown() throws Exception {
        Intents.release();
    }
}