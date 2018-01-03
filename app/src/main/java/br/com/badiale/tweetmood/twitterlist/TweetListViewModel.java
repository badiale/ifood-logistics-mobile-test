package br.com.badiale.tweetmood.twitterlist;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import br.com.badiale.tweetmood.twitter.TwiiterSearchResultStatus;
import br.com.badiale.tweetmood.twitter.TwitterService;
import io.reactivex.android.schedulers.AndroidSchedulers;

class TweetListViewModel extends ViewModel {
    private MutableLiveData<List<TwiiterSearchResultStatus>> statuses = new MutableLiveData<>();
    private final TwitterService twitterService;

    TweetListViewModel() {
        twitterService = TwitterService.getInstance();

    }

    LiveData<List<TwiiterSearchResultStatus>> getStatuses() {
        return statuses;
    }

    void searchUser(String userId) {
        twitterService.getUserTimeline(userId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(twiiterSearchResult -> statuses.setValue(twiiterSearchResult.getStatuses()));
    }
}
