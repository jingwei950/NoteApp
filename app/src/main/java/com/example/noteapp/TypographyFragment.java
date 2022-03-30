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

    // TODO: Rename and change types and number of parameters
    public static TypographyFragment newInstance(String param1, String param2) {
        TypographyFragment fragment = new TypographyFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefManager = new SharedPrefManager(getActivity().getApplicationContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_typography, container, false);
        return rootView;
    }

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

    public void getPref(){
        String typeface = prefManager.get(SharedPrefManager.TYPEFACE, SharedPrefManager.TYPEFACE_DEFAULT);
        setTypeface(typeface);

        String fontSize = prefManager.get(SharedPrefManager.FONT_SIZE, SharedPrefManager.FONT_SIZE_DEFAULT);
        setFontSize(fontSize);

        String lineHeight = prefManager.get(SharedPrefManager.LINE_HEIGHT, SharedPrefManager.LINE_HEIGHT_DEFAULT);
        setLineHeight(lineHeight);

        String letterSpacing = prefManager.get(SharedPrefManager.LETTER_SPACING, SharedPrefManager.LETTER_SPACING_DEFAULT);
        setLetterSpacing(letterSpacing);
    }


    //BUTTON CLICKS
    public void setTypefaceClick(){
        LinearLayout typefaceLayout = rootView.findViewById(R.id.typefaceLayout);
        typefaceLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(getActivity().getApplicationContext(), view);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        return setTypeface(menuItem.getTitleCondensed().toString());
                    }
                });
                popupMenu.inflate(R.menu.typeface_menu);
                popupMenu.show();
            }
        });
    }

    public void setFontSizeClick(){
        LinearLayout fontSizeLayout = rootView.findViewById(R.id.fontSizeLayout);
        fontSizeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(getActivity().getApplicationContext(), view);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        return setFontSize(menuItem.getTitleCondensed().toString());
                    }
                });
                popupMenu.inflate(R.menu.font_size_menu);
                popupMenu.show();
            }
        });
    }

    public void setLineHeightClick(){
        LinearLayout lineHeightLayout = rootView.findViewById(R.id.lineHeightLayout);
        lineHeightLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(getActivity());
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.line_height_dialog);

                TextView textDialogLineHeight = dialog.findViewById(R.id.textDialogLineHeight);
                textDialogLineHeight.setText(textCurrentLineHeight.getText());

                ImageButton buttonIncreaseLineHeight = dialog.findViewById(R.id.imageButtonIncreaseLineHeight);
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

                dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        setLineHeight(textDialogLineHeight.getText().toString());
                    }
                });

                dialog.show();
            }
        });
    }

    public void setLetterSpacingClick(){
        LinearLayout letterSpacingLayout = rootView.findViewById(R.id.letterSpacingLayout);
        letterSpacingLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(getActivity());
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.letter_spacing_dialog);

                TextView textDialogLetterSpacing = dialog.findViewById(R.id.textDialogLetterSpacing);
                textDialogLetterSpacing.setText(textCurrentLetterSpacing.getText());

                ImageButton buttonIncreaseLetterSpacing = dialog.findViewById(R.id.imageButtonIncreaseLetterSpacing);
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

                dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        setLetterSpacing(textDialogLetterSpacing.getText().toString());
                    }
                });

                dialog.show();
            }
        });
    }


    //SETTERS
    public boolean setTypeface(String typeface) {
        prefManager.set(SharedPrefManager.TYPEFACE, typeface);
        switch (typeface) {
            case "anaheim":
                textCurrentTypeface.setText("Anaheim");
                textCurrentTypeface.setTypeface(ResourcesCompat.getFont(getActivity().getApplicationContext(), R.font.anaheim));
                return true;

            case "euphoriaScript":
                textCurrentTypeface.setText("Euphoria Script");
                textCurrentTypeface.setTypeface(ResourcesCompat.getFont(getActivity().getApplicationContext(), R.font.euphoria_script));
                return true;

            case "lancelot":
                textCurrentTypeface.setText("Lancelot");
                textCurrentTypeface.setTypeface(ResourcesCompat.getFont(getActivity().getApplicationContext(), R.font.lancelot));
                return true;

            case "monospace":
                textCurrentTypeface.setText("Monospace");
                textCurrentTypeface.setTypeface(Typeface.MONOSPACE);
                return true;

            default:
                return false;
        }
    }

    public boolean setFontSize(String fontSize){
        prefManager.set(SharedPrefManager.FONT_SIZE, fontSize);
        switch(fontSize){
            case "small":
                textCurrentFontSize.setText("Small");
                textCurrentFontSize.setTextSize(10.0f);
                return true;

            case "medium":
                textCurrentFontSize.setText("Medium");
                textCurrentFontSize.setTextSize(15.0f);

                return true;

            case "large":
                textCurrentFontSize.setText("Large");
                textCurrentFontSize.setTextSize(20.0f);

                return true;

            default:
                return false;
        }
    }

    public void setLineHeight(String lineHeight){
        textCurrentLineHeight.setText(lineHeight);
        prefManager.set(SharedPrefManager.LINE_HEIGHT, lineHeight);
    }

    public void setLetterSpacing(String letterSpacing){
        textCurrentLetterSpacing.setText(letterSpacing);
        prefManager.set(SharedPrefManager.LETTER_SPACING, letterSpacing);
    }
}