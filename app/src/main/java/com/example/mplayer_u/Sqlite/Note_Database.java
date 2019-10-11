package com.example.mplayer_u.Sqlite;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.mplayer_u.Model.Table_Note;
import com.example.mplayer_u.remote.NoteDao;

@Database(entities = {Table_Note.class} , version = 8, exportSchema = false)
public abstract class Note_Database extends RoomDatabase {

    private static Note_Database instance;
    public abstract NoteDao noteDao();

    public static synchronized Note_Database getInstance(Context context){

        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    Note_Database.class , "Mplayer")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallBack)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
        }
    };


}
