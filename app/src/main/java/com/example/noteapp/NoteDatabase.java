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

    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME = "NoteDatabase.db";
    public static final String DATABASE_NOTE = "notesTable";
    public static final String DATABASE_USER = "userTable";

    //Column name for database notesTable
    public static final String NOTE_ID = "noteID";
    public static final String NOTE_TITLE = "title";
    public static final String NOTE_CONTENT = "content";
    public static final String NOTE_USER_ID = "userID"; //--> Can be used as foreign key referencing DATABASE_USER(USER_ID) --cy

    //Column name for database userTable
    public static final String USER_ID = "userID";
    public static final String USER_EMAIL = "email";
    public static final String USER_NAME = "username";
    public static final String USER_PASSWORD = "password";
    private Context context;

    public NoteDatabase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    //Note Table Query
    private static final String CREATE_NOTE_QUERY = "CREATE TABLE " + DATABASE_NOTE +
            " (" +
            NOTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            NOTE_TITLE + " TEXT NOT NULL, " +
            NOTE_CONTENT + " TEXT, " +
            NOTE_USER_ID + " INTEGER, " +
            "FOREIGN KEY (" + NOTE_USER_ID + ") REFERENCES " + DATABASE_USER + "(" + USER_ID + "));";

    //User Table Query
    private static final String CREATE_USER_QUERY = "CREATE TABLE " + DATABASE_USER +
            " (" +
            USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            USER_EMAIL + " TEXT NOT NULL," +
            USER_NAME + " TEXT," +
            USER_PASSWORD + " TEXT);";


    @Override
    public void onCreate(SQLiteDatabase db) {

        //Create table
        db.execSQL(CREATE_NOTE_QUERY);
        db.execSQL(CREATE_USER_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_NOTE);
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_USER);
        onCreate(db);
    }



    //Function to add data into database
    public long addNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(NOTE_TITLE, note.getTitle());
        cv.put(NOTE_CONTENT, note.getContent());
        cv.put(NOTE_USER_ID, note.getUserID());

        long result = db.insert(NoteDatabase.DATABASE_NOTE, null, cv);
        Log.i("Inserted", "ID: " + result);

        if (result == -1) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Added Successfully", Toast.LENGTH_SHORT).show();
        }

        return result;
    }

    //Function to get specific note according to ID
    public Note getNote(long id) {
        //SELECT * FROM TABLE WHERE ID = 1
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(DATABASE_NOTE, new String[]{NOTE_ID, NOTE_TITLE, NOTE_CONTENT, NOTE_USER_ID},
                NOTE_ID + "=?", new String[]{String.valueOf(id)}, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        return new Note(cursor.getLong(0), cursor.getString(1), cursor.getString(2), cursor.getLong(3));
    }



    //Function to get all notes in the database
    public ArrayList<Note> getAllNotes() {
        ArrayList<Note> allNotes = new ArrayList<>();
        String query = "SELECT * FROM " + DATABASE_NOTE + " ORDER BY " + NOTE_ID + " DESC";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Note note = new Note();
                note.setID(Long.parseLong(cursor.getString(0)));
                note.setTitle(cursor.getString(1));
                note.setContent(cursor.getString(2));
                note.setUserID(Long.parseLong(cursor.getString(3)));
                allNotes.add(note);
            } while (cursor.moveToNext());
        }

        return allNotes;
    }

    public ArrayList<Note> getAllNotes(long userID) {
        ArrayList<Note> allNotes = new ArrayList<>();
        String query = "SELECT * FROM " + DATABASE_NOTE + " WHERE " + NOTE_USER_ID + "='" + userID + "' ORDER BY " + NOTE_ID + " DESC";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Note note = new Note();
                note.setID(Long.parseLong(cursor.getString(0)));
                note.setTitle(cursor.getString(1));
                note.setContent(cursor.getString(2));
                note.setUserID(Long.parseLong(cursor.getString(3)));
                allNotes.add(note);
            } while (cursor.moveToNext());
        }

        return allNotes;
    }


    //Function to update the title and/or content of specific note according to ID
    public int updateNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues c = new ContentValues();
        Log.d("Edited", "Edited Title: -> " + note.getTitle() + "\n ID -> " + note.getID());
        c.put(NOTE_TITLE, note.getTitle());
        c.put(NOTE_CONTENT, note.getContent());
        c.put(NOTE_USER_ID, note.getUserID());
        return db.update(DATABASE_NOTE, c, NOTE_ID + "=?", new String[]{String.valueOf(note.getID())});
    }

    //Function to delete specific note in database according to ID
    public void deleteNote(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DATABASE_NOTE, NOTE_ID + "=?", new String[]{
                String.valueOf(id)
        });
        db.close();
    }


    //Orion-Function to add User into Database
    public long addUser(Users users) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(USER_EMAIL, users.getUserEmail());
        cv.put(USER_NAME, users.getUserName());
        cv.put(USER_PASSWORD, users.getUserPassword());

        long resulted = db.insert(NoteDatabase.DATABASE_USER, null, cv);
        if (resulted == -1) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Added Successfully", Toast.LENGTH_SHORT).show();
        }

        return resulted;
    }


    //Function to get all notes in the database
