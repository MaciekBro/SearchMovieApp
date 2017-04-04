package com.example.rent.searchmovieapp.listing;

import com.example.rent.searchmovieapp.details.MovieItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RENT on 2017-03-15.
 */

public class ResultAggregator {

    private List<MovieListingItem> movieItems = new ArrayList<>();      //to są wszystkie itemy ze wszystkich stron
    private int totalItemsResult;                                       //po to zeby nasz mechanizm ladowania wiedzial jak dlugo ma ladowac strony
    private String response;

    public void setTotalItemsResult(int totalItemsResult) {
        this.totalItemsResult = totalItemsResult;
    }

    public void addNewItems (List<MovieListingItem> newItem){
        movieItems.addAll(newItem);
    }

    public int getTotalItemsResult() {
        return totalItemsResult;
    }

    public List<MovieListingItem> getMovieItems() {
        return movieItems;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
