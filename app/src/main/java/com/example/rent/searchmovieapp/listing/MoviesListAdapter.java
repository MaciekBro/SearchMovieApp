package com.example.rent.searchmovieapp.listing;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.rent.searchmovieapp.R;

import java.util.Collections;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by RENT on 2017-03-08.
 */

public class MoviesListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<MovieListingItem> items = Collections.emptyList();   //List to jest interface ktory zawiera ArrayList itd. !!!
    private OnMovieItemClickListener onMovieItemClickListener;
    private static final int GAMES_VIEW_HOLDER = 1;
    private static final int MY_VIEW_HOLDER = 2;

    public void setOnMovieItemClickListener(OnMovieItemClickListener onMovieItemClickListener) {   //do ustawiania
        this.onMovieItemClickListener = onMovieItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {        //parentem jest RecyclerView
        if (viewType == GAMES_VIEW_HOLDER) {
            View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.game_list_item, parent, false);
            return new GamesViewHolder(layout);
        } else if (viewType == MY_VIEW_HOLDER) {
            View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
            return new MyViewHolder(layout);
        }
        return null;

//        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);    //false jest po to zeby nie dodawal do parenta(recycler view).Recycler view sam sobie tym zarządza!
//        return new MyViewHolder(layout);                    //wyzej zaladowalismy layout ktory uzywamy w MyViewHolder
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {       //ta metoda jest wolana dla kazdego widoku
        //tutaj ustawiamy konkretne widoki!
        MovieListingItem movieListingItem = items.get(position);
        if (getItemViewType(position) == MY_VIEW_HOLDER) {
            MyViewHolder myViewHolder = (MyViewHolder) holder;  //musimy go rzutowac na ten typ ktory oczekujemy!

            Glide.with(myViewHolder.poster.getContext()).load(movieListingItem.getPoster()).into(myViewHolder.poster);   //wzielismy kontekst z postera, bo tego wymaga Glide
            myViewHolder.titleAndYear.setText(movieListingItem.getTitle() + "(" + movieListingItem.getYear() + ")");
            myViewHolder.type.setText("typ: " + movieListingItem.getType());
            myViewHolder.itemView.setOnClickListener(v -> {
                if (onMovieItemClickListener != null) {
                    onMovieItemClickListener.onMovieItemClick(movieListingItem.getImdbID());
                }
            });
        } else {
            GamesViewHolder gamesViewHolder = (GamesViewHolder) holder;
            Glide.with(gamesViewHolder.poster.getContext()).load(movieListingItem.getPoster()).into(gamesViewHolder.poster);
            gamesViewHolder.title.setText(movieListingItem.getTitle());

            gamesViewHolder.itemView.setOnClickListener(v -> {
                if (onMovieItemClickListener != null) {
                    onMovieItemClickListener.onMovieItemClick(movieListingItem.getImdbID());
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        if ("Game".equalsIgnoreCase(items.get(position).getType())){
            return GAMES_VIEW_HOLDER;
        } else {
            return MY_VIEW_HOLDER;
        }

    }

    public void setItems(List<MovieListingItem> items) {        //zebysmy mogli podac itemy do adaptera
        this.items = items;
        notifyDataSetChanged();
    }

    public void addItems(List<MovieListingItem> items) {
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {    //wzorzec do przeyrzymywania widoków i zarządzania nimi
        ImageView poster;
        TextView titleAndYear;
        TextView type;
//        View itemView; //zeby moc kliknac i przejsc do detali. zeby moc ustawic onClickListener //jenak nie jest potrzebne bo jest domyslnie w viewholderze zainicjowane

        public MyViewHolder(View itemView) {        //itemView jest naszym list_item. wczytalismy go za pomocą inflate
            super(itemView);
//            this.itemView = itemView;
            poster = (ImageView) itemView.findViewById(R.id.poster);
            titleAndYear = (TextView) itemView.findViewById(R.id.title_and_year);
            type = (TextView) itemView.findViewById(R.id.type);

        }
    }

    class GamesViewHolder extends RecyclerView.ViewHolder {

        ImageView poster;
        TextView title;

        public GamesViewHolder(View itemView) {
            super(itemView);
            poster = ButterKnife.findById(itemView,R.id.game_poster);
            title = ButterKnife.findById(itemView,R.id.game_title);
        }
    }

}
