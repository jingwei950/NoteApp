package com.example.noteapp;

import java.io.Serializable;

public class Note implements Serializable {
    private long ID;
    private String title;
    private String content;

    Note (){};

    public Note(long id, String title, String content){
        this.ID = id;
        this.title = title;
        this.content = content;
    }

    Note(String title, String content){
        this.title = title;
        this.content = content;
    }

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
}
