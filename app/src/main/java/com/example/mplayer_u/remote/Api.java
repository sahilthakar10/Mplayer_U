package com.example.mplayer_u.remote;


import com.example.mplayer_u.Model.Gson_Note_Data;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Api {

    String BASE_URL = "https://knkcab.com/";

    @GET("2.txt")
    Call<List<Gson_Note_Data>> getHeroes();
}
