package br.com.badiale.tweetmood.twitter;

import com.google.common.base.MoreObjects;
import com.google.gson.annotations.SerializedName;

public class TwiiterSearchResultStatus {
    private TwiiterSearchResultUser user;

    private String text;

    @SerializedName("created_at")
    private String createdAt;

    public TwiiterSearchResultUser getUser() {
        return user;
    }

    public void setUser(final TwiiterSearchResultUser user) {
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
