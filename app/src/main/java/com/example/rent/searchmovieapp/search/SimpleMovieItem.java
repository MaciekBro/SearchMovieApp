package com.example.rent.searchmovieapp.search;

/**
 * Created by RENT on 2017-03-14.
 */

public class SimpleMovieItem {          //klasa pomocnicza zeby mozna bylo kliknac w postery na glownym ekranie.

    private String poster;
    private String imdbID;

    public SimpleMovieItem(String imdbID,String poster) {
        this.poster = poster;
        this.imdbID = imdbID;
    }

    public String getPoster() {
        return poster;
    }

    public String getImdbID() {
        return imdbID;
    }
}
