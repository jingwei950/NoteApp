package com.example.noteapp;

import java.io.Serializable;

public class Users implements Serializable {


    //User
    private long userID;
    private String userName;
    private String userPassword;
    private String userEmail;




    public Users(long userID, String userEmail, String userName, String userPassword){
        this.userID = userID;
        this.userEmail = userEmail;
        this.userName = userName;
        this.userPassword = userPassword;
    }

    Users(String email, String userName, String password){
        this.userEmail = email;
        this.userName = userName;
        this.userPassword = password;
    }





    //User get and set

    public long getUserID(){return userID;}

    public void setUserID(long userID){this.userID = userID; }

    public String getUserEmail(){return userEmail; }

    public void setUserEmail(String userEmail){this.userEmail = userEmail;}

    public String getUserName(){return userName;}

    public void setUserName(String userName){this.userName = userName;}

    public String getUserPassword(){return userPassword; }

    public void setUserPassword(String userPassword){this.userPassword = userPassword;}



}
