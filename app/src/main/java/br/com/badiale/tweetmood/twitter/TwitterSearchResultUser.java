package br.com.badiale.tweetmood.twitter;

import com.google.common.base.MoreObjects;
import com.google.gson.annotations.SerializedName;

public class TwitterSearchResultUser {
    private String name;

    @SerializedName("screen_name")
    private String screenName;

    @SerializedName("profile_image_url")
    private String profileImageUrl;

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(final String screenName) {
        this.screenName = screenName;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(final String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("name", name)
                .add("screenName", screenName)
                .add("profileImageUrl", profileImageUrl)
                .toString();
    }
}
