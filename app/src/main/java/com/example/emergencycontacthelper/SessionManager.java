package com.example.emergencycontacthelper;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    private static final String PREF_NAME = "EmergencySession";
    private static final String KEY_LOGGED = "logged";
    private static final String KEY_USER_ID = "userId";

    public SessionManager(Context context) {

        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = prefs.edit();
    }

    public void createLoginSession(long userId) {

        editor.putBoolean(KEY_LOGGED, true);
        editor.putLong(KEY_USER_ID, userId);
        editor.apply();
    }

    public long getUserId() {
        return prefs.getLong(KEY_USER_ID, -1);
    }

    public boolean isLoggedIn() {
        return prefs.getBoolean(KEY_LOGGED, false);
    }

    public void logout() {
        editor.clear().apply();
    }
}