package com.example.noteapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class NoteDatabase extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "NoteDatabase.db";
    public static final String DATABASE_NOTE = "notesTable";
    private static final String DATABASE_USER = "userTable";

    //Column name for database notesTable
    public static final String NOTE_ID = "noteID";
    public static final String NOTE_TITLE = "title";
    public static final String NOTE_CONTENT = "content";

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

    //Note Table Query
    private static final String CREATE_NOTE_QUERY = "CREATE TABLE " + DATABASE_NOTE +
            " ("+
            NOTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            NOTE_TITLE  + " TEXT NOT NULL," +
            NOTE_CONTENT + " TEXT);";

    //User Table Query
    private static final String CREATE_USER_QUERY = "CREATE TABLE " + DATABASE_USER +
            "("+
            USER_ID + " INT PRIMARY KEY AUTOINCREMENT, " +
            USER_EMAIL + " TEXT," +
            USER_NAME + " TEXT," +
            USER_PASSWORD + " TEXT," +
            USER_NOTE_ID + " INT REFERENCES " + DATABASE_NOTE + "(" + NOTE_ID + ")" +

            ")";

    @Override
    public void onCreate(SQLiteDatabase db) {

        //Create table
        db.execSQL(CREATE_NOTE_QUERY);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_NAME);
        onCreate(db);
    }

//    //Add data into NoteTable ORION'S CODE
//    public void addNote (String title, String content){
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues cv = new ContentValues();
//
//        cv.put(NOTE_TITLE, title);
//        cv.put(NOTE_CONTENT, content);
//
//        long result = db.insert(NoteDatabase.DATABASE_NOTE, null, cv);
//        if(result == -1 ){
//            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
//        } else{
//            Toast.makeText(context, "Added Successfully", Toast.LENGTH_SHORT).show();
//        }
//    }

    //Function to add data into database
    public long addNote (Note note){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(NOTE_TITLE, note.getTitle());
        cv.put(NOTE_CONTENT, note.getContent());

        long result = db.insert(NoteDatabase.DATABASE_NOTE, null, cv);
        Log.i("Inserted", "ID: " + result);

        if(result == -1 ){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        } else{
            Toast.makeText(context, "Added Successfully", Toast.LENGTH_SHORT).show();
        }

        return result;
    }

    //Function to get specific note according to ID
    public Note getNote(long id){
        //SELECT * FROM TABLE WHERE ID = 1
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(DATABASE_NOTE, new String[]{NOTE_ID, NOTE_TITLE, NOTE_CONTENT},
                NOTE_ID+"=?", new String[]{String.valueOf(id)}, null, null, null);

        if(cursor != null){
            cursor.moveToFirst();
        }

        return new Note(cursor.getLong(0), cursor.getString(1), cursor.getString(2));
    }

//    //Get all notes ORION'S CODE
//    public Cursor readAllData(){
//        String query = "SELECT * FROM " + NoteDatabase.DATABASE_NOTE;
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        Cursor cursor = null;
//        if(db != null){
//            cursor = db.rawQuery(query, null);
//        }
//        return cursor;
//    }

    //Function to get all notes in the database
    public ArrayList<Note> getAllNotes(){
        ArrayList<Note> allNotes = new ArrayList<>();
        String query = "SELECT * FROM " + DATABASE_NOTE+" ORDER BY "+NOTE_ID+" DESC";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        if(cursor.moveToFirst()){
            do{
                Note note = new Note();
                note.setID(Long.parseLong(cursor.getString(0)));
                note.setTitle(cursor.getString(1));
                note.setContent(cursor.getString(2));
                allNotes.add(note);
            }while (cursor.moveToNext());
        }

        return allNotes;

    }

    //Function to update the title and/or content of specific note according to ID
    public int updateNote(Note note){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues c = new ContentValues();
        Log.d("Edited", "Edited Title: -> "+ note.getTitle() + "\n ID -> "+note.getID());
        c.put(NOTE_TITLE,note.getTitle());
        c.put(NOTE_CONTENT,note.getContent());
        return db.update(DATABASE_NOTE, c,NOTE_ID+"=?",new String[]{String.valueOf(note.getID())});
    }

    //Function to delete specific note in database according to ID
    public void deleteNote(long id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DATABASE_NOTE,NOTE_ID+"=?",new String[]{
                String.valueOf(id)
        });
        db.close();
    }

}
