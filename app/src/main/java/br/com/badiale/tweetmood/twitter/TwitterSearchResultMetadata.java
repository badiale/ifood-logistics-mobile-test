package br.com.badiale.tweetmood.twitter;

import com.google.common.base.MoreObjects;
import com.google.gson.annotations.SerializedName;

public class TwitterSearchResultMetadata {
    @SerializedName("max_id")
    private Long maxId;

    private Integer count;

    @SerializedName("refresh_url")
    private String refreshUrl;

    @SerializedName("next_results")
    private String nextResults;

    public Long getMaxId() {
        return maxId;
    }

    public void setMaxId(final Long maxId) {
        this.maxId = maxId;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(final Integer count) {
        this.count = count;
    }

    public String getRefreshUrl() {
        return refreshUrl;
    }

    public void setRefreshUrl(final String refreshUrl) {
        this.refreshUrl = refreshUrl;
    }

    public String getNextResults() {
        return nextResults;
    }

    public void setNextResults(final String nextResults) {
        this.nextResults = nextResults;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("maxId", maxId)
                .add("count", count)
                .add("refreshUrl", refreshUrl)
                .add("nextResults", nextResults)
                .toString();
    }
}
