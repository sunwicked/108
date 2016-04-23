package com.one.assignment.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.one.assignement.R;
import com.one.assignment.models.Result;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Sunny on 23-04-2016.
 */
public class FeedRecyclerViewAdapter extends RecyclerView.Adapter<FeedRecyclerViewAdapter.MovieViewHolder> {

    private List<Result> mMovieList;
    private Context mContext;

    public FeedRecyclerViewAdapter(Context context, List<Result> feed) {
        mContext = context;
        mMovieList = feed;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.row_movie, parent, false);
        MovieViewHolder movieViewHolder = new MovieViewHolder(view);
        return movieViewHolder;
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        Result movie = mMovieList.get(position);
        holder.movieTitleTv.setText(movie.getOriginalTitle());
        holder.movieReleaseTv.setText(movie.getReleaseDate());
        holder.moviePopularityTv.setText(Double.toString(movie.getPopularity()));
        holder.movieVoteCountTv.setText(Integer.toString(movie.getVoteCount()));
        holder.movieOverviewTv.setText(movie.getOverview());
        Picasso.with(mContext).load(movie.getPosterPath()).into(holder.movieIv);
    }


    @Override
    public int getItemCount() {
        return mMovieList.size();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView movieIv;
        TextView movieTitleTv, movieOverviewTv, movieVoteCountTv, movieReleaseTv, moviePopularityTv;

        public MovieViewHolder(View itemView) {
            super(itemView);
            movieIv = (ImageView) itemView.findViewById(R.id.iv_movie);
            movieTitleTv = (TextView) itemView.findViewById(R.id.tv_movie_title);
            movieOverviewTv = (TextView) itemView.findViewById(R.id.tv_description);
            moviePopularityTv = (TextView) itemView.findViewById(R.id.tv_popularity);
            movieReleaseTv = (TextView) itemView.findViewById(R.id.tv_release_date);
            movieVoteCountTv = (TextView) itemView.findViewById(R.id.tv_vote_Count);
        }
    }
}
