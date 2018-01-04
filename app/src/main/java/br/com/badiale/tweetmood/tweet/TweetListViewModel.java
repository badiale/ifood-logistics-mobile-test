package br.com.badiale.tweetmood.tweet;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import br.com.badiale.tweetmood.R;
import br.com.badiale.tweetmood.naturallanguage.NaturalLanguageService;
import br.com.badiale.tweetmood.twitter.TwitterSearchResultStatus;
import br.com.badiale.tweetmood.twitter.TwitterService;
import io.reactivex.android.schedulers.AndroidSchedulers;

class TweetListViewModel extends ViewModel {
    private final TwitterService twitterService;
    private final NaturalLanguageService naturalLanguageService;

    private String userId = "";

    private MutableLiveData<List<TwitterSearchResultStatus>> statuses = new MutableLiveData<>();
    private MutableLiveData<Boolean> loading = new MutableLiveData<>();
    private MutableLiveData<Integer> error = new MutableLiveData<>();

    TweetListViewModel() {
        twitterService = TwitterService.getInstance();
        naturalLanguageService = NaturalLanguageService.getInstance();
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
        this.userId = userId;
        loading.setValue(true);
        twitterService.getUserTimeline(userId)
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> loading.setValue(false))
                .subscribe(twitterSearchResult -> {
                    statuses.setValue(twitterSearchResult.getStatuses());
                }, (throwable) -> handleError(throwable, R.string.error_loading_tweet));
    }

    void analyse(final TwitterSearchResultStatus tweet) {
        loading.setValue(true);
        naturalLanguageService.analyse(tweet.getText())
                .doFinally(() -> loading.setValue(false))
                .subscribe(sentiment -> {
                    tweet.setSentiment(sentiment);
                    statuses.setValue(statuses.getValue());
                }, (throwable) -> handleError(throwable, R.string.error_analysing_tweet));
    }

    void refresh() {
        searchUser(userId);
    }

    private void handleError(final Throwable throwable, int resourceId) {
        error.setValue(resourceId);
    }

}
