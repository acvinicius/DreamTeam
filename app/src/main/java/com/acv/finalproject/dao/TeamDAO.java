package com.acv.finalproject.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.acv.finalproject.model.Team;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vinicius.castro on 14/03/2017.
 */

public class TeamDAO {

    private SQLiteDatabase db;
    private SQLiteOpenHelper dbhandler;
    public static final String TABLE_NAME = "TTEAM";
    public static final String COLUMN_ID = "teamId";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_EMAIL = "email";
    private static final String[] allColumns = {
            COLUMN_ID,
            COLUMN_NAME,
            COLUMN_EMAIL
    };

    public TeamDAO(Context contextApp) {
        this.dbhandler = new DBHandler(contextApp);
    }

    public void open() {
        this.db = this.dbhandler.getWritableDatabase();
    }

    public void close() {
        this.dbhandler.close();
    }

    public Team insert(Team team) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, team.getName());
        values.put(COLUMN_EMAIL, team.getEmail());
        long insertID = this.db.insert(TABLE_NAME, null, values);
        team.setId(insertID);
        return team;
    }

    public Team select(long id) {
        Cursor cursor = db.query(TABLE_NAME, allColumns, COLUMN_ID
                + "= ? ", new String[]{String.valueOf(id)}, null, null, null);
        Team team;
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            team = new Team(Long.parseLong(cursor.getString(0)), cursor.getString(1), null, cursor.getString(2), null);
            return team;
        }
        return null;
    }

    public List<Team> selectAll() {
        Cursor cursor = db.query(TABLE_NAME, allColumns, null, null, null, null, null);
        List<Team> teams = new ArrayList<>();
        Team team;
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                team = new Team(Long.parseLong(cursor.getString(0)), cursor.getString(1), null, cursor.getString(2), null);
                teams.add(team);
                cursor.moveToNext();
            }
            return teams;
        }
        return null;
    }

    public int update(Team team) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, team.getName());
        values.put(COLUMN_EMAIL, team.getEmail());
        return db.update(TABLE_NAME, values, COLUMN_ID + "= ? ", new String[]{String.valueOf(team.getId())});
    }

    public void delete(long teamID) {
        db.delete(TABLE_NAME, COLUMN_ID + "= ? ", new String[]{String.valueOf(teamID)});
    }

    public void deleteAll() {
        this.db.delete(TABLE_NAME, null, null);
    }


}
