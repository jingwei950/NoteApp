package com.example.noteapp;

import java.io.Serializable;

public class Note implements Serializable {
    private long ID;
    private String title;
    private String content;
    private long userID;

    //Constructor
    Note (){}
    //Constructor with noteID
    public Note(long id, String title, String content, long userID){
        this.ID = id;
        this.title = title;
        this.content = content;
        this.userID = userID;
    }

    //Constructor without noteID
    Note(String title, String content, long userID){
        this.title = title;
        this.content = content;
        this.userID = userID;
    }

    //Get and set method for Note
    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getUserID(){
        return userID;
    }

    public void setUserID(long userID){
        this.userID = userID;
    }
}
