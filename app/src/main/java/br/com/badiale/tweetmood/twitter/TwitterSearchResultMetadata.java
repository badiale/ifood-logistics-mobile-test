package br.com.badiale.tweetmood.twitter;

import com.google.common.base.MoreObjects;
import com.google.gson.annotations.SerializedName;

public class TwitterSearchResultMetadata {
    @SerializedName("max_id")
    private Long maxId;

    private Integer count;

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

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("maxId", maxId)
                .add("count", count)
                .toString();
    }
}
