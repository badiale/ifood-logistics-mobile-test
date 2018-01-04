package br.com.badiale.tweetmood.application;

import android.app.Application;

import br.com.badiale.tweetmood.BuildConfig;
import br.com.badiale.tweetmood.sync.TweetUpdaterJobService;
import br.com.badiale.tweetmood.timber.TimberProductionLog;
import timber.log.Timber;

public class TweetMoodApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        TweetUpdaterJobService.schedule(this);

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            Timber.plant(new TimberProductionLog());
        }
    }
}
