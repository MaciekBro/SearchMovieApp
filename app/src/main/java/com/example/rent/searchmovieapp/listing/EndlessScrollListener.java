package com.example.rent.searchmovieapp.listing;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

/**
 * Created by RENT on 2017-03-11.
 */

public class EndlessScrollListener extends RecyclerView.OnScrollListener {

    //    private RecyclerView.LayoutManager layoutManager;   //zeby wiedziec na ktorej pozycji jestesmy
    private LinearLayoutManager layoutManager;
    private static final double PAGE_SIZE = 10;
    int totalItemsNumber;
    boolean isLoading = false;
    private OnLoadNextPageListener listener;
    private CurrentItemListener currentItemListener;
    private ShowOrHideCounter showOrHideCounter;
    private boolean isCounterShown;


    public void setShowOrHideCounter(ShowOrHideCounter showOrHideCounter) {
        this.showOrHideCounter = showOrHideCounter;
    }

    public EndlessScrollListener(LinearLayoutManager layoutManager, OnLoadNextPageListener listener) {
        this.layoutManager = layoutManager;
        this.listener = listener;

    }

    public void setCurrentItemListener(CurrentItemListener currentItemListener) {       //lepiej w setterze niz w konstruktorze
        this.currentItemListener = currentItemListener;

    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        int alreadyLoadedItems = layoutManager.getItemCount(); //zaladowane itemy
        int currentPage = (int) Math.ceil(alreadyLoadedItems / PAGE_SIZE);
        double numberOfAllPages = Math.ceil(totalItemsNumber / PAGE_SIZE);

        int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition() + 1;

        if (lastVisibleItemPosition == alreadyLoadedItems && currentPage < numberOfAllPages && !isLoading) {
            loadNextPage(++currentPage);
            isLoading = true;
        }

        if (currentItemListener != null){
            currentItemListener.onNewCurrentItem(lastVisibleItemPosition, totalItemsNumber);
        }
    }

    private void loadNextPage(int pageNumber) {     //wynosimy to do interface bo tylko aktywnosc lub prezenter moze doladowac strony
        listener.loadNextPage(pageNumber); //powiadomienie innej klasy ze ma zalaowac
    }

    public void setTotalItemsNumber(int totalItemsNumber) {
        this.totalItemsNumber = totalItemsNumber;
        isLoading = false;
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        if (showOrHideCounter!= null){
            if (isCounterShown && newState == RecyclerView.SCROLL_STATE_IDLE){
                showOrHideCounter.hideCounter();
                isCounterShown= false;
            } else if (!isCounterShown && newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                showOrHideCounter.showCounter();
                isCounterShown = true;
            }
        }
    }
}
