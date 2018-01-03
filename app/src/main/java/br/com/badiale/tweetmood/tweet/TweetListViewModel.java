package br.com.badiale.tweetmood.tweet;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import br.com.badiale.tweetmood.twitter.TwitterSearchResultStatus;
import br.com.badiale.tweetmood.twitter.TwitterService;
import io.reactivex.android.schedulers.AndroidSchedulers;

class TweetListViewModel extends ViewModel {
    private MutableLiveData<List<TwitterSearchResultStatus>> statuses = new MutableLiveData<>();
    private final TwitterService twitterService;

    TweetListViewModel() {
        twitterService = TwitterService.getInstance();

    }

    LiveData<List<TwitterSearchResultStatus>> getStatuses() {
        return statuses;
    }

    void searchUser(String userId) {
        twitterService.getUserTimeline(userId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(twitterSearchResult -> statuses.setValue(twitterSearchResult.getStatuses()));
    }
}
