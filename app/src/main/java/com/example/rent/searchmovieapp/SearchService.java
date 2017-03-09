package com.example.rent.searchmovieapp;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by RENT on 2017-03-08.
 */

public interface SearchService {        //wszystko co definiuje nasz serwis do wyszukiwania
                                        //
    @GET("/")           //tutaj podalibysmy wiecej informacji jestli byloby to potrzebne, np jakis klucz
    Observable<SearchResult> search(@Query("s") String title);
}
