package com.example.mplayer_u.VIewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mplayer_u.Model.Gson_Note_Data;
import com.example.mplayer_u.remote.Api;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Note_Data_VIewModel extends ViewModel {

    //this is the data that we will fetch asynchronously
    private MutableLiveData<List<Gson_Note_Data>> note_Data;

    //we will call this method to get the data
    public LiveData<List<Gson_Note_Data>> getHeroes() {
        //if the list is null
        if (note_Data == null) {
            note_Data = new MutableLiveData<List<Gson_Note_Data>>();
            //we will load it asynchronously from server in this method

            loadHeroes();
        }

        //finally we will return the list
        return note_Data;
    }


    //This method is using Retrofit to get the JSON data from URL
    private void loadHeroes() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
         Api api = retrofit.create(Api.class);
        Call<List<Gson_Note_Data>> call = api.getHeroes();
          call.enqueue(new Callback<List<Gson_Note_Data>>() {
            @Override
            public void onResponse(Call<List<Gson_Note_Data>> call, Response<List<Gson_Note_Data>> response) {

                note_Data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Gson_Note_Data>> call, Throwable t) {
                Log.e("error" , t.getMessage());
            }
        });
    }
}