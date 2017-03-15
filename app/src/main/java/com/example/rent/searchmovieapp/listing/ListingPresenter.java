package com.example.rent.searchmovieapp.listing;

import com.example.rent.searchmovieapp.search.SearchResult;
import com.example.rent.searchmovieapp.search.SearchService;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import nucleus.presenter.Presenter;
import retrofit2.Retrofit;

/**
 * Created by RENT on 2017-03-07.
 */


public class ListingPresenter extends Presenter<ListingActivity> implements OnLoadNextPageListener {

    private ResultAggregator resultAggregator = new ResultAggregator();

    private Retrofit retrofit;
    private String title;
    private String stringYear;
    private String type;
    private SearchResult searchResultOfAllItems;
    private boolean isLoadingFromStart;

//    public Observable<ResultAggregator> subscribeOnLoadingResult() {   // ResultAggregator bedzie sie zmienial i za pomocą obserwatora bedzie on powiadamiał inne metody o tym
//                                                                        //bedziemy powiadamiac aktywność ze cos sie zmienia
//    }

    public void startLoadingItems(String title, int year, String type) {
        this.title = title;
        this.type = type;

        String stringYear = year == ListingActivity.NO_YEAR_SELECTED ? null : String.valueOf(year); //zmieniamy typ roku

        if (resultAggregator.getMovieItems().size() == 0) {
            loadNextPage(1);
            isLoadingFromStart=true;
        }


        //to jest tak na prawde to samo co loadNextPage(1);
//        retrofit.create(SearchService.class).search(1, title, stringYear, type)       //search to nasza metoda z interface SearchService
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<SearchResult>() {
//                    @Override
//                    public void accept(SearchResult searchResult) throws Exception {
//                        resultAggregator.addNewItems(searchResult.getItems());
//                        resultAggregator.setTotalItemsResult(Integer.parseInt(searchResult.getTotalResults()));
//                    }
//                }, new Consumer<Throwable>() {
//                    @Override
//                    public void accept(Throwable throwable) throws Exception {
//
//                    }
//                });
    }

    @Override
    protected void onTakeView(ListingActivity listingActivity) {        //metoda w tym momencie podpina widok do
        super.onTakeView(listingActivity);
        if (!isLoadingFromStart){

            listingActivity.setNewAggregatorResult(resultAggregator);
        }
    }

    public void setRetrofit(Retrofit retrofit) {
        this.retrofit = retrofit;

    }

    @Override
    public void loadNextPage(int page) {
        isLoadingFromStart=false;
        retrofit.create(SearchService.class).search(page, title, stringYear, type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(searchResult -> {
                    resultAggregator.addNewItems(searchResult.getItems());
                    resultAggregator.setTotalItemsResult(Integer.parseInt(searchResult.getTotalResults()));
                    resultAggregator.setResponse(searchResult.getResponse());
                    getView().setNewAggregatorResult(resultAggregator);
                }, throwable -> {
                    //TODO obsługa błędów
                });
    }

    //mozna wyrzucic bo dostarczamy rettrofita z aktywnosci
//    public ListingPresenter() {            //pobieranie informacji z sieci
//        retrofit = new Retrofit.Builder()
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .addConverterFactory(GsonConverterFactory.create())
//                .baseUrl("http://www.omdbapi.com")
//                .build();
//    }

    //wszystko co mielismy poniżej moglismy uprościć za pomocą RETRO FIT!!!!!!


//
//    public void startLoadingItems(String title){        //osobny watek do przetwarzania danych
//        new Thread(){                               //requesty sieciowe nie moga byc odpalane na wątku głónym!!!!
//            @Override
//            public void run() {
//                try {
//                    String result = getData(title);
//                    SearchResult searchResult = new Gson().fromJson(result,SearchResult.class); //metoda ktora przetwarza json na model przez nas stwotrzony
//                    getView().setDataOnUiThread(searchResult, false);
//                } catch (IOException e) {
//                    getView().setDataOnUiThread(null, true);
//                }
//            }
//        }.start();
//
//    }
//
//    public String getData (String title) throws IOException {       //pobiera dane z internetu
//        String stringUrl = "http://www.omdbapi.com/?s=" + title;
//        URL url = new URL(stringUrl);
//        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//        urlConnection.setConnectTimeout(3000);
//        InputStream inputStream = urlConnection.getInputStream();
//
//        return convertStreamToString(inputStream);
//    }
//
//    private String convertStreamToString(java.io.InputStream is) {
//        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
//        return s.hasNext() ? s.next() : "";
//    }

}
