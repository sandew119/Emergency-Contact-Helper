package com.example.emergencycontacthelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "emergency.db";
    private static final int VERSION = 3; // Incremented version to add is_emergency column

    public static final String TABLE_CONTACTS = "contacts";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_USER_ID = "user_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_PHONE = "phone";
    public static final String COLUMN_NOTE = "note";
    public static final String COLUMN_IS_EMERGENCY = "is_emergency";

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
                "CREATE TABLE " + TABLE_CONTACTS + "(" +
                        COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        COLUMN_USER_ID + " INTEGER," +
                        COLUMN_NAME + " TEXT," +
                        COLUMN_PHONE + " TEXT," +
                        COLUMN_NOTE + " TEXT," +
                        COLUMN_IS_EMERGENCY + " INTEGER DEFAULT 0)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        if (oldVersion < 3) {
            db.execSQL("ALTER TABLE " + TABLE_CONTACTS + " ADD COLUMN " + COLUMN_IS_EMERGENCY + " INTEGER DEFAULT 0");
        }
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
    public long addContact(long userId, String name, String phone, String note, int isEmergency){

        SQLiteDatabase db = getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COLUMN_USER_ID, userId);
        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_PHONE, phone);
        cv.put(COLUMN_NOTE, note);
        cv.put(COLUMN_IS_EMERGENCY, isEmergency);

        return db.insert(TABLE_CONTACTS, null, cv);
    }

    // Get User Contacts
    public Cursor getUserContacts(long userId){

        SQLiteDatabase db = getReadableDatabase();

        return db.query(
                TABLE_CONTACTS,
                null,
                COLUMN_USER_ID + "=?",
                new String[]{String.valueOf(userId)},
                null,
                null,
                COLUMN_NAME + " ASC"
        );
    }

    // Get Emergency Contacts only
    public Cursor getQuickDialContacts(long userId){
        SQLiteDatabase db = getReadableDatabase();
        return db.query(
                TABLE_CONTACTS,
                null,
                COLUMN_USER_ID + "=? AND " + COLUMN_IS_EMERGENCY + "=1",
                new String[]{String.valueOf(userId)},
                null,
                null,
                COLUMN_NAME + " ASC"
        );
    }

    // Get Contact by ID
    public Cursor getContactById(long contactId){

        SQLiteDatabase db = getReadableDatabase();

        return db.query(
                TABLE_CONTACTS,
                null,
                COLUMN_ID + "=?",
                new String[]{String.valueOf(contactId)},
                null,
                null,
                null
        );
    }

    // Update Contact
    public int updateContact(long contactId, String name, String phone, String note, int isEmergency){

        SQLiteDatabase db = getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_PHONE, phone);
        cv.put(COLUMN_NOTE, note);
        cv.put(COLUMN_IS_EMERGENCY, isEmergency);

        return db.update(
                TABLE_CONTACTS,
                cv,
                COLUMN_ID + "=?",
                new String[]{String.valueOf(contactId)}
        );
    }

    // Delete Contact
    public int deleteContact(long contactId){

        SQLiteDatabase db = getWritableDatabase();

        return db.delete(
                TABLE_CONTACTS,
                COLUMN_ID + "=?",
                new String[]{String.valueOf(contactId)}
        );
    }
}