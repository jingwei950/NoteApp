package com.example.noteapp;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.noteapp.model.SharedPrefManager;

import java.text.DecimalFormat;

public class TypographyFragment extends Fragment {
    View rootView;
    TextView textCurrentTypeface;
    TextView textCurrentFontSize;
    TextView textCurrentLineHeight;
    TextView textCurrentLetterSpacing;
    SharedPrefManager prefManager;
    DecimalFormat df = new DecimalFormat("0.0");

    public TypographyFragment() {
        // Required empty public constructor
    }

    //Constructor
    public static TypographyFragment newInstance() {
        TypographyFragment fragment = new TypographyFragment();
        return fragment;
    }

    //Init SharedPrefManager
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefManager = new SharedPrefManager(getActivity().getApplicationContext());
    }

    //Get rootView
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_typography, container, false);
        return rootView;
    }

    //Init views, onclick events
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textCurrentTypeface = rootView.findViewById(R.id.textCurrentTypeface);
        textCurrentFontSize = rootView.findViewById(R.id.textCurrentFontSize);
        textCurrentLineHeight = rootView.findViewById(R.id.textCurrentLineHeight);
        textCurrentLetterSpacing = rootView.findViewById(R.id.textCurrentLetterSpacing);

        getPref();
        setTypefaceClick();
        setFontSizeClick();
        setLineHeightClick();
        setLetterSpacingClick();
    }

    //Retrieve the typography from SharedPref
    public void getPref(){
        String typeface = prefManager.get(SharedPrefManager.TYPEFACE, SharedPrefManager.TYPEFACE_DEFAULT);
        textCurrentTypeface.setText(typeface);
        prefManager.setTypeface(textCurrentTypeface, typeface);

        String fontSize = prefManager.get(SharedPrefManager.FONT_SIZE, SharedPrefManager.FONT_SIZE_DEFAULT);
        textCurrentFontSize.setText(fontSize);
        prefManager.setFontSize(textCurrentFontSize, fontSize);

        String lineHeight = prefManager.get(SharedPrefManager.LINE_HEIGHT, SharedPrefManager.LINE_HEIGHT_DEFAULT);
        textCurrentLineHeight.setText(lineHeight);

        String letterSpacing = prefManager.get(SharedPrefManager.LETTER_SPACING, SharedPrefManager.LETTER_SPACING_DEFAULT);
        textCurrentLetterSpacing.setText(letterSpacing);
    }

    //Define the typeface onclick event
    public void setTypefaceClick(){
        LinearLayout typefaceLayout = rootView.findViewById(R.id.typefaceLayout);
        typefaceLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(getActivity().getApplicationContext(), view);
                //Set menu item onclick event
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        String typeface = menuItem.getTitleCondensed().toString();
                        prefManager.set(SharedPrefManager.TYPEFACE, typeface);
                        prefManager.setTypeface(textCurrentTypeface, typeface);
                        textCurrentTypeface.setText(typeface);

                        return true;
                    }
                });
                popupMenu.inflate(R.menu.typeface_menu);
                popupMenu.show();
            }
        });
    }

    //Define the font size onclick event
    public void setFontSizeClick(){
        LinearLayout fontSizeLayout = rootView.findViewById(R.id.fontSizeLayout);
        fontSizeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(getActivity().getApplicationContext(), view);
                //Set menu item onclick event
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        String fontSize = menuItem.getTitleCondensed().toString();
                        prefManager.set(SharedPrefManager.FONT_SIZE, fontSize);
                        prefManager.setFontSize(textCurrentFontSize, fontSize);
                        textCurrentFontSize.setText(fontSize);

                        return true;
                    }
                });
                popupMenu.inflate(R.menu.font_size_menu);
                popupMenu.show();
            }
        });
    }

    //Define the line height onclick event
    public void setLineHeightClick(){
        LinearLayout lineHeightLayout = rootView.findViewById(R.id.lineHeightLayout);
        lineHeightLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Open dialog
                Dialog dialog = new Dialog(getActivity());
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.line_height_dialog);

                TextView textDialogLineHeight = dialog.findViewById(R.id.textDialogLineHeight);
                textDialogLineHeight.setText(textCurrentLineHeight.getText());

                ImageButton buttonIncreaseLineHeight = dialog.findViewById(R.id.imageButtonIncreaseLineHeight);
                //Set increase button onclick event
                buttonIncreaseLineHeight.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        double lineHeight = Double.parseDouble(textDialogLineHeight.getText().toString());
                        double newLineHeight = lineHeight + 0.1;
                        if(newLineHeight >= 1.0 && newLineHeight <= 4.0){
                            textDialogLineHeight.setText(df.format(newLineHeight));
                        }else{
                            Toast.makeText(getActivity().getApplicationContext(), R.string.line_height_out_of_range, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                ImageButton buttonDecreaseLineHeight = dialog.findViewById(R.id.imageButtonDecreaseLineHeight);
                //Set decrease button onclick event
                buttonDecreaseLineHeight.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        double lineHeight = Double.parseDouble(textDialogLineHeight.getText().toString());
                        double newLineHeight = lineHeight - 0.1;
                        if(newLineHeight >= 1.0 && newLineHeight <= 4.0){
                            textDialogLineHeight.setText(df.format(newLineHeight));
                        }else{
                            Toast.makeText(getActivity().getApplicationContext(),R.string.line_height_out_of_range, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                //Set dialog cancel event
                dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        String lineHeight = textDialogLineHeight.getText().toString();
                        textCurrentLineHeight.setText(lineHeight);
                        prefManager.set(SharedPrefManager.LINE_HEIGHT, lineHeight);
                    }
                });

                dialog.show();
            }
        });
    }

    //Define the letter spacing onclick event
    public void setLetterSpacingClick(){
        LinearLayout letterSpacingLayout = rootView.findViewById(R.id.letterSpacingLayout);
        letterSpacingLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Open dialog
                Dialog dialog = new Dialog(getActivity());
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.letter_spacing_dialog);

                TextView textDialogLetterSpacing = dialog.findViewById(R.id.textDialogLetterSpacing);
                textDialogLetterSpacing.setText(textCurrentLetterSpacing.getText());

                ImageButton buttonIncreaseLetterSpacing = dialog.findViewById(R.id.imageButtonIncreaseLetterSpacing);
                //Set increase button onclick event
                buttonIncreaseLetterSpacing.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        double letterSpacing = Double.parseDouble(textDialogLetterSpacing.getText().toString());
                        double newLetterSpacing = letterSpacing + 0.1;
                        if(newLetterSpacing >= 0.0 && newLetterSpacing <= 1.0){
                            textDialogLetterSpacing.setText(df.format(newLetterSpacing));
                        }else{
                            Toast.makeText(getActivity().getApplicationContext(), R.string.letter_spacing_out_of_range, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                ImageButton buttonDecreaseLetterSpacing = dialog.findViewById(R.id.imageButtonDecreaseLetterSpacing);
                //Set decrease button onclick event
                buttonDecreaseLetterSpacing.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        double letterSpacing = Double.parseDouble(textDialogLetterSpacing.getText().toString());
                        double newLetterSpacing = letterSpacing - 0.1;
                        if(newLetterSpacing >= 0.0 && newLetterSpacing <= 1.0){
                            textDialogLetterSpacing.setText(df.format(newLetterSpacing));
                        }else{
                            Toast.makeText(getActivity().getApplicationContext(), R.string.letter_spacing_out_of_range, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                //Set dialog cancel event
                dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        String letterSpacing = textDialogLetterSpacing.getText().toString();
                        textCurrentLetterSpacing.setText(letterSpacing);
                        prefManager.set(SharedPrefManager.LETTER_SPACING, letterSpacing);
                    }
                });

                dialog.show();
            }
        });
    }
}