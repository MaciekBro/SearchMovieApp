package com.example.rent.searchmovieapp;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;

import nucleus.factory.RequiresPresenter;
import nucleus.presenter.Presenter;

/**
 * Created by RENT on 2017-03-07.
 */


public class ListingPresenter extends Presenter<ListingActivity> {

    public void getDataAnsync(String title){        //osobny watek do przetwarzania danych
        new Thread(){                               //requesty sieciowe nie moga byc odpalane na wątku głónym!!!!
            @Override
            public void run() {
                try {
                    String result = getData(title);
                    SearchResult searchResult = new Gson().fromJson(result,SearchResult.class); //metoda ktora przetwarza json na model przez nas stwotrzony
                    getView().setDataOnUiThread(searchResult, false);
                } catch (IOException e) {
                    getView().setDataOnUiThread(null, true);
                }
            }
        }.start();

    }

    public String getData (String title) throws IOException {       //pobiera dane z internetu
        String stringUrl = "http://www.omdbapi.com/?s=" + title;
        URL url = new URL(stringUrl);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setConnectTimeout(3000);
        InputStream inputStream = urlConnection.getInputStream();

        return convertStreamToString(inputStream);
    }

    private String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }


}
