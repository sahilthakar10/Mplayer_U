package com.example.mplayer_u.remote;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.mplayer_u.Model.Table_Note;

import java.util.List;
@Dao
public interface NoteDao {

    @Insert
    void insert(Table_Note note);

    @Query("SELECT * FROM M_Player")
    LiveData<List<Table_Note>> getAllData();

    @Query("SELECT * FROM M_Player where flag=1")
    LiveData<List<Table_Note>> getFavourites();

    @Query("SELECT * FROM M_Player where trackName LIKE :search || '%'")
    LiveData<List<Table_Note>> getSearchedData(String search);

    @Query("UPDATE M_Player SET flag=0 where trackId = :trackid")
    void Updateflag0(String trackid);

    @Query("UPDATE M_Player SET flag=1 where trackId = :trackid")
    void Updateflag1(String trackid);

}
