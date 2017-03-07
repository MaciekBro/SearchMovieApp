package com.example.rent.searchmovieapp;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.TextView;

public class SearchActivity extends AppCompatActivity {

    NumberPicker np;
    TextView tv1;
    TextView tv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity_main);

        TextInputEditText editText = (TextInputEditText) findViewById(R.id.edit_text);

        ImageButton searchButton = (ImageButton) findViewById(R.id.search_button);
        searchButton.setOnClickListener(v -> {
            startActivity(ListingActivity.createIntent(SearchActivity.this, editText.getText().toString()));
        });


        np = (NumberPicker) findViewById(R.id.number_picker);
//        tv1 = (TextView) findViewById(R.id.textView2);
//        tv2 = (TextView) findViewById(R.id.textView3);

        np.setMinValue(1950);
        np.setMaxValue(2017);
        np.setWrapSelectorWheel(false);

//        np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
//
//            @Override
//            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
//                // TODO Auto-generated method stub
//
//                String Old = "Old Value : ";
//                String New = "New Value : ";
//
////                tv1.setText(Old.concat(String.valueOf(oldVal)));
////                tv2.setText(New.concat(String.valueOf(newVal)));
//            }
//        });


    }


}
