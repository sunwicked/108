package com.one.assignment.utils;

import android.app.Activity;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.one.assignment.LabConstants;
import com.one.assignment.listeners.DownloaderListener;
import com.one.assignment.models.MovieFeed;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Sunny on 06-05-2016.
 */
public class AsyncStart extends AsyncTask<Void, Integer, Boolean> {
    private OkHttpClient client = new OkHttpClient();
    private Gson gson = new Gson();
    private MovieFeed feed;
    private DownloaderListener mDownloaderListener;
    private Activity activity;

    public AsyncStart(Activity activity) {
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mDownloaderListener = (DownloaderListener) activity;
        mDownloaderListener.onStarted();
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        try {
            publishProgress(25);
            feed = getData();
        } catch (Exception e) {
            e.printStackTrace();
            return LabConstants.FAIL;
        }
        return LabConstants.SUCCESS;
    }


    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        mDownloaderListener.onUpdate(values[0]);
    }

    private MovieFeed getData() throws Exception {
        Request request = new Request.Builder()
                .url(LabConstants.LIVE_URL)
                .build();

        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) {
            throw new IOException("Unexpected code " + response);
        }
        publishProgress(50);
        return gson.fromJson(response.body().string(), MovieFeed.class);
    }


    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);
        if (result) {
            mDownloaderListener.onFinished(feed);
        } else {
            mDownloaderListener.onFailed();
        }
    }
}