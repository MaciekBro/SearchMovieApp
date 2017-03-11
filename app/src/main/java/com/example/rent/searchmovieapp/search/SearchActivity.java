package com.example.rent.searchmovieapp.search;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.RadioGroup;

import com.example.rent.searchmovieapp.R;
import com.example.rent.searchmovieapp.RetrofitProvider;
import com.example.rent.searchmovieapp.listing.ListingActivity;
import com.example.rent.searchmovieapp.listing.MovieListingItem;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.functions.Function;
import retrofit2.Retrofit;

public class SearchActivity extends AppCompatActivity {

    //z id robi klucz api
    private Map<Integer, String> apiKeysMap = new HashMap<Integer, String>() {{
        put(R.id.movie_radio_button, "movie");
        put(R.id.episodes_radio_button, "episode");
        put(R.id.series_radio_button, "series");
        put(R.id.games_radio_button, "game");
    }};

    @BindView(R.id.number_picker)
    NumberPicker numberPicker;

    @BindView(R.id.search_button)
    ImageButton searchButton;

    @BindView(R.id.edit_text)
    TextInputEditText editText;

    @BindView(R.id.year_check_box)
    CheckBox yearCheckBox;

    @BindView(R.id.type_checkbox)
    CheckBox typeCheckBox;

    @BindView(R.id.radio_group)
    RadioGroup radioGroup;

    @BindView(R.id.poster_header_recycler_view)
    RecyclerView posterHeaderRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity_main);
        ButterKnife.bind(this);

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);

        numberPicker.setMinValue(1950);
        numberPicker.setMaxValue(year);
        numberPicker.setWrapSelectorWheel(false);
        numberPicker.setValue(year);

        PosterRecyclerViewAdapter adapter = new PosterRecyclerViewAdapter();
        posterHeaderRecyclerView.setAdapter(adapter);
        posterHeaderRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        RetrofitProvider retrofitProvider = (RetrofitProvider) getApplication();
        Retrofit retrofit = retrofitProvider.privideRetrofit();
        SearchService searchService = retrofit.create(SearchService.class);


        searchService.search("A*", "2016", null)
                .flatMap(searchResult -> Observable.fromIterable(searchResult.getItems()))            //rozbijamy wynik, zeby móc dostać się do url posterów
                .map(MovieListingItem::getPoster);


//        searchService.search("A*", "2016", null)
//                .flatMap(searchResult -> Observable.fromIterable(searchResult.getItems()))            //rozbijamy wynik, zeby móc dostać się do url posterów
//                .map(new Function<MovieListingItem, String>() {          //mowimy ze chcemy z obiektu dostać tylko String
//                    @Override
//                    public String apply(MovieListingItem movieListingItem) throws Exception {
//                        return movieListingItem.getPoster();
//                    }
//                });


    }

    @OnCheckedChanged(R.id.year_check_box)
    void onCheckboxStateChanged(CompoundButton buttonView, boolean isChecked) {
        numberPicker.setVisibility(isChecked ? View.VISIBLE : View.GONE);
    }

    @OnCheckedChanged(R.id.type_checkbox)
    void onTypeCheckboxStateChanged(CompoundButton buttonView, boolean isChecked) {
        radioGroup.setVisibility(isChecked ? View.VISIBLE : View.INVISIBLE);
    }

    @OnClick(R.id.search_button)
    void onSearchButtonClick() {
        int checkRadioId = radioGroup.getCheckedRadioButtonId();
        String typeKey = typeCheckBox.isChecked() ? apiKeysMap.get(checkRadioId) : null;
        int year = yearCheckBox.isChecked() ? numberPicker.getValue() : ListingActivity.NO_YEAR_SELECTED;

        startActivity(ListingActivity.createIntent(SearchActivity.this, editText.getText().toString(),
                year, typeKey));
    }

}