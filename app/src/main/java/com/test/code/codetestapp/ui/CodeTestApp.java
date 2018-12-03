package com.test.code.codetestapp.ui;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Context;

import com.test.code.codetestapp.data.local.AlbumDatabase;
import com.test.code.codetestapp.data.local.AppDatabase;

public class CodeTestApp extends Application {
    private static final String DATABASE_NAME = "album_db";

    private AppDatabase appDatabase;
    public static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        setUpContext(this);
    }

    public synchronized AppDatabase getAppDatabase(){
        if(appDatabase==null) {
            appDatabase = Room.databaseBuilder(getApplicationContext(),
                    AppDatabase.class, DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return appDatabase;
    }

    public synchronized void setUpContext(Context mContext) {
        CodeTestApp.mContext = getApplicationContext();
    }

    public static Context getContext() {
        return mContext;
    }


}
