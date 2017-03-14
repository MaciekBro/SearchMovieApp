package com.example.rent.searchmovieapp.details;

import com.annimon.stream.Objects;
import com.google.gson.annotations.SerializedName;


/**
 * Created by RENT on 2017-03-13.
 */

public class MovieItem {

    @SerializedName("Title")
    private String title;

    @SerializedName("Year")
    private String year;

    @SerializedName("Runtime")
    private String runtime;

    @SerializedName("Director")
    private String director;

    @SerializedName("Actors")
    private String actors;

    @SerializedName("Plot")
    private String plot;

    @SerializedName("Awards")
    private String awards;

    @SerializedName("Poster")
    private String poster;

    private String imdbRating;

    @SerializedName("Type")
    private String type;

    private String imdbID;



    public String getTitle() {
        return title;
    }

    public String getYear() {
        return year;
    }

    public String getRuntime() {
        return runtime;
    }

    public String getDirector() {
        return director;
    }

    public String getActors() {
        return actors;
    }

    public String getPlot() {
        return plot;
    }

    public String getAwards() {
        return awards;
    }

    public String getPoster() {
        return poster;
    }

    public String getImdbRating() {
        return imdbRating;
    }

    public String getType() {
        return type;
    }

    public String getImdbID() {
        return imdbID;
    }

    @Override
    public boolean equals(Object o) {           // hashCode słuzy do porównywania obiektów, jesli sa rowne to znaczy ze to sa te same obiekty!
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovieItem movieItem = (MovieItem) o;
        return Objects.equals(title, movieItem.title) &&
                Objects.equals(year, movieItem.year) &&
                Objects.equals(runtime, movieItem.runtime) &&
                Objects.equals(director, movieItem.director) &&
                Objects.equals(actors, movieItem.actors) &&
                Objects.equals(plot, movieItem.plot) &&
                Objects.equals(awards, movieItem.awards) &&
                Objects.equals(poster, movieItem.poster) &&
                Objects.equals(imdbRating, movieItem.imdbRating) &&
                Objects.equals(type, movieItem.type) &&
                Objects.equals(imdbID, movieItem.imdbID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, year, runtime, director, actors, plot, awards, poster, imdbRating, type, imdbID);
    }
}
