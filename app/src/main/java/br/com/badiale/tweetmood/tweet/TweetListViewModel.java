package br.com.badiale.tweetmood.tweet;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.google.common.collect.ImmutableList;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.Collections;
import java.util.List;

import br.com.badiale.tweetmood.R;
import br.com.badiale.tweetmood.naturallanguage.NaturalLanguageService;
import br.com.badiale.tweetmood.preference.PreferenceHelper;
import br.com.badiale.tweetmood.twitter.TwitterSearchResult;
import br.com.badiale.tweetmood.twitter.TwitterSearchResultStatus;
import br.com.badiale.tweetmood.twitter.TwitterService;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import timber.log.Timber;

public class TweetListViewModel extends AndroidViewModel {
    private final TwitterService twitterService;
    private final NaturalLanguageService naturalLanguageService;
    private final FirebaseAnalytics firebaseAnalytics;
    private final PreferenceHelper preferenceHelper;

    private TwitterSearchResult searchResult;

    private MutableLiveData<List<TwitterSearchResultStatus>> statuses = new MutableLiveData<>();
    private MutableLiveData<Boolean> hasMorePages = new MutableLiveData<>();
    private MutableLiveData<Boolean> loading = new MutableLiveData<>();
    private MutableLiveData<Integer> error = new MutableLiveData<>();

    public TweetListViewModel(Application context) {
        super(context);
        twitterService = TwitterService.getInstance();
        naturalLanguageService = NaturalLanguageService.getInstance();
        firebaseAnalytics = FirebaseAnalytics.getInstance(context);
        preferenceHelper = PreferenceHelper.from(context);

        hasMorePages.setValue(false);
        statuses.setValue(Collections.emptyList());
        loading.setValue(false);

        searchByLastUser();
    }

    private void searchByLastUser() {
        firebaseAnalytics.logEvent("searchByLastUser", null);
        final String lastUser = preferenceHelper.getLastUser();
        if (!lastUser.isEmpty()) {
            searchUser(lastUser);
        }
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

    LiveData<Boolean> hasMorePages() {
        return hasMorePages;
    }

    private <T> Observable<T> loading(Observable<T> observable) {
        loading.setValue(true);
        return observable.doFinally(() -> loading.setValue(false));
    }

    private Consumer<Throwable> handleErrors(final int errorMessage) {
        return throwable -> {
            Timber.w(throwable, "Error loading feeds");
            error.setValue(errorMessage);
        };
    }

    void searchUser(String userId) {
        firebaseAnalytics.logEvent("search_user", null);
        preferenceHelper.setLastUser(userId);
        loading(twitterService.searchUserTweets(userId))
                .subscribe(twitterSearchResult -> {
                    updateSearchResult(twitterSearchResult);
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
        if (searchResult == null) {
            searchByLastUser();
            return;
        }
        loading(twitterService.searchTweetsByQueryString(searchResult.getMetadata().getRefreshUrl()))
                .subscribe(twitterSearchResult -> {
                    updateSearchResult(twitterSearchResult);
                    statuses.setValue(ImmutableList.<TwitterSearchResultStatus>builder()
                            .addAll(twitterSearchResult.getStatuses())
                            .addAll(statuses.getValue())
                            .build());
                }, handleErrors(R.string.error_loading_tweet));
    }

    void loadNextPage() {
        firebaseAnalytics.logEvent("loadNextPage", null);
        if (loading.getValue()) {
            return;
        }
        loading(twitterService.searchTweetsByQueryString(searchResult.getMetadata().getNextResults()))
                .subscribe(twitterSearchResult -> {
                    updateSearchResult(twitterSearchResult);
                    statuses.setValue(ImmutableList.<TwitterSearchResultStatus>builder()
                            .addAll(statuses.getValue())
                            .addAll(twitterSearchResult.getStatuses())
                            .build());
                }, handleErrors(R.string.error_loading_tweet));
    }

    private void updateSearchResult(final TwitterSearchResult twitterSearchResult) {
        this.searchResult = twitterSearchResult;
        preferenceHelper.setLastUpdateUrl(twitterSearchResult.getMetadata().getRefreshUrl());
        final boolean newValue = twitterSearchResult.getMetadata().getNextResults() != null;
        if (newValue != hasMorePages.getValue()) {
            hasMorePages.setValue(newValue);
        }
    }
}
