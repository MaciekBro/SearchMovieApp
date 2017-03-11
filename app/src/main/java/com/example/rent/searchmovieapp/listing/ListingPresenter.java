package com.example.rent.searchmovieapp.listing;

import com.example.rent.searchmovieapp.search.SearchResult;
import com.example.rent.searchmovieapp.search.SearchService;

import io.reactivex.Observable;
import nucleus.presenter.Presenter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by RENT on 2017-03-07.
 */


public class ListingPresenter extends Presenter<ListingActivity> {

    private Retrofit retrofit;


    //mozna wyrzucic bo dostarczamy rettrofita z aktywnosci
//    public ListingPresenter() {            //pobieranie informacji z sieci
//        retrofit = new Retrofit.Builder()
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .addConverterFactory(GsonConverterFactory.create())
//                .baseUrl("http://www.omdbapi.com")
//                .build();
//    }

    public Observable<SearchResult> getDataAnsync(String title, int year, String type) {

        String stringYear = year == ListingActivity.NO_YEAR_SELECTED ? null : String.valueOf(year); //zmieniamy typ roku

        return retrofit.create(SearchService.class).search(title,                   //search to nasza metoda z interface SearchService
                stringYear, type);
    }

    public void setRetrofit(Retrofit retrofit) {
        this.retrofit = retrofit;

    }

    //wszystko co mielismy poniżej moglismy uprościć za pomocą RETRO FIT!!!!!!


//
//    public void getDataAnsync(String title){        //osobny watek do przetwarzania danych
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