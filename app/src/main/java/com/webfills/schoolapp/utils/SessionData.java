package com.webfills.schoolapp.utils;

public class SessionData {
    private static final SessionData ourInstance = new SessionData();
    public static SessionData I() {
        return ourInstance;
    }

    private SessionData() {
    }

    public String studentId = null;
    public boolean isEditable = false;
    public boolean isDeleteMode = false;
}
