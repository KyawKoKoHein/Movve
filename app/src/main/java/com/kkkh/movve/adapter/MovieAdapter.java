package com.kkkh.movve.adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kkkh.movve.R;
import com.kkkh.movve.model.Movie;
import android.content.Context;
import android.content.Intent;

import com.kkkh.movve.MovieDetailActivity;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private Context context;
    private final List<Movie> movieList;


    public MovieAdapter(Context context, List<Movie> movieList) {
        this.context = context;
        this.movieList = movieList;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_movie, parent, false);

        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {

        Movie movie = movieList.get(position);

        holder.imgMovie.setImageResource(movie.getImage());
        holder.txtMovieTitle.setText(movie.getTitle());
        holder.itemView.setOnClickListener(v->{

            Intent intent=new Intent(context,MovieDetailActivity.class);

            intent.putExtra("movieId",movie.getId());

            context.startActivity(intent);

        });

    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    static class MovieViewHolder extends RecyclerView.ViewHolder {

        ImageView imgMovie;
        TextView txtMovieTitle;

        MovieViewHolder(@NonNull View itemView) {
            super(itemView);

            imgMovie = itemView.findViewById(R.id.imgMovie);
            txtMovieTitle = itemView.findViewById(R.id.txtMovieTitle);
        }
    }
}
