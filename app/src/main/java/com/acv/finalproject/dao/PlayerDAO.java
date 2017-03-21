package com.acv.finalproject.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.acv.finalproject.model.Player;
import com.acv.finalproject.model.Team;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vinicius.castro on 14/03/2017.
 */

public class PlayerDAO {

    private SQLiteDatabase db;
    private SQLiteOpenHelper dbhandler;
    public static final String TABLE_NAME = "TPLAYER";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TEAM_ID = "teamID";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PHONE = "phone";
    public static final String COLUMN_POSITION = "position";
    public static final String COLUMN_NUMBER = "number";
    private static final String[] allColumns = {
            COLUMN_ID,
            COLUMN_TEAM_ID,
            COLUMN_NAME,
            COLUMN_PHONE,
            COLUMN_EMAIL,
            COLUMN_POSITION,
            COLUMN_NUMBER
    };

    public PlayerDAO(Context contextApp) {
        this.dbhandler = new DBHandler(contextApp);
    }

    public void open() {
        this.db = this.dbhandler.getWritableDatabase();
    }

    public void close() {
        this.dbhandler.close();
    }

    public Player insert(Player player) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, player.getName());
        values.put(COLUMN_EMAIL, player.getEmail());
        values.put(COLUMN_PHONE, player.getPhone());
        values.put(COLUMN_POSITION, player.getPosition());
        values.put(COLUMN_NUMBER, player.getNumber());
        values.put(COLUMN_TEAM_ID, player.getTeamID());
        long insertID = this.db.insert(TABLE_NAME, null, values);
        player.setId(insertID);
        return player;
    }

    public List<Player> selectAll(long id) {
        Cursor cursor = db.query(TABLE_NAME, allColumns, COLUMN_TEAM_ID
                + "= ? ", new String[]{String.valueOf(id)}, null, null, null);
        List<Player> players = new ArrayList<>();
        Player player;
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                player = new Player(Long.parseLong(cursor.getString(0)), Long.parseLong(cursor.getString(1)), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), Integer.parseInt(cursor.getString(6)));
                players.add(player);
                cursor.moveToNext();
            }
            return players;
        }
        return null;
    }

    public int update(Player player) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, player.getName());
        values.put(COLUMN_EMAIL, player.getEmail());
        values.put(COLUMN_PHONE, player.getPhone());
        values.put(COLUMN_POSITION, player.getPosition());
        values.put(COLUMN_NUMBER, player.getNumber());
        return this.db.update(TABLE_NAME, values, COLUMN_ID + "= ? ", new String[]{String.valueOf(player.getId())});
    }

    public void delete(long playerID) {
        db.delete(TABLE_NAME, COLUMN_ID + "= ? ", new String[]{String.valueOf(playerID)});
    }

    public void deleteAll() {
        this.db.delete(TABLE_NAME, null, null);
    }

    private static final String TABLE_CREATE_TPLAYER = "CREATE TABLE " + PlayerDAO.TABLE_NAME + "( " +
            PlayerDAO.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            PlayerDAO.COLUMN_TEAM_ID + " INTEGER, " +
            PlayerDAO.COLUMN_NAME + " TEXT, " +
            PlayerDAO.COLUMN_PHONE + " TEXT, " +
            PlayerDAO.COLUMN_EMAIL + " TEXT, " +
            PlayerDAO.COLUMN_POSITION + " TEXT, " +
            PlayerDAO.COLUMN_NUMBER + " INTEGER )";

    public void deleteTable() {
        db.execSQL("DROP TABLE IF EXISTS " + PlayerDAO.TABLE_NAME);
        db.execSQL(TABLE_CREATE_TPLAYER);
    }
}
