package com.example.rent.searchmovieapp.details;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by RENT on 2017-03-13.
 */

public interface DetailService {

    @GET("/")
    Observable<MovieItem> getDetailInfo(@Query("i")String id);

}
