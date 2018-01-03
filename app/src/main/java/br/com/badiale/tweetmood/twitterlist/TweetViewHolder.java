package br.com.badiale.tweetmood.twitterlist;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import br.com.badiale.tweetmood.R;
import br.com.badiale.tweetmood.eventbus.EventBusUtils;
import br.com.badiale.tweetmood.twitter.TwiiterSearchResultStatus;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

class TweetViewHolder extends RecyclerView.ViewHolder {

    private TwiiterSearchResultStatus tweet;

    @BindView(R.id.tweet_profile_picture)
    ImageView profilePicture;

    @BindView(R.id.tweet_user_name)
    TextView userName;

    @BindView(R.id.tweet_text)
    TextView text;

    @BindView(R.id.tweet_date)
    TextView date;

    TweetViewHolder(final View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    void setTweet(final TwiiterSearchResultStatus tweet) {
        this.tweet = tweet;
        updatePicture();
        userName.setText(tweet.getUser().getName());
        text.setText(tweet.getText());
        updateDate();
    }

    private void updatePicture() {
        Picasso.with(itemView.getContext())
                .load(tweet.getUser().getProfileImageUrl())
                .into(profilePicture);
    }

    private void updateDate() {
        date.setText(tweet.getCreatedAt());
    }

    @OnClick(R.id.tweet_view_card)
    void tweetClicked() {
        EventBusUtils.post(new TweetClickedEvent(tweet));
    }
}
