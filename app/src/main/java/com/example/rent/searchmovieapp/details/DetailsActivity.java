package com.example.rent.searchmovieapp.details;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.rent.searchmovieapp.MovieApplication;
import com.example.rent.searchmovieapp.R;
import com.example.rent.searchmovieapp.RetrofitProvider;
import com.example.rent.searchmovieapp.details.gallery.GalleryActivity;

import org.w3c.dom.Text;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import nucleus.factory.RequiresPresenter;
import nucleus.view.NucleusAppCompatActivity;
import retrofit2.Retrofit;

@RequiresPresenter(DetailPresenter.class)
public class DetailsActivity extends NucleusAppCompatActivity<DetailPresenter> {
    private static final String ID_KEY = "id_key";
    private Disposable subscribe; //zeby zapobiec wyciekom

    @BindView(R.id.poster)
    ImageView poster;

    @BindView(R.id.title_and_year)
    TextView titleAndYear;

    @BindView(R.id.type)
    TextView type;

    @BindView(R.id.plot)
    TextView plot;

    @BindView(R.id.rating)
    TextView rating;

    @BindView(R.id.awards)
    TextView awards;

    @BindView(R.id.director)
    TextView director;

    @BindView(R.id.actors)
    TextView actors;

    @BindView(R.id.country)
    TextView country;

    @Inject
    Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);
        String imdbId = getIntent().getStringExtra(ID_KEY);

        //niepotrzebne przy daggerze
//        RetrofitProvider retrofitProvider = (RetrofitProvider) getApplication();  //pobieramy konfiguracje retrofit z application
//        getPresenter().setRetrofit(retrofitProvider.privideRetrofit());

        //dagger
        MovieApplication movieApplication = (MovieApplication) getApplication();
        movieApplication.getAppComponent().inject(this);
        getPresenter().setRetrofit(retrofit);
        //dagger

        subscribe = getPresenter().loadDetail(imdbId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::success, this::error);


    }

    @Override
    protected void onDestroy() {        //zeby zapobiec wyciekom pamieci!!!
        super.onDestroy();
        if (subscribe != null) {
            subscribe.dispose();
        }
    }

    private void success(MovieItem movieItem) {
        Glide.with(this).load(movieItem.getPoster()).into(poster);
        titleAndYear.setText(movieItem.getTitle() + " (" + movieItem.getYear() + ")");
        type.setText(movieItem.getType());
        plot.setText("Description: " + movieItem.getPlot() + "\n");
        rating.setText(movieItem.getImdbRating() + "/10"+ "\n");
        awards.setText("Awards: " + movieItem.getAwards()+ "\n");
        director.setText("Director: " +movieItem.getDirector()+ "\n");
        actors.setText("Actors: " + movieItem.getActors());

        poster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(GalleryActivity.createIntent(DetailsActivity.this, movieItem.getPoster()));
            }
        });

    }

    private void error(Throwable throwable) {

    }


    public static Intent createIntent(Context context, String imdbID) {
        Intent intent = new Intent(context, DetailsActivity.class);
        intent.putExtra(ID_KEY, imdbID);
        return intent;
    }

}
