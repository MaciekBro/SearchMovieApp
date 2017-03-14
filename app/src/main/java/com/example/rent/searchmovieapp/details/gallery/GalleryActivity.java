package com.example.rent.searchmovieapp.details.gallery;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.rent.searchmovieapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GalleryActivity extends AppCompatActivity {

    private static final String URL_KEY= "url_key";

    @BindView(R.id.full_screen_image)
    ImageView fullScreenImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        ButterKnife.bind(this);
        String url = getIntent().getStringExtra(URL_KEY);
        Glide.with(this).load(url).into(fullScreenImage);

    }

    public static Intent createIntent (Context context, String url) {
        Intent intent = new Intent(context, GalleryActivity.class);
        intent.putExtra(URL_KEY, url);
        return intent;
    }

}
