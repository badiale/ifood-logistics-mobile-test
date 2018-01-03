package br.com.badiale.tweetmood.twitterlist;

import com.google.common.base.MoreObjects;

import br.com.badiale.tweetmood.twitter.TwiiterSearchResultStatus;

class TweetClickedEvent {
    private TwiiterSearchResultStatus tweet;

    TweetClickedEvent(final TwiiterSearchResultStatus tweet) {
        this.tweet = tweet;
    }

    TwiiterSearchResultStatus getTweet() {
        return tweet;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("tweet", tweet)
                .toString();
    }
}
