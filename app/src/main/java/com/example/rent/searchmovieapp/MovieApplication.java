package com.example.rent.searchmovieapp;

import android.app.Application;

import com.example.rent.searchmovieapp.dagger.AppComponent;
import com.example.rent.searchmovieapp.dagger.DaggerAppComponent;
import com.facebook.stetho.Stetho;

import retrofit2.Retrofit;

/**
 * Created by RENT on 2017-03-11.
 */

public class MovieApplication extends Application implements RetrofitProvider {
    //z application korzystamy za pomocą metody getApplication

    private Retrofit retrofit;
    private AppComponent appComponent;

    public AppComponent getAppComponent() {
        return appComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Stetho.initializeWithDefaults(this);

//        retrofit = new Retrofit.Builder()
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .addConverterFactory(GsonConverterFactory.create())
//                .baseUrl("http://www.omdbapi.com")
//                .appComponent();

        appComponent = DaggerAppComponent.builder().build();
    }

    @Override
    public Retrofit privideRetrofit() {
        return null;
    }
}
