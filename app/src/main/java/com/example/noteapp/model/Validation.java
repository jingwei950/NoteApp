package com.example.noteapp.model;

import java.util.ArrayList;

public class Validation {
    private boolean valid;
    private ArrayList<String> errorMessage;

    public Validation(){
        valid = false;
        errorMessage = new ArrayList<>();
    }

    public boolean getValid(){
        return valid;
    }

    public void setValid(boolean valid){
        this.valid = valid;
    }

    public ArrayList<String> getErrorMessageList(){
        return errorMessage;
    }

    public void addErrorMessage(String message){
        this.errorMessage.add(message);
    }

    public void removeErrorMessage(String message){
        this.errorMessage.remove(message);
    }
}
