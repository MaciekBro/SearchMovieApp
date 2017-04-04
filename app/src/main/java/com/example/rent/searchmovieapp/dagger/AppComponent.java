package com.example.rent.searchmovieapp.dagger;

import com.example.rent.searchmovieapp.details.DetailsActivity;
import com.example.rent.searchmovieapp.listing.ListingActivity;
import com.example.rent.searchmovieapp.search.SearchActivity;

import dagger.Component;

/**
 * Created by RENT on 2017-04-04.
 */

@Component(modules = NetworkModule.class)
public interface AppComponent {

    void inject(ListingActivity listingActivity);   //te 3 aktywnosci beda korzystały z komponenta
    void inject(DetailsActivity detailsActivity);
    void inject(SearchActivity searchActivity);
}
