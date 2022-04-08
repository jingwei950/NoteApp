package com.example.noteapp;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.pressBack;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.RootMatchers.isPlatformPopup;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.intent.Intents;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.example.noteapp.model.SharedPrefManager;

import junit.framework.TestCase;

import org.hamcrest.CoreMatchers;
import org.junit.Rule;
import org.junit.Test;

import java.text.DecimalFormat;

public class ProfileActivityTest extends TestCase {

    @Rule
    public ActivityScenarioRule<ProfileActivity> activityScenarioRule = new ActivityScenarioRule<>(ProfileActivity.class);
    public ActivityScenario activityScenario;

    SharedPrefManager prefManager;
    public void setUp() throws Exception {
        super.setUp();
        activityScenario = ActivityScenario.launch(ProfileActivity.class);
        prefManager = new SharedPrefManager(getInstrumentation().getTargetContext());
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
        String typeface = prefManager.get(SharedPrefManager.TYPEFACE, SharedPrefManager.TYPEFACE_DEFAULT);
        onView(withId(R.id.textCurrentTypeface)).check(matches(withText(typeface)));
    }

    @Test
    public void testFontSizeDisplay(){
        String fontSize = prefManager.get(SharedPrefManager.FONT_SIZE, SharedPrefManager.FONT_SIZE_DEFAULT);
        onView(withId(R.id.textCurrentFontSize)).check(matches(withText(fontSize)));
    }

    @Test
    public void testLineHeightDisplay(){
        String lineHeight = prefManager.get(SharedPrefManager.LINE_HEIGHT, SharedPrefManager.LINE_HEIGHT_DEFAULT);
        onView(withId(R.id.textCurrentLineHeight)).check(matches(withText(lineHeight)));
    }

    @Test
    public void testLetterSpacingDisplay(){
        String letterSpacing = prefManager.get(SharedPrefManager.LETTER_SPACING, SharedPrefManager.LETTER_SPACING_DEFAULT);
        onView(withId(R.id.textCurrentLetterSpacing)).check(matches(withText(letterSpacing)));
    }

    @Test
    public void testDayNightDisplay(){
        String dayNight = prefManager.get(SharedPrefManager.NIGHT_MODE, SharedPrefManager.NIGHT_MODE_DEFAULT);
        onView(withId(R.id.textCurrentDayNight)).check(matches(withText(dayNight)));
    }

    @Test
    public void testSignUpButton(){
        onView(withId(R.id.buttonSignUpPage)).perform(click());
        intended(hasComponent(SignUpActivity.class.getName()));
    }

    //Click Tests
    @Test
    public void testTypefaceMenu(){
        onView(withId(R.id.typefaceLayout)).perform(click());
        onView(withText("Lancelot")).inRoot(isPlatformPopup()).perform(click());
        onView(withId(R.id.textCurrentTypeface)).check(matches(withText("lancelot")));
    }

    @Test
    public void testFontSizeMenu(){
        onView(withId(R.id.fontSizeLayout)).perform(click());
        onView(withText("Large")).inRoot(isPlatformPopup()).perform(click());
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
        onView(withId(R.id.dayNightLayout)).perform(click());
        onView(withText("Night")).inRoot(isPlatformPopup()).perform(click());
        onView(withId(R.id.textCurrentDayNight)).check(matches(withText("night")));
        activityScenario.onActivity(activity -> {
            assertEquals(AppCompatDelegate.MODE_NIGHT_YES, AppCompatDelegate.getDefaultNightMode());
        });
    }

    public void tearDown() throws Exception {
        Intents.release();
    }
}