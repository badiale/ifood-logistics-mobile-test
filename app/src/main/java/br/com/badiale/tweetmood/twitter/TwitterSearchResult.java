package br.com.badiale.tweetmood.twitter;

import com.google.common.base.MoreObjects;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TwitterSearchResult {
    private List<TwitterSearchResultStatus> statuses;

    @SerializedName("search_metadata")
    private TwitterSearchResultMetadata metadata;

    public List<TwitterSearchResultStatus> getStatuses() {
        return statuses;
    }

    public void setStatuses(final List<TwitterSearchResultStatus> statuses) {
        this.statuses = statuses;
    }

    public TwitterSearchResultMetadata getMetadata() {
        return metadata;
    }

    public void setMetadata(final TwitterSearchResultMetadata metadata) {
        this.metadata = metadata;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("statuses", statuses)
                .add("metadata", metadata)
                .toString();
    }
}
