package com.example.rent.searchmovieapp;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.GET;

/**
 * Created by RENT on 2017-03-08.
 */

public interface SearchService {        //wszystko co definiuje nasz serwis do wyszukiwania

    @GET("/")
    Observable<SearchResult> search(@Field("s") String title);
}
