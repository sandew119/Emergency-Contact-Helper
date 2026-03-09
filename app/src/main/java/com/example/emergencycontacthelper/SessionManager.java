package com.example.emergencycontacthelper;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    private static final String PREF_NAME = "user_session";
    private static final String KEY_USER_ID = "user_id";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Context context;

    public SessionManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    // Save login session
    public void createLoginSession(long userId) {
        editor.putLong(KEY_USER_ID, userId);
        editor.apply();
    }

    // Get logged-in user ID
    public long getUserId() {
        return sharedPreferences.getLong(KEY_USER_ID, -1);
    }

    // Logout user
    public void logout() {
        editor.clear();
        editor.apply();
    }

    // Check if user is logged in
    public boolean isLoggedIn() {
        return getUserId() != -1;
    }
}