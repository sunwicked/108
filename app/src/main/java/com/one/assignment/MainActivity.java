package com.one.assignment;

import android.animation.ObjectAnimator;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.one.assignement.R;
import com.one.assignment.adapters.FeedRecyclerViewAdapter;
import com.one.assignment.models.MovieFeed;
import com.one.assignment.utils.ConnectionDetector;
import com.one.assignment.utils.Log;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private TextView mStatusTv;
    private RecyclerView mMovieFeedRv;

    private static final String TAG = "MainActivity";
    private static final boolean FAIL = false;
    private static final boolean SUCCESS = true;


    private OkHttpClient client = new OkHttpClient();
    MovieFeed feed;
    private Gson gson = new Gson();
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setViews();
        if (null != savedInstanceState) {
            feed = savedInstanceState.getParcelable("key");
        }
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
        mLayoutManager = new LinearLayoutManager(this);
        mMovieFeedRv.setLayoutManager(mLayoutManager);

    }


    private void setFeed() throws IOException {
        if (null != feed) {
            bindData();
        } else if (ConnectionDetector.isConnectingToInternet(this)) {
            feed = new MovieFeed();
            mStatusTv.setText(getResources().getString(R.string.loading));
            Log.d(TAG, "setFeed:" + "network available");
            new AsyncStart().execute();
        } else {
            // network not available
            mStatusTv.setText(getResources().getString(R.string.no_network));
            ObjectAnimator.ofFloat(mStatusTv, "translationX", -10, 0, 10, 0).start();
            Log.d(TAG, "setFeed:" + "network not available");
        }
    }


    public class AsyncStart extends AsyncTask<Void, Void, Boolean> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                feed = getData();
            } catch (Exception e) {
                e.printStackTrace();
                return FAIL;
            }
            return SUCCESS;
        }

        private MovieFeed getData() throws Exception {
            Request request = new Request.Builder()
                    .url(LabConstants.LIVE_URL)
                    .build();

            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            return gson.fromJson(response.body().string(), MovieFeed.class);
        }


        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            if (result) {
                bindData();
            }
        }
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
            outState.putParcelable("key", feed);
    }

}
