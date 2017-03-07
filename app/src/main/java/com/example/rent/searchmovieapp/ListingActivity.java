package com.example.rent.searchmovieapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;


import com.annimon.stream.Stream;

import nucleus.factory.RequiresPresenter;
import nucleus.view.NucleusAppCompatActivity;
@RequiresPresenter(ListingPresenter.class)
public class ListingActivity extends NucleusAppCompatActivity<ListingPresenter> {

    private static final String SEARCH_TITLE = "search_title";    //do przechowywania wpisanego tekstu

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listing_activity);
        String title = getIntent().getStringExtra(SEARCH_TITLE); //wyciagamy tekst z intentu
        getPresenter().getDataAnsync(title);
    }

    public void setDataOnUiThread(SearchResult result) {
        runOnUiThread(() -> {
            Stream.of(result.getItems()).forEach(movieListingItem -> {
                Log.d("result", "id " + movieListingItem.getImdbID());
            });
        });

    }

    public static Intent createIntent(Context context, String title){  //inny psosób tworzenia intentów!!
        Intent intent = new Intent(context, ListingActivity.class);    //wzorzec gdzie nie potrzebujesz pamietac jakich parametrow potrzebuje !!
        intent.putExtra(SEARCH_TITLE,title);
        return intent;
    }


}
