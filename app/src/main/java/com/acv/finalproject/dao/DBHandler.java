package com.acv.finalproject.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by vinicius.castro on 15/03/2017.
 */

public class DBHandler extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "DreamTeam.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_CREATE_TUSERS = "CREATE TABLE " + UserDAO.TABLE_NAME + "( " +
            UserDAO.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            UserDAO.COLUMN_NAME + " TEXT, " +
            UserDAO.COLUMN_PASSWORD + " TEXT )";
    private static final String TABLE_CREATE_TTEAM = "CREATE TABLE " + TeamDAO.TABLE_NAME + "( " +
            TeamDAO.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            TeamDAO.COLUMN_NAME + " TEXT, " +
            TeamDAO.COLUMN_EMAIL + " TEXT )";
    private static final String TABLE_CREATE_TPLAYER = "CREATE TABLE " + PlayerDAO.TABLE_NAME + "( " +
            PlayerDAO.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            PlayerDAO.COLUMN_TEAM_ID + " INTEGER, " +
            PlayerDAO.COLUMN_NAME + " TEXT, " +
            PlayerDAO.COLUMN_PHONE + " TEXT, " +
            PlayerDAO.COLUMN_EMAIL + " TEXT, " +
            PlayerDAO.COLUMN_POSITION + " TEXT, " +
            PlayerDAO.COLUMN_NUMBER + " INTEGER )";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        System.out.print("OnCreate UserDBHandler");
        db.execSQL(TABLE_CREATE_TUSERS);
        db.execSQL(TABLE_CREATE_TTEAM);
        db.execSQL(TABLE_CREATE_TPLAYER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        System.out.print("OnUpgrade USerDBHandler");
        db.execSQL("DROP TABLE IF EXISTS " + UserDAO.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TeamDAO.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + PlayerDAO.TABLE_NAME);
        onCreate(db);
    }
}
