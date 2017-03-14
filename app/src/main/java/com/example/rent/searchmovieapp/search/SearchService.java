package com.example.rent.searchmovieapp.search;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by RENT on 2017-03-08.
 */

public interface SearchService {        //wszystko co definiuje nasz serwis do wyszukiwania
                                        //
    @GET("/")           //tutaj podalibysmy wiecej informacji jestli byloby to potrzebne, numberPicker jakis klucz
    Observable<SearchResult> search(@Query("page") int page, @Query("s") String title, @Query("y") String year, @Query ("type") String type);
}
