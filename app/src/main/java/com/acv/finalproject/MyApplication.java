package com.acv.finalproject;

import android.app.Application;
import android.content.Context;

import com.acv.finalproject.dao.DBHandler;
import com.acv.finalproject.dao.DatabaseManager;
import com.acv.finalproject.dao.PlayerDAO;
import com.acv.finalproject.model.Player;
import com.facebook.stetho.Stetho;

/**
 * Created by vinicius.castro on 13/03/2017.
 */

public class MyApplication extends Application {

    private static Context context;

    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
        MyApplication.context = getApplicationContext();
        DatabaseManager.initializeInstance(new DBHandler(context));
//        PlayerDAO dao = new PlayerDAO(context);
//        dao.open();
//        dao.deleteTable();
//        dao.close();

    }

    public static Context getAppContext() {
        return MyApplication.context;
    }
}
