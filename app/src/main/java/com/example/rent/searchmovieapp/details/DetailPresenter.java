package com.example.rent.searchmovieapp.details;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.rent.searchmovieapp.RetrofitProvider;

import io.reactivex.Observable;
import nucleus.factory.RequiresPresenter;
import nucleus.presenter.Presenter;
import retrofit2.Retrofit;

/**
 * Created by RENT on 2017-03-13.
 */


public class DetailPresenter extends Presenter<DetailsActivity> {

    private Retrofit retrofit;

    public void setRetrofit(Retrofit retrofit) {
        this.retrofit = retrofit;
    }

//    @Override
//    protected void onCreate(@Nullable Bundle savedState) {
//        super.onCreate(savedState);
//        RetrofitProvider retrofitProvider = (RetrofitProvider) getView().getApplication();  //pobieramy konfiguracje retrofit z application
//        retrofit = retrofitProvider.privideRetrofit(); //zamiast settera!
//    }

    public Observable<MovieItem> loadDetail (String imdbID) {
        DetailService detailService = retrofit.create(DetailService.class);
        return detailService.getDetailInfo(imdbID);
    }
}
