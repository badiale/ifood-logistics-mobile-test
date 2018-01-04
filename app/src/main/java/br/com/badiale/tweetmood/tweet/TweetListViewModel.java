package br.com.badiale.tweetmood.tweet;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.crashlytics.android.Crashlytics;
import com.google.common.collect.ImmutableList;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.List;

import br.com.badiale.tweetmood.R;
import br.com.badiale.tweetmood.naturallanguage.NaturalLanguageService;
import br.com.badiale.tweetmood.twitter.TwitterSearchResult;
import br.com.badiale.tweetmood.twitter.TwitterSearchResultStatus;
import br.com.badiale.tweetmood.twitter.TwitterService;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

class TweetListViewModel extends AndroidViewModel {
    private final TwitterService twitterService;
    private final NaturalLanguageService naturalLanguageService;
    private final FirebaseAnalytics firebaseAnalytics;

    private TwitterSearchResult searchResult;

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

    private <T> Observable<T> loading(Observable<T> observable) {
        loading.setValue(true);
        return observable.doFinally(() -> loading.setValue(false));
    }

    private Consumer<Throwable> handleErrors(final int errorMessage) {
        return throwable -> {
            Crashlytics.logException(throwable);
            error.setValue(errorMessage);
        };
    }

    void searchUser(String userId) {
        firebaseAnalytics.logEvent("search_user", null);
        loading(twitterService.searchUserTweets(userId))
                .subscribe(twitterSearchResult -> {
                    this.searchResult = twitterSearchResult;
                    statuses.setValue(twitterSearchResult.getStatuses());
                }, handleErrors(R.string.error_loading_tweet));
    }

    void analyse(final TwitterSearchResultStatus tweet) {
        firebaseAnalytics.logEvent("analyse_tweet", null);
        loading(naturalLanguageService.analyse(tweet.getText()))
                .subscribe(sentiment -> {
                    tweet.setSentiment(sentiment);
                    statuses.setValue(statuses.getValue());
                }, handleErrors(R.string.error_analysing_tweet));
    }

    void refresh() {
        firebaseAnalytics.logEvent("refresh", null);
        loading(twitterService.searchTweetsByQueryString(searchResult.getMetadata().getRefreshUrl()))
                .subscribe(twitterSearchResult -> {
                    this.searchResult = twitterSearchResult;
                    statuses.setValue(ImmutableList.<TwitterSearchResultStatus>builder()
                            .addAll(twitterSearchResult.getStatuses())
                            .addAll(statuses.getValue())
                            .build());
                }, handleErrors(R.string.error_loading_tweet));
    }
}
