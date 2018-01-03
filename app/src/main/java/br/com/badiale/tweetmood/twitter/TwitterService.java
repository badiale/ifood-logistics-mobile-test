package br.com.badiale.tweetmood.twitter;

import android.support.annotation.VisibleForTesting;

import br.com.badiale.tweetmood.BuildConfig;
import br.com.badiale.tweetmood.retrofit.Requests;
import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TwitterService {
    private static final TwitterService INSTANCE = new TwitterService();

    private final TwitterApi api;

    public static TwitterService getInstance() {
        return INSTANCE;
    }

    private TwitterService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.twitter.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        this.api = retrofit.create(TwitterApi.class);
    }

    @VisibleForTesting
    Observable<TwitterAuthenticationResult> authenticate() {
        return Requests.enqueue(api.oauthToken("Basic " + BuildConfig.TWITTER_API_KEY, "client_credentials"));
    }

    public Observable<TwitterSearchResult> getUserTimeline(String userId) {
        return authenticate()
                .map(TwitterAuthenticationResult::getAccessToken)
                .map(token -> api.searchTweets("Bearer " + token, "from:" + userId))
                .flatMap(Requests::enqueue);
    }
}
