package com.example.noteapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.res.ResourcesCompat;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class ProfileActivity extends AppCompatActivity{
    Switch switchDayNight;
    SharedPreferences pref;
    SharedPreferences.Editor prefEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_layout);

        switchDayNight = findViewById(R.id.switchDarkLight);
        pref = getSharedPreferences("day_night", MODE_PRIVATE);
        prefEditor = pref.edit();

        getPrefs();
        setDayNightSwitchCheckChange();
    }

    public void getPrefs(){
        boolean isNightMode = pref.getBoolean("night_mode", false);
        setDayNightMode(isNightMode);

        String typeface = pref.getString("typeface", "monospace");
        setTypeface(typeface);

        String fontSize = pref.getString("font_size", "small");
        setFontSize(fontSize);

        String lineHeight = pref.getString("line_height", "1.0");
        setLineHeight(lineHeight);

        String lineWidth = pref.getString("line_width", "64");
        setLineWidth(lineWidth);
    }

    public void setDayNightSwitchCheckChange(){
        switchDayNight.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                setDayNightMode(isChecked);
            }
        });
    }

    public void setDayNightMode(boolean isNightMode){
        if(isNightMode){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            prefEditor.putBoolean("night_mode", true);
            prefEditor.apply();
        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            prefEditor.putBoolean("night_mode", false);
            prefEditor.apply();
        }
    }

    public void typefaceClick(View view){
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener(){
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                return setTypeface(menuItem.getTitleCondensed().toString());
            }
        });
        popupMenu.inflate(R.menu.typeface_menu);
        popupMenu.show();
    }

    public boolean setTypeface(String typeface){
        TextView textCurrentTypeface = findViewById(R.id.textCurrentTypeface);
        switch(typeface) {
            case "anaheim":
                textCurrentTypeface.setText("Anaheim");
                textCurrentTypeface.setTypeface(ResourcesCompat.getFont(ProfileActivity.this, R.font.anaheim));
                prefEditor.putString("typeface", typeface);
                prefEditor.apply();
                return true;
            case "euphoriaScript":
                textCurrentTypeface.setText("Euphoria Script");
                textCurrentTypeface.setTypeface(ResourcesCompat.getFont(ProfileActivity.this, R.font.euphoria_script));
                prefEditor.putString("typeface", typeface);
                prefEditor.apply();
                return true;
            case "lancelot":
                textCurrentTypeface.setText("Lancelot");
                textCurrentTypeface.setTypeface(ResourcesCompat.getFont(ProfileActivity.this, R.font.lancelot));
                prefEditor.putString("typeface", typeface);
                prefEditor.apply();
                return true;
            case "monospace":
                textCurrentTypeface.setText("Monospace");
                textCurrentTypeface.setTypeface(Typeface.MONOSPACE);
                prefEditor.putString("typeface", typeface);
                prefEditor.apply();
                return true;
            default:
                return false;
        }
    }

    public void fontSizeClick(View view){
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                return setFontSize(menuItem.getTitleCondensed().toString());
            }
        });
        popupMenu.inflate(R.menu.font_size_menu);
        popupMenu.show();
    }

    public boolean setFontSize(String fontSize){
        TextView textCurrentFontSize = findViewById(R.id.textCurrentFontSize);
        switch(fontSize){
            case "small":
                textCurrentFontSize.setText("Small");
                prefEditor.putString("font_size", fontSize);
                prefEditor.apply();
                return true;
            case "medium":
                textCurrentFontSize.setText("Medium");
                prefEditor.putString("font_size", fontSize);
                prefEditor.apply();
                return true;
            case "large":
                textCurrentFontSize.setText("Large");
                prefEditor.putString("font_size", fontSize);
                prefEditor.apply();
                return true;
            default:
                return false;
        }
    }


    public void lineHeightClick(View view){
        Dialog dialog = new Dialog(this);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.line_height_dialog);

        TextView textCurrentLineHeight = findViewById(R.id.textCurrentLineHeight);
        TextView textDialogLineHeight = dialog.findViewById(R.id.textDialogLineWidth);
        textDialogLineHeight.setText(textCurrentLineHeight.getText());

        DecimalFormat df = new DecimalFormat("0.0");

        ImageButton buttonIncreaseLineHeight = dialog.findViewById(R.id.imageButtonIncreaseLineWidth);
        buttonIncreaseLineHeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double lineHeight = Double.parseDouble(textDialogLineHeight.getText().toString());
                double newLineHeight = lineHeight + 0.1;

                if(newLineHeight >= 1.0 && newLineHeight <= 2.0){
                    textDialogLineHeight.setText(df.format(newLineHeight));
                }else{
                    Toast.makeText(ProfileActivity.this, "Range between 1.0 and 2.0", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ImageButton buttonDecreaseLineHeight = dialog.findViewById(R.id.imageButtonDecreaseLineWidth);
        buttonDecreaseLineHeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double lineHeight = Double.parseDouble(textDialogLineHeight.getText().toString());
                double newLineHeight = lineHeight - 0.1;

                if(newLineHeight >= 1.0 && newLineHeight <= 2.0){
                    textDialogLineHeight.setText(df.format(newLineHeight));
                }else{
                    Toast.makeText(ProfileActivity.this, "Height range between 1.0 and 2.0", Toast.LENGTH_SHORT).show();
                }
            }
        });

        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                setLineHeight(textDialogLineHeight.getText().toString());
            }
        });

        dialog.show();
    }

    public void setLineHeight(String lineHeight){
        TextView textCurrentLineHeight = findViewById(R.id.textCurrentLineHeight);
        textCurrentLineHeight.setText(lineHeight);
        prefEditor.putString("line_height", lineHeight);
        prefEditor.apply();
    }

    //64-240
    public void lineWidthClick(View view){
        Dialog dialog = new Dialog(this);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.line_width_dialog);

        TextView textCurrentLineWidth = findViewById(R.id.textCurrentLineWidth);
        TextView textDialogLineWidth = dialog.findViewById(R.id.textDialogLineWidth);
        textDialogLineWidth.setText(textCurrentLineWidth.getText());

        ImageButton buttonIncreaseLineWidth = dialog.findViewById(R.id.imageButtonIncreaseLineWidth);
        buttonIncreaseLineWidth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int lineWidth = Integer.parseInt(textDialogLineWidth.getText().toString());
                int newLineWidth = lineWidth + 2;

                if(newLineWidth >= 64 && newLineWidth <= 240){
                    textDialogLineWidth.setText(Integer.toString(newLineWidth));
                }else{
                    Toast.makeText(ProfileActivity.this, "Width range between 64 and 240", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ImageButton buttonDecreaseLineWidth = dialog.findViewById(R.id.imageButtonDecreaseLineWidth);
        buttonDecreaseLineWidth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int lineWidth = Integer.parseInt(textDialogLineWidth.getText().toString());
                int newLineWidth = lineWidth - 2;

                if(newLineWidth >= 64 && newLineWidth <= 240){
                    textDialogLineWidth.setText(Integer.toString(newLineWidth));
                }else{
                    Toast.makeText(ProfileActivity.this, "Width range between 64 and 240", Toast.LENGTH_SHORT).show();
                }
            }
        });

        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                setLineWidth(textDialogLineWidth.getText().toString());
            }
        });

        dialog.show();
    }

    public void setLineWidth(String lineWidth){
        TextView textCurrentLineWidth = findViewById(R.id.textCurrentLineWidth);
        textCurrentLineWidth.setText(lineWidth);
        prefEditor.putString("line_width", lineWidth);
        prefEditor.apply();
    }

    public void loginClick(View view){
        //New Activity

    }
}
