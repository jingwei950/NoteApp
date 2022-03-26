package com.example.noteapp;

public class Note {
    private String ID;
    private String title;
    private String content;
//    private String date;
//    private String time;

    Note(){}
    Note(String title, String content){
        this.title = title;
        this.content = content;
    }

//    Note(String id, String title, String content, String date, String time){
//        this.ID = id;
//        this.title = title;
//        this.content = content;
//        this.date = date;
//        this.time = time;
//    }



    public String getID() {
        return ID;
    }

    public void setID(String ID) {
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

//    public String getDate() {
//        return date;
//    }
//
//    public void setDate(String date) {
//        this.date = date;
//    }
//
//    public String getTime() {
//        return time;
//    }
//
//    public void setTime(String time) {
//        this.time = time;
//    }
}
