package com.example.noteapp;

import android.app.Dialog;
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

import com.example.noteapp.model.Validation;

public class ProfileFragment extends Fragment {
    View rootView;
    Button buttonLogin;
    TextView textProfileUserName;
    TextView textProfileEmail;
    TextView textProfilePassword;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        buttonLogin = rootView.findViewById(R.id.buttonLogin);
        textProfileUserName = rootView.findViewById(R.id.textProfileUsername);
        textProfileEmail = rootView.findViewById(R.id.textProfileEmail);
        textProfilePassword = rootView.findViewById(R.id.textProfilePassword);

        setButtonLoginClick();
        setUsernameClick();
        setEmailClick();
        setPasswordClick();
    }

    public void setButtonLoginClick(){
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //To Login Activity
                Log.i("profile", "button working");
            }
        });
    }

    public void setUsernameClick(){
        textProfileUserName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(getActivity());
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.name_change_dialog);

                TextView editDialogProfileUsername = dialog.findViewById(R.id.editDialogProfileUsername);
                editDialogProfileUsername.setText(textProfileUserName.getText());

                ImageButton imageButtonUsernameConfirm = dialog.findViewById(R.id.imageButtonUsernameConfirm);
                imageButtonUsernameConfirm.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(View view) {
                        //Check Valid
                        Validation valid = checkUsernameValid(editDialogProfileUsername.getText().toString());

                        if(valid.getValid()){
                            //UPDATE DB
                            textProfileUserName.setText(editDialogProfileUsername.getText());
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

    public void setEmailClick(){
        textProfileEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(getActivity());
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.email_change_dialog);

                TextView editDialogProfileEmail = dialog.findViewById(R.id.editDialogProfileEmail);
                editDialogProfileEmail.setText(textProfileEmail.getText());

                ImageButton imageButtonEmailConfirm = dialog.findViewById(R.id.imageButtonEmailConfirm);
                imageButtonEmailConfirm.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(View view) {
                        Validation valid = checkEmailValid(editDialogProfileEmail.getText().toString());

                        if(valid.getValid()){
                            //UPDATE DB
                            textProfileEmail.setText(editDialogProfileEmail.getText());
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

                ImageButton imageButtonPasswordConfirm = dialog.findViewById(R.id.imageButtonPasswordConfirm);
                imageButtonPasswordConfirm.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(View view) {
                        Validation valid = checkPasswordValid(editDialogProfilePassword.getText().toString(), editDialogProfileRePassword.getText().toString());
                        if(valid.getValid()){
                            //UPDATE DB
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

    public void showHide(TextView textView){
        if(textView.getTransformationMethod() instanceof PasswordTransformationMethod){
            textView.setTransformationMethod(null);
        }else{
            textView.setTransformationMethod(new PasswordTransformationMethod());
        }
    }

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