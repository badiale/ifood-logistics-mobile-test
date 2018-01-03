package br.com.badiale.tweetmood.twitter;

import com.google.common.base.MoreObjects;
import com.google.gson.annotations.SerializedName;

public class TwitterSearchResultStatus {
    private TwitterSearchResultUser user;

    private String text;

    @SerializedName("created_at")
    private String createdAt;

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

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("user", user)
                .add("text", text)
                .add("createdAt", createdAt)
                .toString();
    }
}
