package br.com.badiale.tweetmood.twitterlist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import br.com.badiale.tweetmood.R;
import br.com.badiale.tweetmood.twitter.TwitterSearchResultStatus;

class TweetAdapter extends RecyclerView.Adapter<TweetViewHolder> {
    private List<TwitterSearchResultStatus> tweets = new ArrayList<>();

    void append(List<TwitterSearchResultStatus> tweets) {
        this.tweets.addAll(tweets);
        notifyDataSetChanged();
    }

    void update(List<TwitterSearchResultStatus> tweets) {
        this.tweets.clear();
        append(tweets);
    }

    @Override
    public TweetViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final Context context = parent.getContext();
        final LayoutInflater layoutInflater = LayoutInflater.from(context);
        final View view = layoutInflater.inflate(R.layout.tweet_view_holder, parent, false);
        return new TweetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final TweetViewHolder holder, final int position) {
        holder.setTweet(tweets.get(position));
    }

    @Override
    public int getItemCount() {
        return tweets.size();
    }
}