//    public ArrayList<Users> getAllUser() {
//        ArrayList<Users> allUsers = new ArrayList<>();
//        String query = "SELECT * FROM " + DATABASE_USER + " ORDER BY " + USER_ID + " DESC";
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery(query, null);
//        if (cursor.moveToFirst()) {
//            do {
////                Users user = new Users();
//                user.setUserID(Long.parseLong(cursor.getString(0)));
//                user.setUserEmail(cursor.getString(1));
//                user.setUserName(cursor.getString(2));
//                user.setUserPassword(cursor.getString(3));
//                allUsers.add(user);
//            } while (cursor.moveToNext());
//        }
//
//        return allUsers;
//
//    }

    //Function to get specific note according to ID
    public Users getUser(long id) {
        //SELECT * FROM TABLE WHERE ID = 1
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(DATABASE_USER, new String[]{USER_ID, USER_EMAIL, USER_NAME, USER_PASSWORD},
                USER_ID + "=?", new String[]{String.valueOf(id)}, null, null, null);

        if (cursor.getCount()>0) {
            cursor.moveToFirst();
            return new Users(cursor.getLong(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));
        }else{
            return new Users(0, "Username", "Email", "Password");
        }
    }

    //Overload to get id --cy
    public Users getUser(String email){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + DATABASE_USER + " WHERE " + USER_EMAIL + "='" + email + "'";
        Cursor result = db.rawQuery(query, null);
        if(result.getCount() > 0){
            result.moveToFirst();
        }
        return new Users(result.getLong(0), result.getString(1), result.getString(2), result.getString(3));
    }

    public int updateUser(Users user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues c = new ContentValues();

        c.put(USER_EMAIL, user.getUserEmail());
        c.put(USER_NAME, user.getUserName());
        c.put(USER_PASSWORD, user.getUserPassword());
        return db.update(DATABASE_USER, c, USER_ID + "=?", new String[]{String.valueOf(user.getUserID())});
    }

    //Check for duplicate emails -cy
    public boolean checkEmailExists(Users user) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + DATABASE_USER +
                " WHERE " + USER_EMAIL + "='" + user.getUserEmail() + "'";
        Cursor result = db.rawQuery(query, null);
        if(result.getCount() > 0){
            return true;
        }else{
            return false;
        }
    }

    //check for duplicate username -cy
    public boolean checkUsernameExists(Users user) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + DATABASE_USER +
                " WHERE " + USER_NAME + "='" + user.getUserName() + "'";
        Cursor result = db.rawQuery(query, null);
        if(result.getCount() > 0){
            return true;
        }else{
            return false;
        }
    }


    //check username & password - orion

    public Boolean checkUsernamePasswordEmail(Users user){
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + DATABASE_USER + " WHERE "+ USER_NAME + "='" + user.getUserName() + "'" +
                " AND " + USER_PASSWORD + "='" + user.getUserPassword() + "'" + " AND " + USER_EMAIL + "='" + user.getUserEmail() + "'";
        Cursor result = db.rawQuery(query, null);
        if(result.getCount() > 0){
            return true;
        }else
        {
            return false;
        }
    }

    //FOR TESTING
    public Note getNoteTest(String title, String content, long userID){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM "+ DATABASE_NOTE +" WHERE "+
                NOTE_TITLE +"='"+ title +"' AND "+
                NOTE_CONTENT +"='" +content+ "' AND "+
                NOTE_USER_ID +"='" +userID+ "';";
        Cursor result = db.rawQuery(query, null);
        if(result.getCount()>0){
            result.moveToFirst();
            return new Note(result.getLong(0), result.getString(1), result.getString(2), result.getLong(3));
        }else{
            return new Note(0, "Title Not Added", "Content Not Added", 0);
        }
    }

}
