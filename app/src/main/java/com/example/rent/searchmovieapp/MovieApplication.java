package com.example.rent.searchmovieapp;

import android.app.Application;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by RENT on 2017-03-11.
 */

public class MovieApplication extends Application implements RetrofitProvider {
    //z application korzystamy za pomocą metody getApplication

private Retrofit retrofit;

    @Override
    public void onCreate() {
        super.onCreate();

        retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://www.omdbapi.com")
                .build();
    }

    @Override
    public Retrofit privideRetrofit() {
        return retrofit;
    }
}
