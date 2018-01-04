package br.com.badiale.tweetmood.tweet;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.crashlytics.android.Crashlytics;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.List;

import br.com.badiale.tweetmood.R;
import br.com.badiale.tweetmood.naturallanguage.NaturalLanguageService;
import br.com.badiale.tweetmood.twitter.TwitterSearchResultStatus;
import br.com.badiale.tweetmood.twitter.TwitterService;

class TweetListViewModel extends AndroidViewModel {
    private final TwitterService twitterService;
    private final NaturalLanguageService naturalLanguageService;
    private final FirebaseAnalytics firebaseAnalytics;

    private String userId = "";

    private MutableLiveData<List<TwitterSearchResultStatus>> statuses = new MutableLiveData<>();
    private MutableLiveData<Boolean> loading = new MutableLiveData<>();
    private MutableLiveData<Integer> error = new MutableLiveData<>();

    TweetListViewModel(Application context) {
        super(context);
        twitterService = TwitterService.getInstance();
        naturalLanguageService = NaturalLanguageService.getInstance();
        firebaseAnalytics = FirebaseAnalytics.getInstance(context);
    }

    LiveData<List<TwitterSearchResultStatus>> getStatuses() {
        return statuses;
    }

    LiveData<Boolean> isLoading() {
        return loading;
    }

    LiveData<Integer> getError() {
        return error;
    }

    void searchUser(String userId) {
        firebaseAnalytics.logEvent("search_user", null);
        this.userId = userId;
        loading.setValue(true);
        twitterService.getUserTimeline(userId)
                .doFinally(() -> loading.setValue(false))
                .subscribe(twitterSearchResult -> {
                    statuses.setValue(twitterSearchResult.getStatuses());
                }, (throwable) -> handleError(throwable, R.string.error_loading_tweet));
    }

    void analyse(final TwitterSearchResultStatus tweet) {
        firebaseAnalytics.logEvent("analyse_tweet", null);
        loading.setValue(true);
        naturalLanguageService.analyse(tweet.getText())
                .doFinally(() -> loading.setValue(false))
                .subscribe(sentiment -> {
                    tweet.setSentiment(sentiment);
                    statuses.setValue(statuses.getValue());
                }, (throwable) -> handleError(throwable, R.string.error_analysing_tweet));
    }

    void refresh() {
        firebaseAnalytics.logEvent("analyse_tweet", null);
        searchUser(userId);
    }

    private void handleError(final Throwable throwable, int resourceId) {
        Crashlytics.logException(throwable);
        error.setValue(resourceId);
    }

}
