package com.example.rent.searchmovieapp;

import retrofit2.Retrofit;

/**
 * Created by RENT on 2017-03-11.
 */

public interface RetrofitProvider {
    //udostepnia konfiguracje Retrofita
    //bo chcielismy jej uzywac wiecej razy

    Retrofit privideRetrofit();

}
