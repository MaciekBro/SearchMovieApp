package com.example.rent.searchmovieapp.search;

import com.example.rent.searchmovieapp.listing.MovieListingItem;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by RENT on 2017-03-07.
 */

public class SearchResult {

    @SerializedName("Search")
    private List<MovieListingItem> items;
    private String totalResults;
    @SerializedName("Response")
    private String response;

    public List<MovieListingItem> getItems() {
        return items;
    }

    public String getTotalResults() {
        return totalResults;
    }

    public String getResponse() {
        return response;
    }
}
