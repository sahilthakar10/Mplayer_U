package com.example.mplayer_u.Sqlite;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.mplayer_u.Model.Table_Note;
import com.example.mplayer_u.remote.NoteDao;

import java.util.List;

public class NoteRepository {

    private NoteDao noteDao;
    private LiveData<List<Table_Note>> alldata;

    public NoteRepository(Application application)
    {
        Note_Database database = Note_Database.getInstance(application);
        noteDao = database.noteDao();

        alldata = noteDao.getAllData();

    }

    public void insert(Table_Note note){
        new InsertNoteAsyncTask(noteDao).execute(note);
    }

    public LiveData<List<Table_Note>> getAlldata() {
        return alldata;
    }


    public LiveData<List<Table_Note>> getSearchdata(String search) {
        return noteDao.getSearchedData(search);
    }

    public LiveData<List<Table_Note>> getFavourites() {
        return noteDao.getFavourites();
    }

    public void updateflag0(String trackid){

        new UpdateFlag0(noteDao).execute(trackid);


    }

    public void updateflag1(String trackid){
        new UpdateFlag1(noteDao).execute(trackid);

    }

    private static class InsertNoteAsyncTask extends AsyncTask<Table_Note, Void , Void> {
            private NoteDao noteDao;

            private InsertNoteAsyncTask(NoteDao noteDao)
            {
                this.noteDao = noteDao;
            }


            @Override
            protected Void doInBackground(Table_Note... notes) {

                try {
                    noteDao.insert(notes[0]);

                }catch (Exception e)
                {

                }
                return null;
            }
    }


    private static class UpdateFlag0 extends AsyncTask<String , String , String> {
        private NoteDao noteDao;

        private UpdateFlag0(NoteDao noteDao)
        {
            this.noteDao = noteDao;
        }


        @Override
        protected String doInBackground(String... strings) {

            try {
              noteDao.Updateflag0(strings[0]);

            }catch (Exception e)
            {

            }
            return null;
        }
    }

    private static class UpdateFlag1 extends AsyncTask<String , String , String> {
        private NoteDao noteDao;

        private UpdateFlag1(NoteDao noteDao)
        {
            this.noteDao = noteDao;
        }


        @Override
        protected String doInBackground(String... strings) {

            try {
                noteDao.Updateflag1(strings[0]);

            }catch (Exception e)
            {

            }
            return null;
        }
    }
}
