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

/**
 * Created by RENT on 2017-03-08.
 */

public class MoviesListAdapter extends RecyclerView.Adapter<MoviesListAdapter.MyViewHolder> {

    private List<MovieListingItem> items = Collections.emptyList();   //List to jest interface ktory zawiera ArrayList itd. !!!
    private OnMovieItemClickListener onMovieItemClickListener;


    public void setOnMovieItemClickListener (OnMovieItemClickListener onMovieItemClickListener) {   //do ustawiania
        this.onMovieItemClickListener = onMovieItemClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {        //parentem jest RecyclerView
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);    //false jest po to zeby nie dodawal do parenta(recycler view).Recycler view sam sobie tym zarządza!
        return new MyViewHolder(layout);                    //wyzej zaladowalismy layout ktory uzywamy w MyViewHolder
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {       //ta metoda jest wolana dla kazdego widoku
        //tutaj ustawiamy konkretne widoki!
        MovieListingItem  movieListingItem = items.get(position);
        Glide.with(holder.poster.getContext()).load(movieListingItem.getPoster()).into(holder.poster);   //wzielismy kontekst z postera, bo tego wymaga Glide
        holder.titleAndYear.setText(movieListingItem.getTitle() + "(" + movieListingItem.getYear() + ")");
        holder.type.setText("typ: " + movieListingItem.getType());

        holder.itemView.setOnClickListener(v -> {
            if (onMovieItemClickListener != null) {
                onMovieItemClickListener.onMovieItemClick(movieListingItem.getImdbID());
            }
        });
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
        View itemView; //zeby moc kliknac i przejsc do detali. zeby moc ustawic onClickListener

        public MyViewHolder(View itemView) {        //itemView jest naszym list_item. wczytalismy go za pomocą inflate
            super(itemView);
            this.itemView = itemView;
            poster = (ImageView) itemView.findViewById(R.id.poster);
            titleAndYear = (TextView) itemView.findViewById(R.id.title_and_year);
            type = (TextView) itemView.findViewById(R.id.type);

        }
    }

}
