package com.example.noteapp.model;

import java.util.ArrayList;

public class Validation {
    private boolean valid;
    private ArrayList<String> errorMessage;

    //Constructor
    public Validation(){
        valid = false;
        errorMessage = new ArrayList<>();
    }

    //Get boolean valid
    public boolean getValid(){
        return valid;
    }

    //Set boolean valid
    public void setValid(boolean valid){
        this.valid = valid;
    }

    //Get list of all error message
    public ArrayList<String> getErrorMessageList(){
        return errorMessage;
    }

    //Adds error message to list
    public void addErrorMessage(String message){
        this.errorMessage.add(message);
    }
}
