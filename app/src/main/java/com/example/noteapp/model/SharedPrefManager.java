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

    public final static String NIGHT_MODE_DEFAULT = "system";
    public final static String TYPEFACE_DEFAULT = "anaheim";
    public final static String FONT_SIZE_DEFAULT = "medium";
    public final static String LINE_HEIGHT_DEFAULT = "1.0";
    public final static String LETTER_SPACING_DEFAULT = "0.0";

    public final static float FONT_SIZE_SMALL = 20.0f;
    public final static float FONT_SIZE_MEDIUM = 30.0f;
    public final static float FONT_SIZE_LARGE = 40.0f;


    public SharedPrefManager(Context context){
        this.context = context;
        pref = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        prefEditor = pref.edit();
    }

    public String get(String key, String defVal){
        return pref.getString(key, defVal);
    }

    public void set(String key, String val){
        prefEditor.putString(key, val);
        prefEditor.apply();
    }

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
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                set(NIGHT_MODE, "day");
                return false;
        }
    }

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

    public void setLineHeight(TextView textView, String lineHeight){
        float lineHeight_float = Float.parseFloat(lineHeight);
        textView.setLineSpacing(0, lineHeight_float);
    }

    public void setLetterSpacing(TextView textView, String letterSpacing){
        float letterSpacing_float = Float.parseFloat(letterSpacing);
        textView.setLetterSpacing(letterSpacing_float);
    }
}