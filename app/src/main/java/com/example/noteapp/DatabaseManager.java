package com.example.noteapp;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.sql.SQLDataException;


public class DatabaseManager {
    public NoteDatabase dbHelper;
    public Context context;
    public SQLiteDatabase database;

    public DatabaseManager(Context ctx){

        context = ctx;
    }

    public DatabaseManager open() throws SQLDataException{

        dbHelper = new NoteDatabase(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        dbHelper.close();
    }

    //Get data in NoteTable
    public Cursor fetchNote(){
        String [] columns = new String[] {NoteDatabase.NOTE_ID, NoteDatabase.NOTE_CONTENT, NoteDatabase.NOTE_TITLE};
        Cursor cursor = database.query(NoteDatabase.DATABASE_NOTE, columns, null, null, null, null, null);
        if (cursor !=null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

//    //Update data in NoteTable
//    public int updateNote ( String title, String content) {
//        ContentValues cv = new ContentValues();
//        cv.put(NoteDatabase.NOTE_TITLE, title);
//        cv.put(NoteDatabase.NOTE_CONTENT, content);
//        int ret = database.update(NoteDatabase.DATABASE_NOTE, cv, "title = ?", new String[]{title});
//        return ret;
//    }

    //Delete data in NoteTable
    public void deleteNote (long noteID) {
        database.delete(NoteDatabase.DATABASE_NOTE, NoteDatabase.NOTE_ID + "=" + noteID, null);
    }



    





}
