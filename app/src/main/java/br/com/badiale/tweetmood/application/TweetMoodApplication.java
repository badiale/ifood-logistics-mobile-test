package br.com.badiale.tweetmood.application;

import android.app.Application;

import br.com.badiale.tweetmood.sync.TweetUpdaterJobService;

public class TweetMoodApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        TweetUpdaterJobService.schedule(this);
    }
}
