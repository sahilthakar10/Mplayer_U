package com.example.mplayer_u.VIewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.mplayer_u.Model.Table_Note;
import com.example.mplayer_u.Sqlite.NoteRepository;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {
    private NoteRepository repository;
    private LiveData<List<Table_Note>> allData;

    public NoteViewModel(@NonNull Application application) {
        super(application);
        repository = new NoteRepository(application);

        allData = repository.getAlldata();
    }

    public void insert(Table_Note note){
        repository.insert(note);
    }

    public LiveData<List<Table_Note>> getAllData(){
        return allData;
    }

    public LiveData<List<Table_Note>> getSearchData(String search){
        return repository.getSearchdata(search);
    }

    public LiveData<List<Table_Note>> getFavourites(){
        return repository.getFavourites();
    }

    public void updateflag0(String trackid){

        repository.updateflag0(trackid);
    }

    public void updateflag1(String trackid){

        repository.updateflag1(trackid);
    }
}
