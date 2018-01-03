package br.com.badiale.tweetmood.twitter;

import com.google.common.base.MoreObjects;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TwiiterSearchResult {
    private List<TwiiterSearchResultStatus> statuses;

    @SerializedName("search_metadata")
    private TwiiterSearchResultMetadata metadata;

    public List<TwiiterSearchResultStatus> getStatuses() {
        return statuses;
    }

    public void setStatuses(final List<TwiiterSearchResultStatus> statuses) {
        this.statuses = statuses;
    }

    public TwiiterSearchResultMetadata getMetadata() {
        return metadata;
    }

    public void setMetadata(final TwiiterSearchResultMetadata metadata) {
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
