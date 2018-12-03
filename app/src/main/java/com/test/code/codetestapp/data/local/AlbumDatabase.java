package com.test.code.codetestapp.data.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.test.code.codetestapp.data.model.Album;

import java.util.List;

@Dao
public interface AlbumDatabase {

    @Insert
    void insertOnlySingleMovie(Album album);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMultipleAlbum(List<Album> albumList);

    @Query("SELECT * FROM Album WHERE id =:id")
    Album fetchOneAlbumById(int id);

    @Query("SELECT * FROM Album")
    List<Album> fetchOAllAlbum();

    @Update
    void updateAlbum(Album album);

    @Delete
    void deleteAlbum(Album album);

}
