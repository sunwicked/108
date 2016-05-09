package com.one.assignment.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.one.assignement.R;
import com.one.assignment.LabConstants;
import com.one.assignment.adapters.FeedRecyclerViewAdapter;
import com.one.assignment.models.MovieFeed;

/**
 * Created by Sunny on 09-05-2016.
 */
public class FeedFragment extends Fragment {

    private RecyclerView mMovieFeedRv;

    private MovieFeed feed;

    FeedRecyclerViewAdapter mFeedAdapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        feed =  new MovieFeed();


    }

    // Called when Fragment should create its View hierarchy,
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_feed, container,false);
    }



    // After onCreateView()
    // View lookups ,  view listeners etc.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mMovieFeedRv = (RecyclerView) view.findViewById(R.id.rv_feed);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mMovieFeedRv.setLayoutManager(mLayoutManager);

    }


    // Called after the parent Activity's onCreate() method has completed.
      @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

          Bundle bundle = this.getArguments();
          if (bundle != null) {
             feed =  bundle.getParcelable(LabConstants.KEY_LOCAL);
              mFeedAdapter = new FeedRecyclerViewAdapter(getContext(), feed);
              mMovieFeedRv.setAdapter(mFeedAdapter);
          }
    }
}
