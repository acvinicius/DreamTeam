package com.acv.finalproject.dao;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by vinicius.castro on 12/12/2016.
 */


public class DatabaseManager {
    Integer mOpenCounter = 0;

    private static DatabaseManager instance;
    private static SQLiteOpenHelper mDatabaseHelper;
    private SQLiteDatabase mDatabase;

    public static synchronized void initializeInstance(SQLiteOpenHelper helper) {
        if (instance == null) {
            System.out.print("Initializing Databate");
            instance = new DatabaseManager();
            mDatabaseHelper = helper;
        }
    }

    public static synchronized DatabaseManager getInstance() {
        if (instance == null) {
            throw new IllegalStateException(DatabaseManager.class.getSimpleName() +
                    " is not initialized, call initializeInstance(..) method first.");
        }

        return instance;
    }

    public synchronized SQLiteDatabase openDatabase(boolean onReadable) {
        mOpenCounter += 1;
        if (mOpenCounter == 1) {
            if (onReadable) {
                System.out.print("Readable Database");
                mDatabase = mDatabaseHelper.getReadableDatabase();
            } else {
                System.out.print("Writable Database");
                mDatabase = mDatabaseHelper.getWritableDatabase();
            }
        }
        return mDatabase;
    }

    public synchronized void closeDatabase() {
        mOpenCounter -= 1;
        if (mOpenCounter == 0) {
            // Closing database
            System.out.print("Closing Database");
            mDatabase.close();

        }
    }
}