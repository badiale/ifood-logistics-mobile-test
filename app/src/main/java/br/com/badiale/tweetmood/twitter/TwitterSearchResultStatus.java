package br.com.badiale.tweetmood.twitter;

import com.google.common.base.MoreObjects;
import com.google.gson.annotations.SerializedName;

import br.com.badiale.tweetmood.naturallanguage.Sentiment;

public class TwitterSearchResultStatus {
    private TwitterSearchResultUser user;

    private String text;

    @SerializedName("created_at")
    private String createdAt;

    private Sentiment sentiment;

    public TwitterSearchResultUser getUser() {
        return user;
    }

    public void setUser(final TwitterSearchResultUser user) {
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public void setText(final String text) {
        this.text = text;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(final String createdAt) {
        this.createdAt = createdAt;
    }

    public Sentiment getSentiment() {
        return sentiment;
    }

    public void setSentiment(final Sentiment sentiment) {
        this.sentiment = sentiment;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("user", user)
                .add("text", text)
                .add("createdAt", createdAt)
                .add("sentiment", sentiment)
                .toString();
    }
}
