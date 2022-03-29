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

    public static ThemeFragment newInstance() {
        ThemeFragment fragment = new ThemeFragment();
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
        rootView = inflater.inflate(R.layout.fragment_theme, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textCurrentDayNight = rootView.findViewById(R.id.textCurrentDayNight);

        getPref();
        setDayNightLayoutClick();
    }

    public void getPref(){
        String dayNight = prefManager.get(SharedPrefManager.NIGHT_MODE, "day");
        setDayNightText(dayNight);
    }

    public void setDayNightLayoutClick(){
        LinearLayout dayNightLayout = rootView.findViewById(R.id.dayNightLayout);
        dayNightLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(getActivity().getApplicationContext(), view);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        String dayNight = menuItem.getTitleCondensed().toString();
                        if(prefManager.setDayNightMode(dayNight)){
                            setDayNightText(dayNight);
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

    public void setDayNightText(String dayNight){
        prefManager.set(SharedPrefManager.NIGHT_MODE, dayNight);

        switch(dayNight){
            case "day":
                textCurrentDayNight.setText("Day");
                break;
            case "night":
                textCurrentDayNight.setText("Night");
                break;
            case "system":
                textCurrentDayNight.setText("System");
                break;
            default:
                textCurrentDayNight.setText("System");
                break;
        }
    }

}