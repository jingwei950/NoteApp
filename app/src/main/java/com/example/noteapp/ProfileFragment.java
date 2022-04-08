package com.example.noteapp;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.noteapp.model.SharedPrefManager;
import com.example.noteapp.model.Validation;

public class ProfileFragment extends Fragment {
    View rootView;
    Button buttonLogin;
    Button buttonSignup;
    TextView textProfileUserName;
    TextView textProfileEmail;
    TextView textProfilePassword;

    SharedPrefManager prefManager;
    Users user;
    NoteDatabase myDB;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //Get rootView
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        return rootView;
    }

    //Init views, managers onclick events
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Initialize all views
        buttonLogin = rootView.findViewById(R.id.buttonLogin);
        buttonSignup = rootView.findViewById(R.id.buttonSignUpPage);
        textProfileUserName = rootView.findViewById(R.id.textProfileUsername);
        textProfileEmail = rootView.findViewById(R.id.textProfileEmail);
        textProfilePassword = rootView.findViewById(R.id.textProfilePassword);

        //New SharedPref and Database managers
        prefManager = new SharedPrefManager(getActivity().getApplicationContext());
        myDB = new NoteDatabase(getActivity().getApplicationContext());

        getUser();
        displayUser();

        setButtonLoginClick();
        setButtonSignupClick();

        //Check login status -set onclick event accordingly
        if(prefManager.get(SharedPrefManager.USER_ID, 0) != 0){
            setUsernameClick();
            setEmailClick();
            setPasswordClick();
        }
    }

    //Get current user from SharedPref and Database
    public void getUser(){
        long id = prefManager.get(SharedPrefManager.USER_ID, 0);
        if(id > 0){
            user = myDB.getUser(id);
        }else{
            user = new Users(0, "Username" , "Email", "Password");
        }
    }

    //Display the information of current user
    public void displayUser(){
        textProfileUserName.setText(user.getUserName());
        textProfileEmail.setText(user.getUserEmail());
        textProfilePassword.setText(user.getUserPassword());
        if(prefManager.get(SharedPrefManager.USER_ID, 0) != 0){
            textProfilePassword.setTransformationMethod(new PasswordTransformationMethod());
        }
    }

    //Defines the login Button onclick event -function changes depending on login status
    public void setButtonLoginClick(){
        if(prefManager.get(SharedPrefManager.USER_ID, 0) == 0){
            buttonLogin.setText("Login");
            buttonLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //To Login Activity

                }
            });
        }else{
            buttonLogin.setText("Logout");
            buttonLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Logout
                    prefManager.set(SharedPrefManager.USER_ID, 0);
                    Intent intent = new Intent(getActivity().getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
            });
        }
    }

    //Defines the sign up button onclick event
    public void setButtonSignupClick(){
        buttonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //To SignUp Activity
                Intent signUp =  new Intent(view.getContext(), SignUpActivity.class);
                startActivity(signUp);
            }
        });
    }

    //Defines the username onclick event
    public void setUsernameClick(){
        textProfileUserName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(getActivity());
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.name_change_dialog);

                TextView editDialogProfileUsername = dialog.findViewById(R.id.editDialogProfileUsername);
                editDialogProfileUsername.setText(textProfileUserName.getText());

                //Define the confirm button onclick event to validate and update database
                ImageButton imageButtonUsernameConfirm = dialog.findViewById(R.id.imageButtonUsernameConfirm);
                imageButtonUsernameConfirm.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(View view) {
                        //Check Valid
                        Validation valid = checkUsernameValid(editDialogProfileUsername.getText().toString());

                        if(valid.getValid()){
                            //UPDATE DB
                            user.setUserName(editDialogProfileUsername.getText().toString());

                            if(myDB.checkUsernameExists(user)){
                                Toast.makeText(getActivity().getApplicationContext(), "Username exists", Toast.LENGTH_SHORT).show();
                                user.setUserName(textProfileUserName.getText().toString());
                            }else{
                                myDB.updateUser(user);
                                textProfileUserName.setText(editDialogProfileUsername.getText());
                                dialog.dismiss();
                            }
                        }else{
                            String errorMessage = String.join("\n", valid.getErrorMessageList());
                            Toast.makeText(getActivity().getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                dialog.show();
            }
        });
    }

    //Defines the email onclick event
    public void setEmailClick(){
        textProfileEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(getActivity());
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.email_change_dialog);

                TextView editDialogProfileEmail = dialog.findViewById(R.id.editDialogProfileEmail);
                editDialogProfileEmail.setText(textProfileEmail.getText());

                //Define the confirm button onclick event to validate and update database
                ImageButton imageButtonEmailConfirm = dialog.findViewById(R.id.imageButtonEmailConfirm);
                imageButtonEmailConfirm.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(View view) {
                        Validation valid = checkEmailValid(editDialogProfileEmail.getText().toString());

                        if(valid.getValid()){
                            //UPDATE DB
                            user.setUserName(editDialogProfileEmail.getText().toString());

                            if(myDB.checkEmailExists(user)){
                                Toast.makeText(getActivity().getApplicationContext(), "Email exists", Toast.LENGTH_SHORT).show();
                                user.setUserName(textProfileEmail.getText().toString());
                            }else{
                                myDB.updateUser(user);
                                textProfileEmail.setText(editDialogProfileEmail.getText());
                                dialog.dismiss();
                            }
                        }else{
                            String errorMessage = String.join("\n", valid.getErrorMessageList());
                            Toast.makeText(getActivity().getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                dialog.show();
            }
        });
    }

    //Defines the password onclick event
    public void setPasswordClick(){
        textProfilePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(getActivity());
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.password_change_layout);

                TextView editDialogProfilePassword = dialog.findViewById(R.id.editDialogProfilePassword);
                TextView editDialogProfileRePassword = dialog.findViewById(R.id.editDialogProfileRePassword);
                editDialogProfilePassword.setText(textProfilePassword.getText());
                editDialogProfileRePassword.setText(textProfilePassword.getText());
                editDialogProfilePassword.setTransformationMethod(new PasswordTransformationMethod());
                editDialogProfileRePassword.setTransformationMethod(new PasswordTransformationMethod());

                //Define the show/hide buttons onclick event to show and hide password
                ImageButton imageButtonShowHidePassword = dialog.findViewById(R.id.imageButtonShowHidePassword);
                imageButtonShowHidePassword.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showHide(editDialogProfilePassword);
                    }
                });

                ImageButton imageButtonShoHideRePassword = dialog.findViewById(R.id.imageButtonShowHideRePassword);
                imageButtonShoHideRePassword.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showHide(editDialogProfileRePassword);
                    }
                });

                //Define the confirm button onclick event to validate and update database
                ImageButton imageButtonPasswordConfirm = dialog.findViewById(R.id.imageButtonPasswordConfirm);
                imageButtonPasswordConfirm.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(View view) {
                        Validation valid = checkPasswordValid(editDialogProfilePassword.getText().toString(), editDialogProfileRePassword.getText().toString());
                        if(valid.getValid()){
                            //UPDATE DB
                            user.setUserPassword(editDialogProfilePassword.getText().toString());
                            myDB.updateUser(user);

                            textProfilePassword.setText(editDialogProfilePassword.getText());
                            dialog.dismiss();
                        }else{
                            String errorMessage = String.join("\n", valid.getErrorMessageList());
                            Toast.makeText(getActivity().getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                dialog.show();
            }
        });
    }

    //To show and hide passwords
    public void showHide(TextView textView){
        if(textView.getTransformationMethod() instanceof PasswordTransformationMethod){
            textView.setTransformationMethod(null);
        }else{
            textView.setTransformationMethod(new PasswordTransformationMethod());
        }
    }

    //Check validity of username
    public Validation checkUsernameValid(String username){
        Validation valid = new Validation();
        valid.setValid(true);

        if(username.equals("")){
            valid.setValid(false);
            valid.addErrorMessage("Empty Field");
        }
        //if exists in database
        if(false){
            valid.setValid(false);
            valid.addErrorMessage("Username already exists");
        }
        return valid;
    }

    //Check validity of email
    public Validation checkEmailValid(String email){
        Validation valid = new Validation();
        valid.setValid(true);

        if(email.equals("")){
            valid.setValid(false);
            valid.addErrorMessage("Empty Field");
        }

        if(!email.contains("@")){
            valid.setValid(false);
            valid.addErrorMessage("Incorrect Format");
        }

        //if exists in database
        if(false){
            valid.setValid(false);
            valid.addErrorMessage("Email already exists");
        }
        return valid;
    }

    //Check validity of password
    public Validation checkPasswordValid(String password, String rePassword){
        Validation valid = new Validation();
        valid.setValid(true);
        if(password.equals("") || rePassword.equals("")){
            valid.setValid(false);
            valid.addErrorMessage("Empty Fields");
        }

        if(!password.equals(rePassword)){
            valid.setValid(false);
            valid.addErrorMessage("Passwords are not same");
        }
        return valid;
    }
}