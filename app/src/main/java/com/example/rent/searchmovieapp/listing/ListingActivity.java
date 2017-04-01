package com.example.rent.searchmovieapp.listing;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;


import com.example.rent.searchmovieapp.MovieDatabaseOpenHelper;
import com.example.rent.searchmovieapp.MovieTableContract;
import com.example.rent.searchmovieapp.R;
import com.example.rent.searchmovieapp.RetrofitProvider;
import com.example.rent.searchmovieapp.details.DetailsActivity;
import com.example.rent.searchmovieapp.search.SearchResult;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import nucleus.factory.RequiresPresenter;
import nucleus.view.NucleusAppCompatActivity;

import static io.reactivex.android.schedulers.AndroidSchedulers.mainThread;
import static io.reactivex.schedulers.Schedulers.io;


@RequiresPresenter(ListingPresenter.class)
public class ListingActivity extends NucleusAppCompatActivity<ListingPresenter> implements CurrentItemListener, ShowOrHideCounter, OnMovieItemClickListener, OnLikeButtonClickListener {

    private static final String SEARCH_TITLE = "search_title";    //do przechowywania wpisanego tekstu
    private MoviesListAdapter adapter;
    private static final String SEARCH_YEAR = "search_year";
    public static final int NO_YEAR_SELECTED = -1;
    public static final String SEARCH_TYPE = "search_type";

    MovieDatabaseOpenHelper openHelper;


    @BindView(R.id.view_flipper)
    ViewFlipper viewFlipper;

    @BindView(R.id.no_internet_image_view)
    ImageView noInternetImage;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.no_result)
    FrameLayout noResultLayout;

    @BindView(R.id.counter)
    TextView counter;

    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;

    private EndlessScrollListener endlessScrollListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listing_activity);
        String title = getIntent().getStringExtra(SEARCH_TITLE); //wyciagamy tekst z intentu

        ButterKnife.bind(this); //binduje nasze powiazania nie potrzebujemy ich pozniej

        if (savedInstanceState == null) { //jest nullem przy pierwszym uruchomieniu aplikacji, potem juz nie!!! wiemy z tego czy wchodzimy w aplikacje ktorys raz
            RetrofitProvider retrofitProvider = (RetrofitProvider) getApplication();   //wiemy ze aplikacja jest RetrofitProviderem
            getPresenter().setRetrofit(retrofitProvider.privideRetrofit());         //ustawiamy retrofita do prezentera
        }

        int year = getIntent().getIntExtra(SEARCH_YEAR, NO_YEAR_SELECTED);
        String type = getIntent().getStringExtra(SEARCH_TYPE);

//        viewFlipper = (ViewFlipper) findViewById(R.id.view_flipper);
//        noInternetImage = (ImageView) findViewById(R.id.no_internet_image_view);
//        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        openHelper = new MovieDatabaseOpenHelper(this);
        adapter = new MoviesListAdapter();
        adapter.setOnMovieItemClickListener(this);
        adapter.setOnLikeButtonClickListener(this);

        recyclerView.setAdapter(adapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        endlessScrollListener = new EndlessScrollListener(linearLayoutManager, getPresenter());
        recyclerView.addOnScrollListener(endlessScrollListener);

        endlessScrollListener.setCurrentItemListener(this); //bo aktywność nasłuchuje
        endlessScrollListener.setShowOrHideCounter(this);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                startLoading(title, year, type);

            }
        });

        startLoading(title, year, type);

        counter.animate().alpha(0);

    }

    //        getPresenter().startLoadingItems(title, year, type)   //getPresenter zwraca prezentaera którego wczesniej definiowalismy
//                .subscribeOn(io())       //to co jest  powyżej jest wykonane w innym wątku
//                .observeOn(mainThread())  //to co bedzie wykonywane w głównym wątku
//                .subscribe(this::success, this::error);     //pierwszy metr jest pozytywny, drugi odpowiada za bledy


    //        getPresenter().startLoadingItems(title)
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

    private void startLoading(String title, int year, String type) {
        getPresenter().startLoadingItems(title, year, type);   //getPresenter zwraca prezentaera którego wczesniej definiowalismy
//                .subscribeOn(io())       //to co jest  powyżej jest wykonane w innym wątku
//                .observeOn(mainThread())  //to co bedzie wykonywane w głównym wątku
//                .subscribe(this::success, this::error);
    }

    @OnClick(R.id.no_internet_image_view)
    public void onNoInternetImageViewClick(View view) {
        Toast.makeText(this, "To nic nie da!", Toast.LENGTH_SHORT).show();
    }

    private void error(Throwable throwable) {
        swipeRefreshLayout.setRefreshing(false);
        viewFlipper.setDisplayedChild(viewFlipper.indexOfChild(noInternetImage));
    }


    private void success(ResultAggregator resultAggregator) {

        swipeRefreshLayout.setRefreshing(false);

        if ("false".equalsIgnoreCase(resultAggregator.getResponse())) {          //robimy tak z false bo wiemy ze nie przyjdzie nam null           //ignorujemy wielkosc litery

            viewFlipper.setDisplayedChild(viewFlipper.indexOfChild(noResultLayout));

        } else {

            viewFlipper.setDisplayedChild(viewFlipper.indexOfChild(swipeRefreshLayout));
            adapter.setItems(resultAggregator.getMovieItems());
            endlessScrollListener.setTotalItemsNumber(resultAggregator.getTotalItemsResult());
        }

    }

    public void appendItems(SearchResult searchResult) { //ma za zadanie dodac do adaptera kolejne strony
        adapter.addItems(searchResult.getItems());
        endlessScrollListener.setTotalItemsNumber(Integer.parseInt(searchResult.getTotalResults()));
    }

    public static Intent createIntent(Context context, String title, int year, String type) {  //inny psosób tworzenia intentów!!
        Intent intent = new Intent(context, ListingActivity.class);    //wzorzec gdzie nie potrzebujesz pamietac jakich parametrow potrzebuje !!
        intent.putExtra(SEARCH_TITLE, title);
        intent.putExtra(SEARCH_YEAR, year);
        intent.putExtra(SEARCH_TYPE, type);
        return intent;
    }


    @Override
    public void onNewCurrentItem(int currentItem, int totalItemsCount) {
        counter.setText(currentItem + "/" + totalItemsCount);
    }

    @Override
    public void showCounter() {
        counter.animate().alpha(1).setDuration(20);
    }

    @Override
    public void hideCounter() {
        counter.animate().alpha(0).setDuration(900);   //translation jest relatywne do jego początkowej pozycji
    }

    @Override
    public void onMovieItemClick(String imdbID) {           //z interface
        startActivity(DetailsActivity.createIntent(this, imdbID));
    }

    public void setNewAggregatorResult(ResultAggregator newAggregatorResult) {
        swipeRefreshLayout.setRefreshing(false);
        success(newAggregatorResult);

    }

    @Override
    public void onLikeButtonClick(MovieListingItem movieListingItem) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MovieTableContract.COLUMN_TITLE, movieListingItem.getTitle());
        contentValues.put(MovieTableContract.COLUMN_YEAR, movieListingItem.getYear());
        contentValues.put(MovieTableContract.COLUMN_POSTER, movieListingItem.getPoster());
        contentValues.put(MovieTableContract.COLUMN_TYPE, movieListingItem.getType());

        openHelper.getWritableDatabase().insert(MovieTableContract.TABLE_NAME, null, contentValues);
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
