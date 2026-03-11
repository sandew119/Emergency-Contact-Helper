package com.example.emergencycontacthelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "emergency.db";
    private static final int VERSION = 2;

    public DatabaseHelper(Context context){
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(
                "CREATE TABLE users(" +
                        "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "username TEXT UNIQUE," +
                        "email TEXT UNIQUE," +
                        "password TEXT)"
        );

        db.execSQL(
                "CREATE TABLE contacts(" +
                        "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "user_id INTEGER," +
                        "name TEXT," +
                        "phone TEXT," +
                        "note TEXT)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS contacts");
        onCreate(db);
    }

    // Register User
    public boolean registerUser(String username, String email, String password){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("username", username);
        cv.put("email", email);
        cv.put("password", PasswordUtils.hashPassword(password));

        long result = db.insert("users", null, cv);

        return result != -1;
    }

    // Login User
    public long loginUser(String username, String password){

        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(
                "users",
                new String[]{"_id"},
                "username=? AND password=?",
                new String[]{username, PasswordUtils.hashPassword(password)},
                null,
                null,
                null
        );

        long id = -1;

        if(cursor.moveToFirst()){
            id = cursor.getLong(0);
        }

        cursor.close();

        return id;
    }

    // Add Contact
    public long addContact(long userId, String name, String phone, String note){

        SQLiteDatabase db = getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("user_id", userId);
        cv.put("name", name);
        cv.put("phone", phone);
        cv.put("note", note);

        return db.insert("contacts", null, cv);
    }

    // Get User Contacts
    public Cursor getUserContacts(long userId){

        SQLiteDatabase db = getReadableDatabase();

        return db.query(
                "contacts",
                null,
                "user_id=?",
                new String[]{String.valueOf(userId)},
                null,
                null,
                "name ASC"
        );
    }

    // Get Contact by ID
    public Cursor getContactById(long contactId){

        SQLiteDatabase db = getReadableDatabase();

        return db.query(
                "contacts",
                null,
                "_id=?",
                new String[]{String.valueOf(contactId)},
                null,
                null,
                null
        );
    }

    // Update Contact
    public int updateContact(long contactId, String name, String phone, String note){

        SQLiteDatabase db = getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("phone", phone);
        cv.put("note", note);

        return db.update(
                "contacts",
                cv,
                "_id=?",
                new String[]{String.valueOf(contactId)}
        );
    }

    // Delete Contact
    public int deleteContact(long contactId){

        SQLiteDatabase db = getWritableDatabase();

        return db.delete(
                "contacts",
                "_id=?",
                new String[]{String.valueOf(contactId)}
        );
    }
}