package com.example.noteapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class NoteDatabase extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "NoteDatabase.db";
    private static final String DATABASE_NOTE = "notesTable";
    private static final String DATABASE_USER = "userTable";

    //Column name for database notesTable
    private static final String NOTE_ID = "noteID";
    private static final String NOTE_TITLE = "title";
    private static final String NOTE_CONTENT = "content";

    //Column name for database userTable
    private static final String USER_ID = "userID";
    private static final String USER_EMAIL = "email";
    private static final String USER_NAME = "username";
    private static final String USER_PASSWORD = "password";
    private static final String USER_NOTE_ID = "noteID";
    private Context context;

    public NoteDatabase(@Nullable Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //Create table
        String query = "CREATE TABLE " + DATABASE_NOTE +
                " ("+
                NOTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                NOTE_TITLE  + " TEXT," +
                NOTE_CONTENT + " TEXT);";

        db.execSQL(query);

//        String user = "CREATE TABLE " + DATABASE_USER +
//                "("+
//                    USER_ID + " INT PRIMARY KEY AUTOINCREMENT, " +
//                    USER_EMAIL + " TEXT," +
//                    USER_NAME + " TEXT," +
//                    USER_PASSWORD + " TEXT," +
//                    USER_NOTE_ID + " INT REFERENCES " + DATABASE_NOTE + "(" + NOTE_ID + ")" +
//
//                ")";
//
//        db.execSQL(user);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_NAME);
        onCreate(db);
    }

    void addNote (String title, String content){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(NOTE_TITLE, title);
        cv.put(NOTE_CONTENT, content);
        long result = db.insert(DATABASE_NOTE, null, cv);
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
        }

    }

    void updateNote (String id, String title, String content){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(NOTE_ID, id);
        cv.put(NOTE_TITLE, title);
        cv.put(NOTE_CONTENT, content);
    }

}
