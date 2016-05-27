package com.one.assignment.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.one.assignement.R;
import com.one.assignment.models.MovieFeed;
import com.one.assignment.models.Result;
import com.one.assignment.recyclerUtils.ItemTouchHelperAdapter;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

/**
 * Created by Sunny on 23-04-2016.
 */
public class FeedAdapterRv extends RecyclerView.Adapter<FeedAdapterRv.MovieViewHolder> implements ItemTouchHelperAdapter {

    private List<Result> mMovieList;
    private Context mContext;

    public FeedAdapterRv(Context context, MovieFeed feed) {
        mContext = context;
        mMovieList = feed.getResults();
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
        Picasso.with(mContext).load(movie.getPosterPath())
                .into(holder.movieIv);
    }


    @Override
    public int getItemCount() {
        return mMovieList.size();
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(mMovieList, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(mMovieList, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public void onItemDismiss(int position) {
        mMovieList.remove(position);
        notifyItemRemoved(position);
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
