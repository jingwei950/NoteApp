package com.example.noteapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.noteapp.model.SharedPrefManager;

public class ThemeFragment extends Fragment {
    View rootView;
    TextView textCurrentDayNight;

    SharedPrefManager prefManager;

    public ThemeFragment() {
        // Required empty public constructor
    }

    //Constructor
    public static ThemeFragment newInstance() {
        ThemeFragment fragment = new ThemeFragment();
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
        rootView = inflater.inflate(R.layout.fragment_theme, container, false);
        return rootView;
    }

    //Init views, onclick events
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textCurrentDayNight = rootView.findViewById(R.id.textCurrentDayNight);

        getPref();
        setDayNightLayoutClick();
    }

    //Retrieve night_mode from SharedPref
    public void getPref(){
        String dayNight = prefManager.get(SharedPrefManager.NIGHT_MODE, SharedPrefManager.NIGHT_MODE_DEFAULT);
        textCurrentDayNight.setText(dayNight);
    }

    //Define the day night onclick event
    public void setDayNightLayoutClick(){
        LinearLayout dayNightLayout = rootView.findViewById(R.id.dayNightLayout);
        dayNightLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(getActivity().getApplicationContext(), view);
                //Set menu item onclick event
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        String dayNight = menuItem.getTitleCondensed().toString();
                        if(prefManager.setDayNightMode(dayNight)){
                            textCurrentDayNight.setText(dayNight);
                            return true;
                        }else{
                            return false;
                        }
                    }
                });
                popupMenu.inflate(R.menu.day_night_menu);
                popupMenu.show();
            }
        });
    }
}