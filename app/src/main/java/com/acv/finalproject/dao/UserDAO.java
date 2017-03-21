package com.acv.finalproject.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.acv.finalproject.model.User;

/**
 * Created by vinicius.castro on 08/03/2017.
 */

public class UserDAO {

    private SQLiteDatabase db;
    private SQLiteOpenHelper dbhandler;

    public static final String TABLE_NAME = "TUSERS";
    public static final String COLUMN_ID = "userID";
    public static final String COLUMN_NAME = "username";
    public static final String COLUMN_PASSWORD = "password";

    private static final String[] allColumns = {
            COLUMN_ID,
            COLUMN_NAME,
            COLUMN_PASSWORD
    };

    public UserDAO(Context contextApp) {
        System.out.print("New UserDBHandler");
        this.dbhandler = new DBHandler(contextApp);
    }

    public void open() {
        System.out.print("Open Database UserDAO");
        this.db = this.dbhandler.getWritableDatabase();
    }

    public void close() {
        System.out.print("Closing Database UserDAO");
        this.dbhandler.close();
    }

    public User insert(User user) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_PASSWORD, user.getPassword());
        values.put(COLUMN_NAME, user.getUsername());
//        this.db = DatabaseManager.getInstance().openDatabase(false);
        System.out.print("Insert UserDAO");
        long insertID = this.db.insert(TABLE_NAME, null, values);
        System.out.print("ID user inserted:" + insertID);
//        DatabaseManager.getInstance().closeDatabase();
        user.setId(insertID);
        return user;
    }

    public User select(String username, String password) {
//        this.db = DatabaseManager.getInstance().openDatabase(false);
        User user;
        System.out.print("Select User");
        Cursor cursor = this.db.query(TABLE_NAME, allColumns, COLUMN_NAME
                + "= ? AND " + COLUMN_PASSWORD + "= ? ", new String[]{username, password}, null, null, null);
//        DatabaseManager.getInstance().closeDatabase();
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            user = new User(Long.parseLong(cursor.getString(0)), cursor.getString(1), cursor.getString(2));
            return user;
        }
        return null;
    }

    public void deleteAll() {
//        this.db = DatabaseManager.getInstance().openDatabase(false);
        this.db.delete(TABLE_NAME, null, null);
//        DatabaseManager.getInstance().closeDatabase();
    }

}
