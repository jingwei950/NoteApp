package com.example.noteapp.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.res.ResourcesCompat;

import com.example.noteapp.R;

public class SharedPrefManager {
    SharedPreferences pref;
    SharedPreferences.Editor prefEditor;
    Context context;

    private final static String PREFERENCES = "preferences";
    public final static String NIGHT_MODE = "night_mode";
    public final static String TYPEFACE = "typeface";
    public final static String FONT_SIZE = "font_size";
    public final static String LINE_HEIGHT = "line_height";
    public final static String LETTER_SPACING = "line_width";
    public final static String USER_ID = "user_id";

    public final static String NIGHT_MODE_DEFAULT = "system";
    public final static String TYPEFACE_DEFAULT = "anaheim";
    public final static String FONT_SIZE_DEFAULT = "medium";
    public final static String LINE_HEIGHT_DEFAULT = "1.0";
    public final static String LETTER_SPACING_DEFAULT = "0.0";

    public final static float FONT_SIZE_SMALL = 15.0f;
    public final static float FONT_SIZE_MEDIUM = 20.0f;
    public final static float FONT_SIZE_LARGE = 30.0f;


    //Constructor
    public SharedPrefManager(Context context){
        this.context = context;
        pref = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        prefEditor = pref.edit();
    }

    //Get String SharedPref
    public String get(String key, String defVal){
        return pref.getString(key, defVal);
    }

    //Set String SharedPref
    public void set(String key, String val){
        prefEditor.putString(key, val);
        prefEditor.apply();
    }

    //Get long SharedPref (overload)
    public long get(String key, long defVal){
        return pref.getLong(key, defVal);
    }

    //Set long SharedPref (overload)
    public void set(String key, long val){
        prefEditor.putLong(key, val);
        prefEditor.apply();
    }

    //Sets theme for day/night
    public boolean setDayNightMode(String isNightMode){
        switch(isNightMode) {
            case "day":
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                set(NIGHT_MODE, "day");
                return true;
            case "night":
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                set(NIGHT_MODE, "night");
                return true;
            case "system":
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                set(NIGHT_MODE, "system");
                return true;
            default:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                set(NIGHT_MODE, "system");
                return false;
        }
    }

    //Sets typeface for notes
    public void setTypeface(TextView textView, String typeface){
        switch(typeface){
            case "anaheim":
                textView.setTypeface(ResourcesCompat.getFont(context, R.font.anaheim));
                break;
            case "euphoriaScript":
                textView.setTypeface(ResourcesCompat.getFont(context, R.font.euphoria_script));
                break;
            case "lancelot":
                textView.setTypeface(ResourcesCompat.getFont(context, R.font.lancelot));
                break;
            case "monospace":
                textView.setTypeface(Typeface.MONOSPACE);
                break;
            default:
                break;
        }
    }

    //Sets font size for notes
    public void setFontSize(TextView textView, String fontSize){
        switch(fontSize){
            case "small":
                textView.setTextSize(SharedPrefManager.FONT_SIZE_SMALL);
                break;
            case "medium":
                textView.setTextSize(SharedPrefManager.FONT_SIZE_MEDIUM);
                break;
            case "large":
                textView.setTextSize(SharedPrefManager.FONT_SIZE_LARGE);
                break;
            default:
                break;
        }
    }

    //Sets line height for notes
    public void setLineHeight(TextView textView, String lineHeight){
        float lineHeight_float = Float.parseFloat(lineHeight);
        textView.setLineSpacing(0, lineHeight_float);
    }

    //sets letter spacing for notes
    public void setLetterSpacing(TextView textView, String letterSpacing){
        float letterSpacing_float = Float.parseFloat(letterSpacing);
        textView.setLetterSpacing(letterSpacing_float);
    }
}