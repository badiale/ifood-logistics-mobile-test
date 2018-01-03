package br.com.badiale.tweetmood.twitterlist;

import com.google.common.base.MoreObjects;

import br.com.badiale.tweetmood.twitter.TwitterSearchResultStatus;

class TweetClickedEvent {
    private TwitterSearchResultStatus tweet;

    TweetClickedEvent(final TwitterSearchResultStatus tweet) {
        this.tweet = tweet;
    }

    TwitterSearchResultStatus getTweet() {
        return tweet;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("tweet", tweet)
                .toString();
    }
}
