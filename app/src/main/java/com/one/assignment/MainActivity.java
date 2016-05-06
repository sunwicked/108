package com.one.assignment;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.one.assignement.R;
import com.one.assignment.adapters.FeedRecyclerViewAdapter;
import com.one.assignment.database.DbManager;
import com.one.assignment.listeners.DownloaderListener;
import com.one.assignment.models.MovieFeed;
import com.one.assignment.models.Result;
import com.one.assignment.utils.AsyncStart;
import com.one.assignment.utils.ConnectionDetector;
import com.one.assignment.utils.Log;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements DownloaderListener {

    private TextView mStatusTv;
    private RecyclerView mMovieFeedRv;

    private static final String TAG = "MainActivity";


    private MovieFeed feed;
    private DbManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setViews();
        if (null != savedInstanceState) {
            feed = savedInstanceState.getParcelable(LabConstants.KEY);
        }
        dbManager = new DbManager(this);
        try {
            setFeed();
        } catch (IOException e) {
            e.printStackTrace();
        }
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_refresh);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    setFeed();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }


    private void setViews() {
        mStatusTv = (TextView) findViewById(R.id.tv_status);
        mStatusTv.setVisibility(View.VISIBLE);
        mMovieFeedRv = (RecyclerView) findViewById(R.id.rv_feed);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mMovieFeedRv.setLayoutManager(mLayoutManager);

    }


    private void setFeed() throws IOException {
        if (null != feed) {
            bindData();
        } else if (ConnectionDetector.isConnectingToInternet(this)) {
            feed = new MovieFeed();
            mStatusTv.setText(getResources().getString(R.string.loading));
            Log.d(TAG, "setFeed:" + "network available");
            AsyncStart asyncStart = new AsyncStart(this);
            asyncStart.execute();
        } else {

            mStatusTv.setText(getResources().getString(R.string.local_data));
            feed = new MovieFeed();
            ArrayList<Result> localResults = dbManager.fetchAll();
            if (!localResults.isEmpty()) {
                feed.setResults(localResults);
                setFeed();
            } else {
                mStatusTv.setText(getResources().getString(R.string.no_network));
                ObjectAnimator.ofFloat(mStatusTv, "translationX", -10, 0, 10, 0).start();
                Log.d(TAG, "setFeed:" + "network not available");
            }

        }
    }

    @Override
    public void onFinished(MovieFeed feedDownloaded) {
        feed = feedDownloaded;
        bindData();
        saveDataToLocalDB(feed);
    }

    @Override
    public void onStarted() {
        mStatusTv.setText(getResources().getString(R.string.loading));
    }

    @Override
    public void onFailed() {

    }


    private void saveDataToLocalDB(MovieFeed feed) {
        dbManager.insertAll(feed.getResults());
    }


    private void bindData() {
        mStatusTv.setVisibility(View.INVISIBLE);
        FeedRecyclerViewAdapter mFeedAdapter = new FeedRecyclerViewAdapter(MainActivity.this, feed.getResults());
        mMovieFeedRv.setAdapter(mFeedAdapter);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (null != feed)
            outState.putParcelable(LabConstants.KEY, feed);
    }

}
