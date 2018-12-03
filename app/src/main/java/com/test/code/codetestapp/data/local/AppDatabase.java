package com.test.code.codetestapp.data.local;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.test.code.codetestapp.data.model.Album;

@Database(entities = {Album.class}, version = 1, exportSchema = false)

public abstract class AppDatabase extends RoomDatabase {
    public abstract AlbumDatabase albumDatabase();
}
