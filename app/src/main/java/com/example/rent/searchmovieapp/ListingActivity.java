package com.example.rent.searchmovieapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.ViewFlipper;


import com.annimon.stream.Stream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import nucleus.factory.RequiresPresenter;
import nucleus.view.NucleusAppCompatActivity;

import static io.reactivex.android.schedulers.AndroidSchedulers.mainThread;
import static io.reactivex.schedulers.Schedulers.io;


@RequiresPresenter(ListingPresenter.class)
public class ListingActivity extends NucleusAppCompatActivity<ListingPresenter> {

    private static final String SEARCH_TITLE = "search_title";    //do przechowywania wpisanego tekstu
    private MoviesListAdapter adapter;
    private static final String SEARCH_YEAR = "search_year";
    public static final int NO_YEAR_SELECTED = -1;
    public static final String SEARCH_TYPE  = "search_type";

    @BindView(R.id.view_flipper)
    ViewFlipper viewFlipper;

    @BindView(R.id.no_internet_image_view)
    ImageView noInternetImage;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.no_result)
    FrameLayout noResultLayout;

//    private ViewFlipper viewFlipper;      //butterknife nie dziala na polach prywatnych
//    private ImageView noInternetImage;
//    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listing_activity);
        String title = getIntent().getStringExtra(SEARCH_TITLE); //wyciagamy tekst z intentu

        ButterKnife.bind(this); //binduje nasze powiazania nie potrzebujemy ich pozniej

        int year = getIntent().getIntExtra(SEARCH_YEAR, NO_YEAR_SELECTED);
        String type = getIntent().getStringExtra(SEARCH_TYPE);

//        viewFlipper = (ViewFlipper) findViewById(R.id.view_flipper);
//        noInternetImage = (ImageView) findViewById(R.id.no_internet_image_view);
//        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);


        adapter = new MoviesListAdapter();
        recyclerView.setAdapter(adapter);

        getPresenter().getDataAnsync(title,year , type)   //getPresenter zwraca prezentaera którego wczesniej definiowalismy
                .subscribeOn(io())       //to co jest  powyżej jest wykonane w innym wątku
                .observeOn(mainThread())  //to co bedzie wykonywane w głównym wątku
                .subscribe(this::success, this::error);     //pierwszy metr jest pozytywny, drugi odpowiada za bledy

        //subscibeOn uzywamy tylko raz
        //observeOn uzywalmy do wielokrotnego przełączania wątków




//        getPresenter().getDataAnsync(title)
//                .subscribeOn(Schedulers.io())       //to co jest  powyżej jest wykonane w innym wątku
//                .observeOn(AndroidSchedulers.mainThread())  //to co bedzie wykonywane w głównym wątku
//                .subscribe(new Consumer<SearchResult>() {
//
//                    @Override
//                    public void accept(SearchResult searchResult) throws Exception {
//                        success(searchResult);
//                    }
//                }, new Consumer<Throwable>() {
//                    @Override
//                    public void accept(Throwable throwable) throws Exception {
//                        error(throwable);
//                    }
//
//                });
    }

    @OnClick(R.id.no_internet_image_view)
    public void onNoInternetImageViewClick(View view){
        Toast.makeText(this, "To nic nie da!", Toast.LENGTH_SHORT).show();
    }


    private void error(Throwable throwable) {
        viewFlipper.setDisplayedChild(viewFlipper.indexOfChild(noInternetImage));
    }

    private void success(SearchResult searchResult) {
        if ("false".equalsIgnoreCase(searchResult.getResponse())) {          //robimy tak z false bo wiemy ze nie przyjdzie nam null           //ignorujemy wielkosc litery

            viewFlipper.setDisplayedChild(viewFlipper.indexOfChild(noResultLayout));

        }else {

            viewFlipper.setDisplayedChild(viewFlipper.indexOfChild(recyclerView));
            adapter.setItems(searchResult.getItems());
        }

    }


    public static Intent createIntent(Context context, String title, int year, String type) {  //inny psosób tworzenia intentów!!
        Intent intent = new Intent(context, ListingActivity.class);    //wzorzec gdzie nie potrzebujesz pamietac jakich parametrow potrzebuje !!
        intent.putExtra(SEARCH_TITLE, title);
        intent.putExtra(SEARCH_YEAR, year);
        intent.putExtra(SEARCH_TYPE,type);
        return intent;
    }


    //    public void setDataOnUiThread(SearchResult result, boolean isProblemWithInternetConnection) { //
//        runOnUiThread(() -> {                                           //jako ze jestesmy w aktywnosci to mozemy uzyc tej metody, inaczej musielibbysmy uzywać numberPicker. Handlera.
//            if (isProblemWithInternetConnection) {
////                viewFlipper.setDisplayedChild(R.id.no_internet_image_view);       //tak nie mozna tego zrobic
//                viewFlipper.setDisplayedChild(viewFlipper.indexOfChild(noInternetImage));
//
//            } else {
//                viewFlipper.setDisplayedChild(viewFlipper.indexOfChild(recyclerView));
//                adapter.setItems(result.getItems());
//            }
//
////            Stream.of(result.getItems()).forEach(movieListingItem -> {        //to bylo tylko do sprawdzenia
////                Log.d("result", "id " + movieListingItem.getImdbID());
////            });
//        });
//
//    }
}
