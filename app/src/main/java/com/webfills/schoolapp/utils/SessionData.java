package com.webfills.schoolapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionData {
    private static final String MyPREFERENCES = "MyPrefs";

    private SharedPreferences sharedpreferences;
    private SharedPreferences.Editor editor;
    private static final SessionData ourInstance = new SessionData();

    public static SessionData I() {
        return ourInstance;
    }

    private SessionData() {
    }

    public String studentId = null;
    public boolean isEditable = false;
    public boolean isDeleteMode = false;

    public void initSharedPref(Context context) {
        sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
    }

    public void saveLogin(boolean isLogin) {
        editor = sharedpreferences.edit();
        editor.putBoolean("key", isLogin);
        editor.apply();
    }

    public boolean isLogin() {
        return sharedpreferences.getBoolean("key", false);
    }
}
