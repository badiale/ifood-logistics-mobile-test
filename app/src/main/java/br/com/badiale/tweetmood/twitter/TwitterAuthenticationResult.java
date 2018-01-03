package br.com.badiale.tweetmood.twitter;

import com.google.gson.annotations.SerializedName;

import static com.google.common.base.MoreObjects.toStringHelper;

public class TwitterAuthenticationResult {
    @SerializedName("token_type")
    private String tokenType;

    @SerializedName("access_token")
    private String accessToken;

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(final String tokenType) {
        this.tokenType = tokenType;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(final String accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    public String toString() {
        return toStringHelper(this)
                .add("tokenType", tokenType)
                .add("accessToken", accessToken)
                .toString();
    }
}
