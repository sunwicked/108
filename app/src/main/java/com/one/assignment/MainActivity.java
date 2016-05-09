package com.one.assignment;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.one.assignement.R;
import com.one.assignment.database.DbManager;
import com.one.assignment.fragments.FeedFragment;
import com.one.assignment.listeners.DownloaderListener;
import com.one.assignment.models.MovieFeed;
import com.one.assignment.models.Result;
import com.one.assignment.utils.AsyncStart;
import com.one.assignment.utils.ConnectionDetector;
import com.one.assignment.utils.Log;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements DownloaderListener {

    private static final String TAG = "MainActivity";
    private MovieFeed feed;
    private DbManager dbManager;
    private  TextView mStatusTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_refresh);
        mStatusTv =  (TextView)findViewById(R.id.tv_status);
        if (null != savedInstanceState) {
            feed = savedInstanceState.getParcelable(LabConstants.KEY);
        }
        dbManager = new DbManager(this);
        try {
            setFeed();
        } catch (IOException e) {
            e.printStackTrace();
        }

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

    private void setFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        FeedFragment fragment = new FeedFragment();
        ft.replace(R.id.your_placeholder, fragment);
        Bundle bundle = new Bundle();
        bundle.putParcelable(LabConstants.KEY_LOCAL, feed);
        fragment.setArguments(bundle);
        ft.commit();
    }





    private void setFeed() throws IOException {
        if (null != feed) {
            setFragment();
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
        try {
            setFeed();
        } catch (IOException e) {
            e.printStackTrace();
        }
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



    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (null != feed)
            outState.putParcelable(LabConstants.KEY, feed);
    }

}
