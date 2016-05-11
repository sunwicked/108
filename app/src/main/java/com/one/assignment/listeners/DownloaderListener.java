package com.one.assignment.listeners;

import com.one.assignment.models.MovieFeed;

/**
 * Created by Sunny on 06-05-2016.
 */
public interface DownloaderListener {

    void onFinished(MovieFeed feed);

    void onStarted();

    void onFailed();

    void onUpdate(int progress);

}
