package project.epam.com.cinemawaddle.tabitems.adapters;


import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import project.epam.com.cinemawaddle.R;
import project.epam.com.cinemawaddle.util.Constants;
import project.epam.com.cinemawaddle.util.service.movies.Movie;


/**
 * RecyclerView adapter to display a list of {@link Movie}.
 */
public class MoviesRecyclerAdapter extends BaseAdapter<Movie> {

    protected Context context;
    private OnMovieClickListener listener;

    public MoviesRecyclerAdapter(Context context, OnMovieClickListener listener,
                                 List<Movie> movies) {
        super(movies);
        this.context = context;
        this.listener = listener;
    }

    @Override
    public void onBindViewHolder(BaseAdapter.ViewHolder holder, int position) {
        Movie movie = objects.get(position);

        holder.object = movie;
        holder.titleView.setText(movie.getName());
        holder.genreView.setText(movie.getReleaseDate());
        holder.ratingView.setText(getVote(movie));
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onMovieClick(movie);
            }
        });

        Glide.with(context)
                .load(Constants.SECURE_BASE_IMAGE_URL + movie.getPosterPath())
                .centerCrop()
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .placeholder(R.drawable.ic_no_poster)
                .fallback(R.drawable.ic_no_poster)
                .into(holder.posterView);
    }

    protected String getVote(Movie movie) {
        double voteAverage = movie.getVoteAverage();
        return voteAverage == 0.0
                ? "--"
                : String.valueOf(voteAverage);
    }

    public interface OnMovieClickListener {
        void onMovieClick(Movie movie);
    }
}
